package com.dcare.common.message;

public class Packet {
	
//	int platform; //平台号   1安卓 ，2 ios
	
	//token存在数据库中   token可以当做用户一段时间内的唯一标识符

	String token;

	int code;//返回码
	
	String data;//业务数据，存储json数据
	
	
//	public Packet(long messageId, int version, String op, String token, int code, String data){
//		
//	
//		this.token = token;
//		this.code = code;
//		this.data = data;
//	}
//	
//	public Packet(long messageId, String op, String token, int code){
//	
//		this.token = token;
//		this.code = code;
//	}
	
	public Packet(String token,int code,String data){
		this.token = token;
		this.code = code;
		this.data = data;
	}
	
	
	public Packet(){
		
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Packet [token=" + token + ", code=" + code + ", data=" + data
				+ "]";
	}
	
	
	

	
}




