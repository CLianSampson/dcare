package com.dcare.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.DownloadTemperatureAO;
import com.dcare.ao.UploadTemperatureAO;
import com.dcare.ao.UploadTemperatureAO.MemberTemperature;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.Temperature;
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
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/temperature/upload", method = RequestMethod.POST)
	public void uploadTemperature(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("上传温度:" + requestString);
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("上传温度参数错误，收到参数为空");
					break;
				}
				
//				UploadTemperatureAO uploadTemperatureAO = null;
				List<MemberTemperature>  allTemperatures = null;
				try {
//					uploadTemperatureAO = JSON.parseObject(packet.getData(), UploadTemperatureAO.class);
					
					allTemperatures = JSON.parseArray(packet.getData(), MemberTemperature.class);
					
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("上传温度参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				
//				List<MemberTemperature>  allTemperatures = uploadTemperatureAO.getAllTemperatures();
				
				for (MemberTemperature memberTemperature : allTemperatures) {
					int familyUserId = memberTemperature.getId();
					int zone = memberTemperature.getZone();
					List<String> temp = memberTemperature.getTemp();
					
					if (familyUserId < 0) {
						logger.error("上传温度参数错误，收到参数错误,familyUserId :"+familyUserId );
						rtv = AppErrorEnums.APP_ARGS_ERRORS;
						break;
					}
					
					if (null==temp || temp.size()<1) {
						logger.error("上传温度参数错误，收到参数错误,temp :"+ temp );
						rtv = AppErrorEnums.APP_ARGS_ERRORS;
						break;
					}
					
					temperatureService.addTemperature(appUserId, familyUserId, zone, temp);
				}
			  

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
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/temperature/download", method = RequestMethod.POST)
	public void getTemperature(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("获取温度:" + requestString);
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
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
				
				if (downloadTemperatureAO.getId() < 0) {
					logger.error("获取温度参数错误，收到参数错误，familyUerId");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				returnList = temperatureService.geTemperaturesByFamilyUserId(downloadTemperatureAO.getId(), downloadTemperatureAO.getDay());
				
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




