package com.dcare.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dcare.common.util.DateUtil;
import com.dcare.dao.TemperatureDO;
import com.dcare.po.Temperature;
import com.dcare.service.TemperatureService;

@Service
@Transactional
public class TemperatureServiceImpl implements TemperatureService {
	
	
	@Autowired
	private TemperatureDO temperatureDO;
	
	public List<Temperature> geTemperaturesByFamilyUserId(int familyUserId, Date time) {
		
		String timeString = DateUtil.getDateStr_YYYY_MM_DD_FORMAT(time);
		
    	return temperatureDO.selectByFamilyUerIdAndDate(null,familyUserId, timeString);
	}

	public void addTemperature(int userId,int familyUserId, int zone,
			List<String> temp) {
		
		Date date = new Date();
		int hour = date.getHours();
		String dateString = DateUtil.getDateStr_YYYY_MM_DD_FORMAT(date);
		
		//1、获取当前小时
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		int hour = calendar.get(Calendar.HOUR);
		
		List<Temperature> list = temperatureDO.selectByFamilyUerIdAndDate(userId, familyUserId, dateString);
		if (null==list || list.size()==0) {
			//当天没有记录
			if (hour < 12) {
				//说明上传的数据中也有昨天的数据
				//首先判断昨天是否已经在数据库中存在数据
				
				updateOrInsertYesterdayRecord(userId, familyUserId, zone, temp, hour);
				
			}else {
				//只有今天,将之前没有的数据 补为 空字符串 ""
				List<String> realTemperatureList = new ArrayList<String>();
				
				for (int i = 0; i < 24; i++) {
					if (i < (hour - 12)) {
						realTemperatureList.add(" ");
					}else if(i>hour){
						realTemperatureList.add(" ");
					}else {
						realTemperatureList.add(temp.get(hour-12));
					}
				}
				
				String jsonString = JSON.toJSONString(realTemperatureList);
				Temperature temperature = new Temperature();
				temperature.setCreateTime(new Date());
				temperature.setFamilyUserId(familyUserId);
				temperature.setTemperature(jsonString);
				temperature.setTime(dateString);
				temperature.setUserId(userId);
				
				
				temperatureDO.insert(temperature);
			}
			
		}else {
			//更新当天记录
			updateTodayTemperature(list, temp, hour);
		}
		
	}
	
	private void updateTodayTemperature(List<Temperature> list ,List<String> temp ,int hour){
		Temperature temperatureInDB = list.get(0);
		
		String stringInDB = temperatureInDB.getTemperature();
		List<String> listInDB = JSON.parseArray(stringInDB, String.class);
		
//		for (int i = 0; i < temp.size(); i++) {
//			if (i > hour-13) {
//				listInDB.set(i, temp.get(hour-i-1-1));
//			}
//		}
		
		for (int i = 0; i < listInDB.size(); i++) {
			if (i > hour-13) {
				int index = i - (hour-13) -1;
				if (index > 12) {
					break;
				}
				listInDB.set(i, temp.get(index) );
			}
		}
		
		//重新设置温度值
		temperatureInDB.setTemperature(JSON.toJSONString(listInDB));
		
		temperatureDO.updateByPrimaryKeySelective(temperatureInDB);
		
	}
	
	private void updateOrInsertYesterdayRecord(int userId,int familyUserId, int zone,
			List<String> temp, int hour){
		
		Date yesterday = DateUtil.getYesterDate(new Date());
		String dateStr = DateUtil.formatCurrentTime(yesterday, DateUtil.YYYY_MM_DD_FORMAT);
//        Date date = DateUtil.stringToDateFormat(dateStr, DateUtil.YYYY_MM_DD_FORMAT);
		
		List<Temperature> list = temperatureDO.selectByFamilyUerIdAndDate(userId, familyUserId, dateStr);
		
		if (null==list || list.size()==0) {
			
//			List<String> realTemperatureList = new ArrayList<String>(24); //这种初始化方法不行，size()为0
			List<String> realTemperatureList = new ArrayList<String>();
			for (int i = 0; i < 24; i++) {
				realTemperatureList.add(" ");
			}
			
			for (int i = 0; i < realTemperatureList.size(); i++) {
				
				if (i >= (24 -(12-i)) ) {
					realTemperatureList.set(i, temp.get(24 -(12-i)));
				}	
			}
			
			String jsonString = JSON.toJSONString(realTemperatureList);
			Temperature temperature = new Temperature();
			temperature.setCreateTime(new Date());
			temperature.setFamilyUserId(familyUserId);
			temperature.setTemperature(jsonString);
			temperature.setTime(dateStr);
			temperature.setUserId(userId);
			
			
			temperatureDO.insert(temperature);
			
		}else {
			
			Temperature temperatureInDB = list.get(0);
			
			String stringInDB = temperatureInDB.getTemperature();
			List<String> listInDB = JSON.parseArray(stringInDB, String.class);
			
			for (int i = 0; i < 12 - i; i++) {
				listInDB.set(24 -(12-i), temp.get(i));
			}
			
			//重新设置温度值
			temperatureInDB.setTemperature(JSON.toJSONString(listInDB));
			
			temperatureDO.updateByPrimaryKeySelective(temperatureInDB);
			
		}
		
	}
	
	public static void main(String[] args){
		List<String> realTemperatureList = new ArrayList<String>(24);
		System.out.println("realTemperatureList.size is : " + realTemperatureList.size());
	}
	
	
}
