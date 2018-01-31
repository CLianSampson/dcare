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
import com.dcare.ao.AddShareUserAO;
import com.dcare.ao.DeleteFamilyMemberAO;
import com.dcare.ao.EditshareUserAO;
import com.dcare.ao.ShareUserSendSmsAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.ShareUser;
import com.dcare.service.ShareUserService;

/**
 * @author sampson
 *
 */
@Controller
public class ShareUserController extends BaseController {
	private static Logger logger = Logger.getLogger(ShareUserController.class);
	
	@Autowired
	private ShareUserService shareUserService;
	
	/**
	 * 新增共享用户
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/shareUser/add", method = RequestMethod.POST)
	public void addPassenger(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("新增共享用户:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("新增共享用户参数错误，收到参数为空");
					break;
				}
				
				 AddShareUserAO addShareUserAO = null;
				try {
					addShareUserAO = JSON.parseObject(packet.getData(), AddShareUserAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("新增共享用户参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (StringUtil.isNullOrBlank(addShareUserAO.getPhone()) && StringUtil.isNullOrBlank(addShareUserAO.getMail())) {
					logger.error("新增共享用户参数错误，收到参数错误，手机号不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				ShareUser shareUser = new ShareUser();
				shareUser.setCreateTime(new Date());
				shareUser.setNickNanme(addShareUserAO.getNickname());
				shareUser.setPhone(addShareUserAO.getPhone());
				shareUser.setMail(addShareUserAO.getMail());
				shareUser.setUserId(appUserId);
			  
				rtv = shareUserService.addShareUser(shareUser);
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("新增共享用户", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("新增共享用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	/**
	 * 获取共享用户
	 * @param packet
	 * @param response
	 */
	@RequestMapping(value = "/shareUser/query", method = RequestMethod.POST)
	public void getPassenger(@RequestBody String requestString, HttpServletResponse response) {
		logger.info("获取共享用户:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		List<ShareUser> returnList = null;
		try {
			while (true) {


				int userId = TokenUtil.getUserIdByToken(packet.getToken());
				
			    returnList = shareUserService.getAllShareUser(userId);
			    
        		break;
			}

		} catch (Exception e) {
			logger.error("获取共享用户失败", e);
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
		
		logger.info("获取共享用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	/**
	 * 删除共享用户
	 * @param packet
	 * @param response
	 */
	@RequestMapping(value = "/shareUser/delete", method = RequestMethod.POST)
	public void deletePassenger(@RequestBody String requestString, HttpServletResponse response) {
		logger.info("删除共享用户:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("删除共享用户参数错误，收到参数为空");
					break;
				}
				
				//与删除家庭用户公用同一个 AO
				DeleteFamilyMemberAO  deleteFamilyMemberAO = null;
				try {
					deleteFamilyMemberAO = JSON.parseObject(packet.getData(), DeleteFamilyMemberAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("删除共享用户参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (deleteFamilyMemberAO.getId() < 1) {
					logger.error("删除共享用户参数错误");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
				
				ShareUser shareUser = new ShareUser();
				shareUser.setUserId(appUserId);
				shareUser.setId(deleteFamilyMemberAO.getId());
				
				rtv = shareUserService.deleteShareUser(shareUser);
				
        		break;
			}

		} catch (Exception e) {
			logger.error("删除共享用户失败", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("删除共享用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	/**
	 * 编辑共享用户
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/shareUser/edit", method = RequestMethod.POST)
	public void editPassenger(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("编辑共享用户:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("编辑共享用户参数错误，收到参数为空");
					break;
				}
				
				EditshareUserAO  editshareUserAO = null;
				try {
					editshareUserAO = JSON.parseObject(packet.getData(), EditshareUserAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("编辑共享用户参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (editshareUserAO.getId()<1) {
					logger.error("编辑共享用户参数错误");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
				
				ShareUser shareUser = new ShareUser();
				shareUser.setId(editshareUserAO.getId());
				shareUser.setNickNanme(editshareUserAO.getNickname());
				shareUser.setPhone(editshareUserAO.getPhone());
				shareUser.setMail(editshareUserAO.getMail());
				shareUser.setUpdateTime(new Date());
				shareUser.setUserId(appUserId);
				
				
				rtv = shareUserService.updateShareUser(shareUser);
				
        		break;
			}

		} catch (Exception e) {
			logger.error("编辑共享用户失败", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("编辑共享用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	/**
	 * 给共享用户发送短信
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/shareUser/sendSms", method = RequestMethod.POST)
	public void sendSms(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("给共享用户发送短信:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("给共享用户发送短信参数错误，收到参数为空");
					break;
				}
				
				ShareUserSendSmsAO  shareUserSendSmsAO = null;
				try {
					shareUserSendSmsAO = JSON.parseObject(packet.getData(), ShareUserSendSmsAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("给共享用户发送短信参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (StringUtil.isNullOrBlank(shareUserSendSmsAO.getPhone())) {
					logger.error("给共享用户发送短信参数错误");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
			
				rtv = shareUserService.sendSms(appUserId,shareUserSendSmsAO.getPhone(),shareUserSendSmsAO.getTemp());
				
        		break;
			}

		} catch (Exception e) {
			logger.error("给共享用户发送短信失败", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("给共享用户发送短信结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
}
