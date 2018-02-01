package com.dcare.dao;

import com.dcare.po.Sms;

public interface SmsDO {
	int deleteByKey(String phone);

    int insert(Sms record);

    int update(Sms record);
    
    Sms selectByPhone(String phone);
    
    
   
}
