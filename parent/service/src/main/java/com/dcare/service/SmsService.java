package com.dcare.service;

import com.dcare.po.Sms;

public interface SmsService {

	public Sms findAppUserSmsByPhone(String phone) ;

	public void updateAppUserSms(Sms sms);
	
}
