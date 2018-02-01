package com.dcare.service;

import java.util.List;

import com.dcare.po.Health;
import com.dcare.po.Sms;

public interface SmsService {

	Sms findAppUserSmsByPhone(String phone) ;

	void updateAppUserSms(Sms sms);
	
	
	
}
