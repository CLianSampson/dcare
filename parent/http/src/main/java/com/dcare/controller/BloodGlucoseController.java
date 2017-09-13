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
import com.dcare.ao.AddBloodGlucoseAO;
import com.dcare.ao.AddBloodPresureAO;
import com.dcare.ao.BloodGlucoseListAO;
import com.dcare.ao.BloodGlucoseTargetAO;
import com.dcare.ao.BloodListAO;
import com.dcare.ao.EditBloodGlucoseAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.BloodGlucose;
import com.dcare.po.BloodGlucoseTarget;
import com.dcare.po.BloodPressure;
import com.dcare.service.BloodGlucoseService;

/**
 * @author sampson
 *
 */
@Controller
@RequestMapping(value = "/bloodGlucose")
public class BloodGlucoseController extends BaseController {

	private static Logger logger = Logger.getLogger(BloodGlucoseController.class);
	
	@Autowired
	private BloodGlucoseService bloodGlucoseService; 
	
	/**
	 *上传血糖
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void uploadBloodGlucose(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("上传血糖:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("上传血糖参数错误，收到参数为空");
					break;
				}
				
				AddBloodGlucoseAO addBloodGlucoseAO = null;
				try {
					addBloodGlucoseAO = JSON.parseObject(packet.getData(), AddBloodGlucoseAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("上传血糖参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (0 == addBloodGlucoseAO.getId()) {
					logger.error("上传血糖参数错误，收到参数错误，家庭成员不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				BloodGlucose bloodGlucose = new BloodGlucose();
				bloodGlucose.setBackup(addBloodGlucoseAO.getBackup());
				bloodGlucose.setCreatTime(new Date());
				bloodGlucose.setFamilyUserId(addBloodGlucoseAO.getId());
				bloodGlucose.setGlucose(addBloodGlucoseAO.getGlucose());
				bloodGlucose.setMeal(addBloodGlucoseAO.getMeal());
				bloodGlucose.setMedicine(addBloodGlucoseAO.getMedicine());
				
				
				bloodGlucoseService.addBloodGlucose(bloodGlucose);
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("上传血糖", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("上传血糖结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	/**
	 *编辑血糖
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public void editBloodGlucose(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("编辑血糖:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("编辑血糖数错误，收到参数为空");
					break;
				}
				
				EditBloodGlucoseAO editBloodGlucoseAO = null;
				try {
					editBloodGlucoseAO = JSON.parseObject(packet.getData(), EditBloodGlucoseAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("编辑血糖错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (0==editBloodGlucoseAO.getId() || 0==editBloodGlucoseAO.getRecordid()) {
					logger.error("编辑血糖参数错误，收到参数错误，家庭成员不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				BloodGlucose bloodGlucose = new BloodGlucose();
				bloodGlucose.setBackup(editBloodGlucoseAO.getBackup());
				bloodGlucose.setCreatTime(new Date());
				bloodGlucose.setFamilyUserId(editBloodGlucoseAO.getId());
				bloodGlucose.setGlucose(editBloodGlucoseAO.getGlucose());
				bloodGlucose.setMeal(editBloodGlucoseAO.getMeal());
				bloodGlucose.setMedicine(editBloodGlucoseAO.getMedicine());
				bloodGlucose.setId(editBloodGlucoseAO.getRecordid());
				
				
				rtv = bloodGlucoseService.update(bloodGlucose);
				
        		break;
			}

		} catch (Exception e) {
			logger.error("编辑血糖", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("编辑血糖结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	
	
	/**
	 *获取血糖
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void getBloodPresureList(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("获取血糖:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		List<BloodGlucose> returnList = null;
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("获取血糖参数错误，收到参数为空");
					break;
				}
				
				BloodGlucoseListAO bloodGlucoseListAO = null;
				try {
					bloodGlucoseListAO = JSON.parseObject(packet.getData(), BloodGlucoseListAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("获取血糖参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (bloodGlucoseListAO.getId()<1) {
					logger.error("获取血糖参数错误，收到参数错误，家庭成员不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null == bloodGlucoseListAO.getStartTime()) {
					logger.error("获取血糖参数错误，收到参数错误，开始时间不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				if (null == bloodGlucoseListAO.getEndTime()) {
					logger.error("获取血糖参数错误，收到参数错误，结束时间不能为空");
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
			
				returnList = bloodGlucoseService.getBloodGlucose(bloodGlucoseListAO.getId(), bloodGlucoseListAO.getStartTime(), bloodGlucoseListAO.getEndTime());
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("获取血糖", e);
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
	
		
		logger.info("获取血糖结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	
	/**
	 *设置血糖目标值
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/targetEdit", method = RequestMethod.POST)
	public void targetEdit(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("设置血糖目标值:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("设置血糖目标值参数错误，收到参数为空");
					break;
				}
				
				BloodGlucoseTargetAO bloodGlucoseTargetAO = null;
				try {
					bloodGlucoseTargetAO = JSON.parseObject(packet.getData(), BloodGlucoseTargetAO.class);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("设置血糖目标值参数错误，收到参数格式错误",e);
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					break;
				}
				
				
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
        	
				BloodGlucoseTarget bloodGlucoseTarget = new BloodGlucoseTarget();
				bloodGlucoseTarget.setUserId(appUserId);
				
				bloodGlucoseTarget.setAfterMealMax(bloodGlucoseTargetAO.getAfterMealMax());
				bloodGlucoseTarget.setAfterMealMin(bloodGlucoseTargetAO.getAfterMealMin());
				bloodGlucoseTarget.setBeforeMealMax(bloodGlucoseTargetAO.getBeforeMealMax());
				bloodGlucoseTarget.setBeforeSleepMax(bloodGlucoseTargetAO.getBeforeSleepMax());
				bloodGlucoseTarget.setBeforeSleepMin(bloodGlucoseTargetAO.getBeforeSleepMin());
				bloodGlucoseTarget.setLimosisMax(bloodGlucoseTargetAO.getLimosisMax());
				bloodGlucoseTarget.setLimosisMin(bloodGlucoseTargetAO.getLimosisMin());
				
				
				bloodGlucoseService.addaddBloodGlucoseTarget(bloodGlucoseTarget);
				
				
        		break;
			}

		} catch (Exception e) {
			logger.error("设置血糖目标值", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		rtvPacket.setData(rtv.getMessage());
	
		
		logger.info("设置血糖目标值结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
	
	/**
	 *获取血糖目标
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/targetQuery", method = RequestMethod.POST)
	public void targetQuery(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("获取血糖目标:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		BloodGlucoseTarget bloodGlucoseTarget = null;
		try {
			while (true) {
		
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
			
				bloodGlucoseTarget = bloodGlucoseService.getBloodGlucoseTarget(appUserId);
				
        		break;
			}

		} catch (Exception e) {
			logger.error("获取血糖目标", e);
			rtv = AppErrorEnums.APP_ERROR;
		}
		
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		
		rtvPacket.setCode(rtv.getCode());
		if (rtv == AppErrorEnums.APP_OK) {
			rtvPacket.setData(JSON.toJSONString(bloodGlucoseTarget));
		}else {
			rtvPacket.setData(rtv.getMessage());
		}
	
		
		logger.info("获取血糖目标结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
}
