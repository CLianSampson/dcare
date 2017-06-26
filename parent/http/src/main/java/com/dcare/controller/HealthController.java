/**
 * 
 */
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
import com.dcare.ao.AdviceAO;
import com.dcare.ao.HealthInfoAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.Health;
import com.dcare.po.UserAdvice;
import com.dcare.service.HealthService;
import com.dcare.service.UserAdviceService;
import com.dcare.service.UserService;

/**
 * @author yaotaxi
 *
 */
@Controller
public class HealthController extends BaseController {
private static Logger logger = Logger.getLogger(HealthController.class);
	
	
	
	@Autowired
	private HealthService healthService;
	

	/**
	 * 获取健康咨讯
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/healthInfo", method = RequestMethod.POST)
	public void getHealthInfo(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("获取健康咨讯:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		List<Health> returnList = null;
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，收到参数为空");
					break;
				}
				
				HealthInfoAO healthInfoAO = JSON.parseObject(packet.getData(), HealthInfoAO.class);
				
				
				
				int classify = healthInfoAO.getClassify();
				int pageNo = healthInfoAO.getPageNo();
				if (classify<0 || pageNo<1) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误");
					break;
				}
				
				returnList = healthService.getHealthInfo(classify, pageNo);
        		
        		
        		break;
			}

		} catch (Exception e) {
			logger.error("获取健康咨讯:失败", e);
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
		
	
		
		logger.info("获取健康咨讯结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
}
