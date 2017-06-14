package com.dcare.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.dcare.dao.UserDO;


public class InitBean implements ApplicationContextAware{
	
	private static InitBean initBean = null;
	
	@Autowired
	private UserDO userDO;
	
	public InitBean(){
		if(null == initBean){
			initBean = this;
		}
	}
	
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
		
	}
	
	public static UserDO getUssrDao(){
		return initBean.userDO;
	}
	
}
