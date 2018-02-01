package com.dcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.dao.SmsDO;
import com.dcare.po.Sms;
import com.dcare.service.SmsService;



@Service
@Transactional
public class SmsServiceImpl implements SmsService{
	@Autowired
	private SmsDO smsDAO;


	public void updateAppUserSms(Sms appUserSms) {
		
		String phone = appUserSms.getPhone();
		Sms appUserSmsInDB = smsDAO.selectByPhone(phone);
		if (appUserSmsInDB == null) {
			//没有该记录则添加
			smsDAO.insert(appUserSms);
		}else{
			//有则更新记录
			smsDAO.update(appUserSms);
		}
	}

	public Sms findAppUserSmsByPhone(String phone) {
		return smsDAO.selectByPhone(phone);
	}

	
	
	
	
}
