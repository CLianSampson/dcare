package com.dcare.ao;

public class LoginAO {
	private String phone;
	
	private String code;
	
	private String deviceId;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "LoginAO [phone=" + phone + ", code=" + code + ", deviceId=" + deviceId + "]";
	}
	
	
}
