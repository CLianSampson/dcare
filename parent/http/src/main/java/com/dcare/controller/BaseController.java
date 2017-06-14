package com.dcare.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.message.Packet;
import com.dcare.common.util.PrintWriterUtil;

@Controller
public abstract class BaseController {
		
	/** 返回数据 */
	protected void returnJson(HttpServletResponse response, Packet packet) {
		PrintWriterUtil.writeResultToClient(response, JSON.toJSONString(packet));
	}
	
	
	protected Packet getRtv(Packet packet, AppErrorEnums enums){
		Packet rtvPacket = new Packet();
		rtvPacket.setToken(packet.getToken());
		rtvPacket.setCode(enums.getCode());
		return rtvPacket;
	}
	
}
