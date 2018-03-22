/**
 * 
 */
package com.dcare.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.util.AppPushUtil;
import com.dcare.common.util.MailUtil;
import com.dcare.common.util.SMSUtil;
import com.dcare.common.util.StringUtil;
import com.dcare.dao.ShareUserDO;
import com.dcare.dao.UserDO;
import com.dcare.po.ShareUser;
import com.dcare.po.User;
import com.dcare.service.ShareUserService;

/**
 * @author sampson
 *
 */
@Service
@Transactional
public class ShareUserServiceImpl implements ShareUserService {
	private static Logger logger = Logger.getLogger(ShareUserServiceImpl.class);
	
	@Autowired
	private ShareUserDO shareUserDO;
	
	@Autowired 
	private UserDO userDO;

	
	public AppErrorEnums addShareUser(ShareUser shareUser) {
		
		List<ShareUser> recordInDbList = shareUserDO.selectAllShareUser(shareUser.getUserId());
		
		if (recordInDbList.size() == 3) {
			return AppErrorEnums.APP_ERROR_SHARE_USER_BEYOND;
		}
		
		shareUserDO.insertSelective(shareUser);
		
		return AppErrorEnums.APP_OK;
	}

	
	public AppErrorEnums deleteShareUser(ShareUser shareUser) {
		List<ShareUser> recordInDbList = shareUserDO.selectAllShareUser(shareUser.getUserId());
		
		boolean flag = false;
		for (ShareUser temp : recordInDbList) {
			if (temp.getId().equals(shareUser.getId())) {
				flag = true;
			}
		}
		
		if (!flag) {
			return AppErrorEnums.APP_ERROR_SHARE_USER_NOT_EXIST;
		}
		
		shareUserDO.deleteByPrimaryKey(shareUser.getId());
		
		return AppErrorEnums.APP_OK;
	}

	
	public AppErrorEnums updateShareUser(ShareUser shareUser) {
		
		List<ShareUser> recordInDbList = shareUserDO.selectAllShareUser(shareUser.getUserId());
		
		boolean flag = false;
		for (ShareUser temp : recordInDbList) {
			if (temp.getId().equals(shareUser.getId())) {
				flag = true;
			}
		}
		
		if (!flag) {
			return AppErrorEnums.APP_ERROR_SHARE_USER_NOT_EXIST;
		}
		
		shareUser.setUpdateTime(new Date());
		shareUserDO.updateByPrimaryKeySelective(shareUser);
		
		return AppErrorEnums.APP_OK;
	}

	
	public List<ShareUser> getAllShareUser(int userId) {
		
		return  shareUserDO.selectAllShareUser(userId);
	}


	
	public AppErrorEnums sendSmsByPhone(int userId,String phone,float temperature) {
		List<ShareUser> recordInDbList = shareUserDO.selectAllShareUser(userId);
		
		for (ShareUser temp : recordInDbList) {
			String  temperaturesStr = "" + temperature; 
			
			String lastFourWord = phone.substring(7, 11);
			
			User user = userDO.selectByPhone(temp.getPhone());
			if (!StringUtil.isNullOrBlank(user.getDeviceToken())) {
				try {
					AppPushUtil.push(user.getDeviceToken(), "【呵护总监】高温提醒:用户" + lastFourWord + "实时体温" + temperaturesStr + "度，" + "已超过预设的高温预警值，请您及时关注，并在适当的时候就医观察。");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CommunicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (KeystoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidDeviceTokenFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return AppErrorEnums.APP_OK;
				
			}
			
			
			//发送验证码
			Map<String, Object> map = SMSUtil.sendSMS(temp.getPhone(), new String[] { lastFourWord, temperaturesStr }, SMSUtil.TEMPLATE_SHARE_USER);
			if (!"000000".equals(map.get("statusCode"))) { // 正常返回输出data包体信息（map）
				// 异常返回输出错误码和错误信息
				logger.error("错误码=" + map.get("statusCode") + " 错误信息= " + map.get("statusMsg"));
				return AppErrorEnums.APP_VERIFY_SEND_EXCEEDING;
			}
		}
		
		return AppErrorEnums.APP_OK;
	}
	
	
	public AppErrorEnums sendSmsByMail(int userId,String mail,float temperature) {
		List<ShareUser> recordInDbList = shareUserDO.selectAllShareUser(userId);
		
		for (ShareUser temp : recordInDbList) {
			
			if (StringUtil.isNullOrBlank(temp.getPhone())) {
				
				
				User user = userDO.selectByMail(temp.getMail());
				
				if (StringUtil.isNullOrBlank(user.getDeviceToken())) {
					logger.info("该用户没有上报devicetoken,用邮箱推送");
					
					try {
						MailUtil.sendMail(temp.getMail(), mail+"High temperature alarm" , "The user's temperature exceeds " + temperature + "degrees.");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return AppErrorEnums.APP_ERROR_SEND_MAIL_ERROR;
					}
				}else {
					try {
						AppPushUtil.push(user.getDeviceToken(), "Caremonitor  "+ "High temperature alarm" +  "The user's temperature exceeds " + temperature + "degrees.");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CommunicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeystoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidDeviceTokenFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
			
			
		}
		
		return AppErrorEnums.APP_OK;
		
	}
	
	
}






