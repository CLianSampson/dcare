package com.dcare.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dcare.ao.GoodsAO;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.StringUtil;
import com.dcare.common.util.TokenUtil;
import com.dcare.po.Goods;
import com.dcare.service.GoodsService;

@Controller
public class GoodsController extends BaseController{
	private static Logger logger = Logger.getLogger(AdviceController.class);
	
	
	
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 商品列表
	 * @param packet
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/goodsList", method = RequestMethod.POST)
	public void appuUserAdvice(@RequestBody String requestString, HttpServletResponse response) throws UnsupportedEncodingException {
		
		logger.info("商品列表:" + requestString.toString());
		
		requestString = new String(requestString.getBytes("ISO-8859-1"), "UTF-8");
		
		logger.info("转码之后的字符串是: " + requestString);
		
		Packet packet = JSON.parseObject(requestString, Packet.class);
		
		AppErrorEnums rtv = AppErrorEnums.APP_OK;// 默认成功
		
		List<Goods> returnList = null;
		try {
			while (true) {
				if (StringUtil.isNullOrBlank(packet.getData())) {
					rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，收到参数为空");
					break;
				}
				
				GoodsAO goodsAO = JSON.parseObject(packet.getData(), GoodsAO.class);
				
				//在shirofilter中已经校验过，此处不用校验
				String token = packet.getToken();
				int appUserId = TokenUtil.getUserIdByToken(token);
				
			   int pageNo = goodsAO.getPageNo();
			   if (pageNo < 0) {
				   rtv = AppErrorEnums.APP_ARGS_ERRORS;
					logger.error("参数错误，pageNo");
					break;
			   }
			   
        		returnList = goodsService.getGoods(pageNo);
        		
        		break;
			}

		} catch (Exception e) {
			logger.error("商品列表失败", e);
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
	
		
		logger.info("商品列表结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}

}
