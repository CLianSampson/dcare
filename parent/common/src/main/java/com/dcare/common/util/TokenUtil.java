package com.dcare.common.util;

import java.util.UUID;

import com.dcare.common.code.AttributeConst;

public class TokenUtil {
	
	//由 （  用户id + deviceid + 随机数 ）base64 组成
	
	public static String getToken(int userId ,String deviceId){
		
		String random = UUID.randomUUID().toString();
		
		return Base64.encode(userId + AttributeConst.SEPREATE_UNDERLINE + deviceId +  AttributeConst.SEPREATE_UNDERLINE + random);
		
	}
	
	
	public static int getUserIdByToken(String token){
		byte[] decodeByte = Base64.decode(token);
		String decodeString = new String(decodeByte);
		
		String[] arry = decodeString.split(AttributeConst.SEPREATE_UNDERLINE);
		if (arry.length<3 || arry==null) {
			return 0;
		}
		
		return Integer.valueOf(arry[0]).intValue();
	}
	
	public static String getDeviceIdByToken(String token){
		
		String[] arry = token.split(AttributeConst.SEPREATE_UNDERLINE);
		if (arry.length<3 || arry==null) {
			return null;
		}
		
		return arry[1];
	}
		
}




