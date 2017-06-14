package com.dcare.ao;

import com.dcare.common.util.StringUtil;

public class SmsAO {
	private String phone;
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean validateAO(){
		if (StringUtil.isNullOrBlank(phone)) {
			return false;
		}
		
		return true;
	}
	
	
}
