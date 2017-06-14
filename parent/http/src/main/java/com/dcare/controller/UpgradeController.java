
 /**
 * Project Name:passengerImpl
 * File Name:UpgradeController.java
 * Package Name:com.taxi.controller
 * Date:2016年12月7日下午2:59:54
 * Copyright (c) 2016, test@126.com All Rights Reserved.
 *
*/

package com.dcare.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.UpgradeAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.po.Upgrade;
import com.dcare.service.VersionCodeService;

/**
 * ClassName:UpgradeController <br/>
 * Function:   ADD FUNCTION. <br/>
 * Reason:	   ADD REASON. <br/>
 * Date:     2016年12月7日 下午2:59:54 <br/>
 * @author   ycj
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Controller
public class UpgradeController extends BaseController{
	private static Logger logger = Logger.getLogger(UpgradeController.class);
	
	@Autowired
	private VersionCodeService versionCodeService;

	/**
	 * 检查客户端版本更新
	 * @param packet
	 * @param response
	 */
	@RequestMapping(value = "/getLatestVersion", method = RequestMethod.POST)
	public void checkVersionCode(@RequestBody  String requestString, HttpServletResponse response){
		
		logger.info("获取最新版本号:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		Upgrade upGrade = null;
		
		try {
			while (true) {
				
				if (StringUtil.isNullOrBlank(packet.getData())) {
					logger.error("获取版本号错误，参数为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				UpgradeAO upgradeAO = JSON.parseObject(packet.getData(), UpgradeAO.class);
				
				if (null ==  upgradeAO ) {
					logger.error("获取版本号错误，参数为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null==upgradeAO.getPlatform() || 0>upgradeAO.getPlatform()) {
					logger.error("平台号为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null==upgradeAO.getvCode() || 0>upgradeAO.getvCode()) {
					logger.error("版本号为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				upGrade  = versionCodeService.getMaxVsersion(upgradeAO.getvCode(), upgradeAO.getPlatform());
				
        		break;
			}

		} catch (Exception e) {
			logger.error("获取最新版本号", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		
		
		if (rtv == AppErrorEnums.APP_OK) {
			rtvPacket.setData(JSON.toJSONString(upGrade));
		}else {
			rtvPacket.setData(rtv.getMessage());
		}
		
		logger.info("获取最新版本号结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
}

