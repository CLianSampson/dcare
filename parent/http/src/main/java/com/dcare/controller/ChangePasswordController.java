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
import com.dcare.ao.ChangePasswordAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.dao.SmsDO;
import com.dcare.dao.UserDO;
import com.dcare.po.Sms;
import com.dcare.po.User;
import com.dcare.service.FamilyService;
import com.dcare.service.UserService;

/**
 * @author apple
 *
 */
@Controller
public class ChangePasswordController extends BaseController {
private static Logger logger = Logger.getLogger(ChangePasswordController.class);
	
	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDO userDO;
	
	@Autowired
	private SmsDO smsDO;
	
	/**
	 * 通过邮箱修改密码
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public void changePassword(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("通过邮箱修改密码:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		int familyId = 0;
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("通过邮箱修改密码参数错误，收到参数为空");
					break;
				}
				
				ChangePasswordAO changePasswordAO = null;
				try {
					changePasswordAO = JSON.parseObject(packet.getData(), ChangePasswordAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("通过邮箱修改密码参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				String password = changePasswordAO.getPassword();
				
				if (StringUtil.isNullOrBlank(password)) {
					logger.error("通过邮箱修改密码参数错误，收到参数错误，密码不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
		  
				if (StringUtil.isNullOrBlank(changePasswordAO.getCode())) {
					logger.error("通过邮箱修改密码参数错误，收到参数错误，验证码不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				
				if (StringUtil.isNullOrBlank(changePasswordAO.getMail())) {
					logger.error("通过邮箱修改密码参数错误，收到参数错误，邮箱不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				
				Sms sms = smsDO.selectByPhone(changePasswordAO.getMail());
				if (!sms.getMsg().equals(changePasswordAO.getCode())) {
					logger.error("通过邮箱修改密码参数错误，收到参数错误，验证码不相等");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				User user = userDO.selectByMail(changePasswordAO.getMail());
				
				user.setPassword(password);
				userDO.updateByPrimaryKey(user);
				
        		break;
			}

		} catch (Exception e) {
			logger.error("通过邮箱修改密码", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
	
		logger.info("通过邮箱修改密码结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
}
