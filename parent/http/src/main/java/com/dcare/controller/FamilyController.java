
 /**
 * Project Name:passengerImpl
 * File Name:AddPassengerController.java
 * Package Name:com.taxi.controller.ticket
 * Date:2017年2月8日上午10:14:33
 * Copyright (c) 2017, test@126.com All Rights Reserved.
 *
*/

package com.dcare.controller;

import java.io.UnsupportedEncodingException;
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
import com.dcare.ao.AddFamilyAO;
import com.dcare.ao.DeleteFamilyMemberAO;
import com.dcare.ao.EditFamilyMemberAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.Family;
import com.dcare.service.FamilyService;

/**
 * ClassName:AddPassengerController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年2月8日 上午10:14:33 <br/>
 * @author   chenlian
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Controller
public class FamilyController extends BaseController {
	private static Logger logger = Logger.getLogger(FamilyController.class);
	
	@Autowired
	private FamilyService familyService;
	
	/**
	 * 新增家庭用户
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/family/add", method = RequestMethod.POST)
	public void addPassenger(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("新增家庭用户:" + requestString.toString());
		
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
					logger.error("新增家庭用户参数错误，收到参数为空");
					break;
				}
				
				 AddFamilyAO addFamilyAO = null;
				try {
					addFamilyAO = JSON.parseObject(packet.getData(), AddFamilyAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("新增家庭用户参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null == addFamilyAO.getRelation()) {
					logger.error("新增家庭用户参数错误，收到参数错误，关系不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				Family family = new Family();
				family.setBrithday(addFamilyAO.getBrithday());
				family.setCreateTime(new Date());
				family.setGender(addFamilyAO.getGender());
				family.setNickname(addFamilyAO.getNickname());
				family.setRelation(addFamilyAO.getRelation());
				family.setStature(addFamilyAO.getStature());
				family.setStatus(addFamilyAO.getStature());
				family.setWeight(addFamilyAO.getWeight());
				family.setUserId(appUserId);
			
			  
				Object object = familyService.addFamilyMember(family);
				if (object instanceof AppErrorEnums) {
					rtv = (AppErrorEnums) object;
				}else {
					Family returnFamily = (Family) object;
					familyId = returnFamily.getId();
				}
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("新增家庭用户", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		if (rtv != AppErrorEnums.APP_OK) {
			rtvPacket.setData(rtv.getMessage());
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyId", familyId);
			
			rtvPacket.setData(JSON.toJSONString(map));
		}
		
	
		
		logger.info("新增家庭用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	/**
	 * 获取家庭用户
	 * @param packet
	 * @param response
	 */
	@RequestMapping(value = "/family/query", method = RequestMethod.POST)
	public void getPassenger(@RequestBody String requestString, HttpServletResponse response) {
		logger.info("获取家庭用户:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		List<Family> returnList = null;
		try {
			while (true) {


				int userId = TokenUtil.getUserIdByToken(packet.getToken());
				
			    returnList = familyService.getAllFamilyMember(userId);
			    
        		break;
			}

		} catch (Exception e) {
			logger.error("获取家庭用户失败", e);
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
		
		logger.info("获取家庭用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	/**
	 * 删除家庭用户
	 * @param packet
	 * @param response
	 */
	@RequestMapping(value = "/family/delete", method = RequestMethod.POST)
	public void deletePassenger(@RequestBody String requestString, HttpServletResponse response) {
		logger.info("删除家庭用户:" + requestString.toString());
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("删除家庭用户参数错误，收到参数为空");
					break;
				}
				
				DeleteFamilyMemberAO  deleteFamilyMemberAO = null;
				try {
					deleteFamilyMemberAO = JSON.parseObject(packet.getData(), DeleteFamilyMemberAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("删除家庭用户参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
				
				Family family = new Family();
				family.setUserId(appUserId);
				family.setId(deleteFamilyMemberAO.getId());
				
				rtv = familyService.deleteFamilyMember(family);
				
        		break;
			}

		} catch (Exception e) {
			logger.error("删除家庭用户失败", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("删除家庭用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	/**
	 * 编辑家庭用户
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/family/edit", method = RequestMethod.POST)
	public void editPassenger(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("编辑家庭用户:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		logger.info("packet is :" + packet);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("编辑家庭用户参数错误，收到参数为空");
					break;
				}
				
				EditFamilyMemberAO  editFamilyMemberAO = null;
				try {
					editFamilyMemberAO = JSON.parseObject(packet.getData(), EditFamilyMemberAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("编辑家庭用户参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (editFamilyMemberAO.getId()<1) {
					logger.error("编辑家庭用户参数错误");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
				
				Family family = new Family();
				family.setBrithday(editFamilyMemberAO.getBrithday());
				family.setGender(editFamilyMemberAO.getGender());
				family.setNickname(editFamilyMemberAO.getNickname());
				family.setRelation(editFamilyMemberAO.getRelation());
				family.setStature(editFamilyMemberAO.getStature());
				family.setStatus(editFamilyMemberAO.getStature());
				family.setWeight(editFamilyMemberAO.getWeight());
				family.setUserId(appUserId);
				family.setId(editFamilyMemberAO.getId());
				
				rtv = familyService.updateFamilyMerber(family);
				
        		break;
			}

		} catch (Exception e) {
			logger.error("编辑家庭用户失败", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("编辑家庭用户结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
}

