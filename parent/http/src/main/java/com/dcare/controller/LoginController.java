/**
 * 
 */
package com.dcare.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.dcare.ao.MailRegisterAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.code.AttributeConst;
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
		List<Family> returnFamily = null; //本人
		while(loopFlag){
			loopFlag = false;
			
			if (StringUtil.isNullOrBlank(packet.getData())) {
				rtv = AppErrorEnums.APP_ARGS_ERRORS;
				logger.error("登录错误，参数为空");
				break;
			}
			
			LoginAO loginAO = JSON.parseObject(packet.getData(), LoginAO.class);
			
			if (StringUtil.isNullOrBlank(loginAO.getPhone()) && StringUtil.isNullOrBlank(loginAO.getMail())) {
				rtv = AppErrorEnums.APP_ERROR_PHONE;
				logger.error("登录错误，手机号和邮箱同时为空");
				break;
			}
			
			if (!StringUtil.isNullOrBlank(loginAO.getPhone()) && !StringUtil.isNullOrBlank(loginAO.getMail())) {
				rtv = AppErrorEnums.APP_ERROR_PHONE;
				logger.error("登录错误，手机号和邮箱同时不为空");
				break;
			}
			
			if (StringUtil.isNullOrBlank(loginAO.getDeviceId())) {
				rtv = AppErrorEnums.APP_ARGS_ERRORS;
				logger.error("登录错误，设备号为空");
				break;
			}
			
			User user = null;
			//邮箱登陆
			if (!StringUtil.isNullOrBlank(loginAO.getMail())) {
				rtv = mailLogin(loginAO);
				if (rtv != AppErrorEnums.APP_OK) {
					break;
				}
			    user = userDO.selectByMail(loginAO.getMail());
			}else {
			//手机号登陆	
				rtv = phoneLogin(loginAO);
				if (rtv != AppErrorEnums.APP_OK) {
					break;
				}
				user = userDO.selectByPhone(loginAO.getPhone());
			}
			
			returnFamily = familyDO.selectByUserId(user.getId());
//			for (Family family : families) {
//				if (family.getRelation().equals(AttributeConst.RELATION_MYSELF)) {
//					returnFamily = family;
//				}
//			}
			
			
			returnToken = user.getToken();
			returnUserId = user.getId();
		}
		
		Packet rtvPacket = getRtv(new Packet(), AppErrorEnums.APP_OK);
		if (rtv == AppErrorEnums.APP_OK) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", returnUserId);
			map.put("family", returnFamily);
			
			rtvPacket.setData(JSON.toJSONString(map));
		}else {
			rtvPacket.setData(rtv.getMessage());
		}
		
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setToken(returnToken);
		
		logger.info("用户登陆结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	
	
	private AppErrorEnums mailLogin(LoginAO loginAO){
		if (StringUtil.isNullOrBlank(loginAO.getPassword())) {
			return AppErrorEnums.APP_PASSWD_ERROR;
		}
		
		User user = userDO.selectByMail(loginAO.getMail());
		if (null == user) {

			return AppErrorEnums.APP_NOT_EXIST_USER;
		}else {
			
			if (!user.getPassword().equals(loginAO.getPassword())) {
				return AppErrorEnums.APP_PASSWD_ERROR;
			}
			
			//更新token
			String token = TokenUtil.getToken(user.getId(), loginAO.getDeviceId());
			
			user.setToken(token);
			userDO.updateByPrimaryKeySelective(user);
		}
			
		return AppErrorEnums.APP_OK;
	}
	
	
	private AppErrorEnums phoneLogin(LoginAO loginAO){
		AppErrorEnums rtv;
		//登录时短信验证码一定不为空
		if (StringUtil.isNullOrBlank(loginAO.getCode())) {
			rtv = AppErrorEnums.APP_CODE_WRONG;
			logger.error("登录错误，验证码为空");
			
			return rtv;
		}
		
		if (loginAO.getPhone().equals("17888888888") && loginAO.getCode().equals("8888")) {
			//ios审核账号
		}else {
			//登录时短信验证码一定不为空
			Sms sms = smsService.findAppUserSmsByPhone(loginAO.getPhone());
			if (null == sms || !sms.getMsg().equals(loginAO.getCode())) {
				rtv = AppErrorEnums.APP_CODE_WRONG;
				logger.error("登录错误，验证码错误");
				return rtv;
			}
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
			family.setRelation(AttributeConst.RELATION_MYSELF);
			family.setUserId(user.getId());
			family.setNickname(AttributeConst.DEFAULT_NICKNAME);
			familyDO.insertSelective(family);
		}else {
			//更新token
			String token = TokenUtil.getToken(user.getId(), loginAO.getDeviceId());
			
			user.setToken(token);
			userDO.updateByPrimaryKeySelective(user);
		}

		return AppErrorEnums.APP_OK;
	}
	
	/**
	 * 通过邮箱注册
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(@RequestBody String requestString ,HttpServletResponse response) {
		logger.info("通过邮箱注册:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;//默认成功
		
		boolean loopFlag = true;
		
		String returnToken = null;
		int returnUserId = 0;
		List<Family> returnFamily = null; //本人
		while(loopFlag){
			loopFlag = false;
			
			if (StringUtil.isNullOrBlank(packet.getData())) {
				rtv = AppErrorEnums.APP_ARGS_ERRORS;
				logger.error("通过邮箱注册错误，参数为空");
				break;
			}
			
			MailRegisterAO mailRegisterAO = JSON.parseObject(packet.getData(), MailRegisterAO.class);
			
			if (StringUtil.isNullOrBlank(mailRegisterAO.getMail())) {
				rtv = AppErrorEnums.APP_ERROR_PHONE;
				logger.error("通过邮箱注册错误，邮箱为空");
				break;
			}
			
			
			//通过邮箱注册时短信验证码一定不为空
			if (StringUtil.isNullOrBlank(mailRegisterAO.getCode())) {
				rtv = AppErrorEnums.APP_CODE_WRONG;
				logger.error("通过邮箱注册错误，验证码为空");
				break;	
			}
			
			//校验验证码
			Sms sms = smsService.findAppUserSmsByPhone(mailRegisterAO.getMail());
			if (null == sms) {
				rtv = AppErrorEnums.APP_CODE_WRONG;
				logger.error("通过邮箱注册错误，邮箱不存在");
				break;	
			}
			
			if (!sms.getMsg().equals(mailRegisterAO.getCode())) {
				rtv = AppErrorEnums.APP_CODE_WRONG;
				logger.error("通过邮箱注册错误，验证码错误");
				break;	
			}
			
			if (StringUtil.isNullOrBlank(mailRegisterAO.getPassword())) {
				rtv = AppErrorEnums.APP_CODE_WRONG;
				logger.error("通过邮箱注册密码错误，密码为空");
				break;	
			}
			
			if (StringUtil.isNullOrBlank(mailRegisterAO.getDeviceId())) {
				rtv = AppErrorEnums.APP_ARGS_ERRORS;
				logger.error("登录错误，设备号为空");
				break;	
			}
			
			
			User user = userDO.selectByMail(mailRegisterAO.getMail());
			if (null == user) {

				//新建用户
				user = new User();
				
				user.setMail(mailRegisterAO.getMail());
				user.setPassword(mailRegisterAO.getPassword());
				user.setCreateTime(new Date());
				user.setUpdateTime(new Date());
				
				
				userDO.insertSelective(user);
				
				user = userDO.selectByMail(mailRegisterAO.getMail());
				returnUserId = user.getId();
				
				String token = TokenUtil.getToken(user.getId(), mailRegisterAO.getDeviceId());
				
				//更新token
				user.setToken(token);
				userDO.updateByPrimaryKeySelective(user);
				
				//增加共享联系人
				Family family = new Family();
				family.setRelation(AttributeConst.RELATION_MYSELF);
				family.setUserId(user.getId());
				family.setNickname(AttributeConst.DEFAULT_NICKNAME);
				familyDO.insertSelective(family);
			}else {
				rtv = AppErrorEnums.APP_ERROR_MAIL_EXIST;
			}
		
		}
		

		Packet rtvPacket = getRtv(new Packet(), AppErrorEnums.APP_OK);
		
		if (rtv == AppErrorEnums.APP_OK) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", returnUserId);
//			map.put("family", returnFamily);
			
			rtvPacket.setData(JSON.toJSONString(map));
		}else {
			rtvPacket.setData(rtv.getMessage());
		}
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setToken(returnToken);
		
		logger.info("用户登陆结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	

	public static void main(String[] args){
		
	}
	
}




