package com.dcare.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.SmsAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.MailUtil;
import com.dcare.common.util.SMSUtil;
import com.dcare.common.util.StringUtil;
import com.dcare.po.Sms;
import com.dcare.service.SmsService;

@Controller
public class SmsController extends BaseController {
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	protected enum SMSType {
		VERIFYCODE, VOICEVERIFY, CARRY
	}
	
	@Autowired
	private SmsService smsService;
	
	/**
	 * 发送短信验证码
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/verifyCode/smsVerify", method = RequestMethod.POST)
	public void SMSVerify(@RequestBody String requestString, HttpServletResponse response) {
		logger.info("请求短信验证码:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;//默认成功
		
		try {
			while (true) {
				if (null == packet) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，收到参数为空");
					break;
				}
				
				logger.info("packet is :" + packet);
				
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，收到参数为空");
					break;
				}
				
				
				
				SmsAO smsAO = JSON.parseObject(packet.getData(), SmsAO.class);
				
				String userPhone = smsAO.getPhone();
				
				//手机号校验
				if (!StringUtil.isPhoneNumber(userPhone)) {
					rtv = AppErrorEnums.APP_ERROR_PHONE;
					logger.error("发送短信验证码错误，手机号格式错误");
					break;
				}
				
				
				String code = StringUtil.randomVerCode(4);// 生成随机数
				
				//发送验证码
				Map<String, Object> map = SMSUtil.sendSMS(userPhone, new String[] { code, SMSUtil.CLOOPEN_VALID_TIME }, SMSUtil.TEMPLATE_CODE);
				if (!"000000".equals(map.get("statusCode"))) { // 正常返回输出data包体信息（map）
					// 异常返回输出错误码和错误信息
					logger.error("错误码=" + map.get("statusCode") + " 错误信息= " + map.get("statusMsg"));
					rtv = AppErrorEnums.APP_VERIFY_SEND_EXCEEDING;
					break;
				}
				
				
				// 添加短信发送记录   先把短信添加到数据库中
				Sms appUserSms = new Sms();
				appUserSms.setCategory(SMSType.VERIFYCODE.ordinal());
				appUserSms.setCreateTime(new Date());
				appUserSms.setMsg(code);
				appUserSms.setPhone(userPhone);
				smsService.updateAppUserSms(appUserSms);

				break;
			}
		} catch (Exception e) {
			logger.error("添加短信发送记录失败", e);
			
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = getRtv(new Packet(), rtv);
	
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
		
		returnJson(response, rtvPacket);
	}
	
	/**
	 * 发送语音验证码
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/verifyCode/voiceVerify")
	public void VoiceVerify(@RequestBody String requestString, HttpServletResponse response) {
		logger.info("请求短信验证码:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;//默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，收到参数为空");
					break;
				}
				
				SmsAO smsAO = JSON.parseObject(packet.getData(), SmsAO.class);
				
				String userPhone = smsAO.getPhone();
				
				//手机号校验
				if (!StringUtil.isPhoneNumber(userPhone)) {
					rtv = AppErrorEnums.APP_ERROR_PHONE;
					logger.error("发送短信验证码错误，手机号格式错误");
					break;
				}
				
				
				String code = StringUtil.randomVerCode(4);// 生成随机数

				//发送验证码
				Map<String, Object> map = SMSUtil.sendVoice(userPhone, code);
				if (!"000000".equals(map.get("statusCode"))) { // 正常返回输出data包体信息（map）
					// 异常返回输出错误码和错误信息
					logger.error("错误码=" + map.get("statusCode") + " 错误信息= " + map.get("statusMsg"));
					rtv = AppErrorEnums.APP_VERIFY_SEND_EXCEEDING;
					break;
				}
				
				
				// 添加短信发送记录
				// 添加短信发送记录   先把短信添加到数据库中
				Sms appUserSms = new Sms();
				appUserSms.setCategory(SMSType.VERIFYCODE.ordinal());
				appUserSms.setCreateTime(new Date());
				appUserSms.setMsg(code);
				appUserSms.setPhone(userPhone);
				smsService.updateAppUserSms(appUserSms);

				break;
			}
		} catch (Exception e) {
			logger.error("添加短信发送记录失败", e);
			
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
		
		returnJson(response, rtvPacket);
	}
	
	
	
	/**
	 * 通过邮箱获取验证码
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/verifyCode/emailVerify")
	public void MailVerify(@RequestBody String requestString, HttpServletResponse response) {
		logger.info("请求邮箱验证码:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;//默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，收到参数为空");
					break;
				}
				
				//不另外加参数，用phone代替mail
				SmsAO smsAO = JSON.parseObject(packet.getData(), SmsAO.class);
				
				String userMail = smsAO.getPhone();
				
				//省略邮箱校验
				
				
				String code = StringUtil.randomVerCode(4);// 生成随机数

				//发送邮箱验证码
//				Map<String, Object> map = SMSUtil.sendVoice(userPhone, code);
//				if (!"000000".equals(map.get("statusCode"))) { // 正常返回输出data包体信息（map）
//					// 异常返回输出错误码和错误信息
//					logger.error("错误码=" + map.get("statusCode") + " 错误信息= " + map.get("statusMsg"));
//					rtv = AppErrorEnums.APP_VERIFY_SEND_EXCEEDING;
//					break;
//				}
				
				MailUtil.sendMail(userMail, "confirm your registration", code);
				
				
				// 添加短信发送记录
				// 添加短信发送记录   先把短信添加到数据库中
				Sms appUserSms = new Sms();
				appUserSms.setCategory(SMSType.VERIFYCODE.ordinal());
				appUserSms.setCreateTime(new Date());
				appUserSms.setMsg(code);
				appUserSms.setPhone(userMail);
				smsService.updateAppUserSms(appUserSms);

				break;
			}
		} catch (Exception e) {
			logger.error("添加邮箱发送记录失败", e);
			
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
		
		returnJson(response, rtvPacket);
	}
	
}
