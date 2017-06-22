/**
 * 
 */
package com.dcare.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.LoginAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.dao.FamilyDO;
import com.dcare.dao.UserDO;
import com.dcare.po.Family;
import com.dcare.po.Sms;
import com.dcare.po.User;
import com.dcare.service.SmsService;


/**
 * @author Administrator
 *
 */
@Controller
public class LoginController extends BaseController{
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	
	@Autowired
	private UserDO userDO;
	
	@Autowired
	private FamilyDO familyDO;
	
	@Autowired
	private SmsService smsService;

	/**
	 * 用户登录注册
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestBody String requestString ,HttpServletResponse response) {
		logger.info("登陆:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;//默认成功
		
		boolean loopFlag = true;
		
		String returnToken = null;
		int returnUserId = 0;
		while(loopFlag){
			loopFlag = false;
			
			if (StringUtil.isNullOrBlank(packet.getData())) {
				rtv = AppErrorEnums.APP_ARGS_ERRORS;
				logger.error("登录错误，参数为空");
				break;
			}
			
			LoginAO loginAO = JSON.parseObject(packet.getData(), LoginAO.class);
			
			if (StringUtil.isNullOrBlank(loginAO.getPhone())) {
				rtv = AppErrorEnums.APP_ERROR_PHONE;
				logger.error("登录错误，手机号为空");
				break;
			}
			
			
			//登录时短信验证码一定不为空
			if (StringUtil.isNullOrBlank(loginAO.getCode())) {
				rtv = AppErrorEnums.APP_CODE_WRONG;
				logger.error("登录错误，验证码为空");
				break;	
			}
			
			
			if (StringUtil.isNullOrBlank(loginAO.getDeviceId())) {
				rtv = AppErrorEnums.APP_ARGS_ERRORS;
				logger.error("登录错误，设备号为空");
				break;	
			}
			
				
			//登录时短信验证码一定不为空
			Sms sms = smsService.findAppUserSmsByPhone(loginAO.getPhone());
			if (null == sms || !sms.getMsg().equals(loginAO.getCode())) {
				rtv = AppErrorEnums.APP_CODE_WRONG;
				logger.error("登录错误，验证码错误");
				break;	
			}
			
			User user = userDO.selectByPhone(loginAO.getPhone());
			if (null == user) {

				//新建用户
				user = new User();
				
				user.setPhone(loginAO.getPhone());
				user.setCreateTime(new Date());
				user.setUpdateTime(new Date());
				
				
				userDO.insertSelective(user);
				
				user = userDO.selectByPhone(loginAO.getPhone());
				
				String token = TokenUtil.getToken(user.getId(), loginAO.getDeviceId());
				
				//更新token
				user.setToken(token);
				userDO.updateByPrimaryKeySelective(user);
				
				//增加共享联系人
				Family family = new Family();
				family.setRelation("本人");
				family.setUserId(user.getId());
				familyDO.insertSelective(family);
			}else {
				//更新token
				String token = TokenUtil.getToken(user.getId(), loginAO.getDeviceId());
				
				user.setToken(token);
				userDO.updateByPrimaryKeySelective(user);
			}
			
			returnToken = user.getToken();
			returnUserId = user.getId();
		}
		
		
		
		Packet rtvPacket = getRtv(new Packet(), AppErrorEnums.APP_OK);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", returnUserId);
		
		rtvPacket.setData(JSON.toJSONString(map));
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setToken(returnToken);
		
		returnJson(response, rtvPacket);
	}
	
	
	public static void main(String[] args){
		
	}
	
}




