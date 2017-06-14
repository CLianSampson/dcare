package com.dcare.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.AddFamilyAO;
import com.dcare.ao.DownloadTemperatureAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.BloodPressure;
import com.dcare.po.Family;
import com.dcare.po.Temperature;
import com.dcare.service.BloodPressureService;
import com.dcare.service.FamilyService;
import com.dcare.service.TemperatureService;

@Controller
public class TemperatureController extends BaseController {
	private static Logger logger = Logger.getLogger(FamilyController.class);
	
	@Autowired
	private TemperatureService temperatureService;
	
	/**
	 * 上传温度
	 * @param packet
	 * @param response
	 */
	@RequestMapping(value = "/temperature/upload", method = RequestMethod.POST)
	public void uploadTemperature(@RequestBody Packet packet, HttpServletResponse response) {
		logger.info("上传温度:" + packet.toString());
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("上传温度参数错误，收到参数为空");
					break;
				}
				
				AddFamilyAO addFamilyAO = null;
				try {
					addFamilyAO = JSON.parseObject(packet.getData(), AddFamilyAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("上传温度参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null == addFamilyAO.getRelation()) {
					logger.error("上传温度参数错误，收到参数错误，关系不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				Family family = new Family();
				family.setBrithday(addFamilyAO.getBrithday());
				family.setCreateTime(new Date());
				family.setGender(addFamilyAO.getGender());
				family.setNickname(addFamilyAO.getNickname());
				family.setRelation(addFamilyAO.getRelation());
				family.setStature(addFamilyAO.getStature());
				family.setStatus(addFamilyAO.getStature());
				family.setWeight(addFamilyAO.getWeight());
				family.setUserId(appUserId);
			
			  
//				rtv = familyService.addFamilyMember(family);
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("上传温度", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("上传温度结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	/**
	 * 获取温度
	 * @param packet
	 * @param response
	 */
	@RequestMapping(value = "/temperature/download", method = RequestMethod.POST)
	public void getTemperature(@RequestBody Packet packet, HttpServletResponse response) {
		logger.info("获取温度:" + packet.toString());
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		List<Temperature> returnList = null;
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("获取温度参数错误，收到参数为空");
					break;
				}
				
				DownloadTemperatureAO downloadTemperatureAO = null;
				try {
					downloadTemperatureAO = JSON.parseObject(packet.getData(), DownloadTemperatureAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("获取温度参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null == downloadTemperatureAO.getDay()) {
					logger.error("获取温度参数错误，收到参数错误，day");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (downloadTemperatureAO.getFamilyUserId() < 0) {
					logger.error("获取温度参数错误，收到参数错误，familyUerId");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				returnList = temperatureService.geTemperaturesByFamilyUserId(downloadTemperatureAO.getFamilyUserId(), downloadTemperatureAO.getDay());
				
        		break;
			}

		} catch (Exception e) {
			logger.error("获取温度", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		if (rtv == AppErrorEnums.APP_OK) {
			rtvPacket.setData(JSON.toJSONString(returnList));
		}else {
			rtvPacket.setData(rtv.getMessage());
		}
	
		
		logger.info("获取温度结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
}




