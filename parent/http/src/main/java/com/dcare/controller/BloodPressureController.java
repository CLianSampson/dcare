package com.dcare.controller;

import java.io.UnsupportedEncodingException;
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
import com.dcare.ao.AddBloodPresureAO;
import com.dcare.ao.BloodListAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.BloodPressure;
import com.dcare.service.BloodPressureService;

@Controller
public class BloodPressureController extends BaseController {
private static Logger logger = Logger.getLogger(FamilyController.class);
	
	@Autowired
	private BloodPressureService bloodPressureService;
	
	/**
	 *上传血压
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/bloodPressure/add", method = RequestMethod.POST)
	public void uploadBloodPressure(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("上传血压:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("上传血压参数错误，收到参数为空");
					break;
				}
				
				AddBloodPresureAO addBloodPresureAO = null;
				try {
					addBloodPresureAO = JSON.parseObject(packet.getData(), AddBloodPresureAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("上传血压参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null == addBloodPresureAO.getId()) {
					logger.error("上传血压参数错误，收到参数错误，家庭成员不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				BloodPressure bloodPresure = new BloodPressure();
				bloodPresure.setUserId(appUserId);
				bloodPresure.setFamilyUserId(addBloodPresureAO.getId());
				bloodPresure.setDiastolic(addBloodPresureAO.getDiastolic());
				bloodPresure.setHeartRate(addBloodPresureAO.getHeartRate());
				bloodPresure.setSystolic(addBloodPresureAO.getSystolic());
				bloodPresure.setCreateTime(new Date());
			  
				rtv = bloodPressureService.addBloodPressure(bloodPresure);
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("上传血压", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("上传血压结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	
	/**
	 *获取血压
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/bloodPressure/list", method = RequestMethod.POST)
	public void getBloodPresureList(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("取血获压:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		List<BloodPressure> returnList = null;
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("获取血压参数错误，收到参数为空");
					break;
				}
				
				BloodListAO bloodListAO = null;
				try {
					bloodListAO = JSON.parseObject(packet.getData(), BloodListAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("获取血压参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (bloodListAO.getId()<1) {
					logger.error("获取血压参数错误，收到参数错误，家庭成员不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (bloodListAO.getPageNo()<0) {
					logger.error("获取血压参数错误，收到参数错误，pageNo");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				returnList = bloodPressureService.getBloodPresureByFamilyUserId(bloodListAO.getId(), bloodListAO.getPageNo());
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("获取血压", e);
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
	
		
		logger.info("获取血压结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
}
