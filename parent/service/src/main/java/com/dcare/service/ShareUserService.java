/**
 * 
 */
package com.dcare.service;

import java.util.List;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.po.ShareUser;

/**
 * @author sampson
 *
 */
public interface ShareUserService {
	AppErrorEnums addShareUser(ShareUser shareUser);
	
	AppErrorEnums deleteShareUser(ShareUser shareUser);
	
	AppErrorEnums updateShareUser(ShareUser shareUser);
	
	List<ShareUser> getAllShareUser(int userId);
	
	AppErrorEnums sendSms(int userId,String phone,float temperature);
}
