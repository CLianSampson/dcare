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
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.BloodGlucoseTarget;
import com.dcare.po.User;
import com.dcare.service.UserService;

/**
 * @author apple
 *
 */
@Controller
public class UserInfoController extends BaseController {
	private static Logger logger = Logger.getLogger(UserInfoController.class);
	
	@Autowired
	private UserService userService;
	
	
	
	/**
	 *获取用户信息
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.POST)
	public void getUserInfo(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("获取用户信息:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		User user = null;
		try {
			while (true) {
		
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
			
				user = userService.getuserById(appUserId);
				user.setPassword("");
				
        		break;
			}

		} catch (Exception e) {
			logger.error("获取用户信息", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		if (rtv == AppErrorEnums.APP_OK) {
			rtvPacket.setData(JSON.toJSONString(user));
		}else {
			rtvPacket.setData(rtv.getMessage());
		}
	
		
		logger.info("获取用户信息 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
}
