/**
 * 
 */
package com.dcare.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.DeviceTokenAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.User;
import com.dcare.service.UserService;

/**
 * @author apple
 *
 */
@Controller
public class DeviceTokenController extends BaseController {
private static Logger logger = Logger.getLogger(DeviceTokenController.class);
	
	
	@Autowired
	private UserService userService;
	
	/**
	 * 绑定设备号
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/bindDeviceToken", method = RequestMethod.POST)
	public void bindDeviceToken(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("绑定设备号:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，收到参数为空");
					break;
				}
				
				DeviceTokenAO deviceTokenAO = JSON.parseObject(packet.getData(), DeviceTokenAO.class);
				
			   int  platform = deviceTokenAO.getPlatform();
			   if (platform!=1 && platform!=2 && platform!=3) {
				   rtv = AppErrorEnums.APP_ERROR_PLATFORM_NULL_ERROR;
					logger.error("平台号错误");
					break;
			   }
				
			   String deviceToken = deviceTokenAO.getDeviceToken();
			   if (platform==1 && StringUtil.isNullOrBlank(deviceToken)) {
				   rtv = AppErrorEnums.APP_ERROR_DEVICETOKEN_NULL_ERROR;
					logger.error("设备号错误");
					break;
			   }
			   
			  //在shirofilter中已经校验过，此处不用校验
		      String token = packet.getToken();
		      int appUserId = TokenUtil.getUserIdByToken(token);
			 
		      User user = userService.getuserById(appUserId);
		      user.setPaltform(deviceTokenAO.getPlatform());
		      user.setDeviceToken(deviceTokenAO.getDeviceToken());
			
		      userService.update(user);
        	  
		      break;
			}

		} catch (Exception e) {
			logger.error("绑定设备号失败", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());

		logger.info("绑定设备号结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}

}




