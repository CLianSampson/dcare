
 /**
 * Project Name:passengerImpl
 * File Name:AdviceController.java
 * Package Name:com.taxi.controller
 * Date:2016年12月7日下午3:02:29
 * Copyright (c) 2016, test@126.com All Rights Reserved.
 *
*/

package com.dcare.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.AdviceAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.UserAdvice;
import com.dcare.service.UserAdviceService;
import com.dcare.service.UserService;

/**
 * ClassName:AdviceController <br/>
 * Function:   ADD FUNCTION. <br/>
 * Reason:	   ADD REASON. <br/>
 * Date:     2016年12月7日 下午3:02:29 <br/>
 * @author   ycj
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Controller
public class AdviceController  extends BaseController{
	private static Logger logger = Logger.getLogger(AdviceController.class);
	
	
	
	@Autowired
	private UserAdviceService userAdviceService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户意见反馈
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/advice", method = RequestMethod.POST)
	public void appuUserAdvice(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("用户意见反馈:" + requestString.toString());
		
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
				
				AdviceAO adviceAo = JSON.parseObject(packet.getData(), AdviceAO.class);
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
				
			   String advice = adviceAo.getAdvice();
			   if (StringUtil.isNullOrBlank(advice)) {
				   rtv = AppErrorEnums.APP_ERROR_ADVICE_NULL;
					logger.error("用户建议为空");
					break;
			   }
			   
        		UserAdvice appUserAdvice = new UserAdvice();
        		appUserAdvice.setUserId(appUserId);
        		appUserAdvice.setContent(advice);
        		appUserAdvice.setCreateTime(new Date());
        		userAdviceService.insertUserAdvice(appUserAdvice);
        		
        		break;
			}

		} catch (Exception e) {
			logger.error("用户意见反馈失败", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("用户意见反馈结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
}

