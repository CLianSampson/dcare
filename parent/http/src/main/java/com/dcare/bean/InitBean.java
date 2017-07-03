package com.dcare.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.dcare.dao.UserDO;
import com.dcare.service.HealthService;


public class InitBean implements ApplicationContextAware{
	
	private static InitBean initBean = null;
	
	@Autowired
	private UserDO userDO;
	
	@Autowired
	private HealthService healthService;
	
	public InitBean(){
		if(null == initBean){
			initBean = this;
		}
	}
	
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		healthService.start();
		
	}
	
	public static UserDO getUssrDao(){
		return initBean.userDO;
	}
	
}
