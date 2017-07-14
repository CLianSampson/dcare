/**
 * 
 */
package com.dcare.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.util.SMSUtil;
import com.dcare.common.util.StringUtil;
import com.dcare.dao.ShareUserDO;
import com.dcare.po.ShareUser;
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

	
	public AppErrorEnums addShareUser(ShareUser shareUser) {
		
		List<ShareUser> recordInDbList = shareUserDO.selectAllShareUser(shareUser.getUserId());
		
		if (recordInDbList.size() == 3) {
			return AppErrorEnums.APP_ERROR_SHARE_USER_BEYOND;
		}
		
		
		return AppErrorEnums.APP_OK;
	}

	
	public AppErrorEnums deleteShareUser(ShareUser shareUser) {
		
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
		
		shareUserDO.updateByPrimaryKeySelective(shareUser);
		
		return AppErrorEnums.APP_OK;
	}

	
	public List<ShareUser> getAllShareUser(int userId) {
		
		return  shareUserDO.selectAllShareUser(userId);
	}


	
	public AppErrorEnums sendSms(int userId) {
		List<ShareUser> recordInDbList = shareUserDO.selectAllShareUser(userId);
		
		for (ShareUser temp : recordInDbList) {
			String code = StringUtil.randomVerCode(4);// 生成随机数
			
			String phone = temp.getPhone();
			
			//发送验证码
			Map<String, Object> map = SMSUtil.sendSMS(phone, new String[] { code, SMSUtil.CLOOPEN_VALID_TIME }, SMSUtil.TEMPLATE_CODE);
			if (!"000000".equals(map.get("statusCode"))) { // 正常返回输出data包体信息（map）
				// 异常返回输出错误码和错误信息
				logger.error("错误码=" + map.get("statusCode") + " 错误信息= " + map.get("statusMsg"));
				return AppErrorEnums.APP_VERIFY_SEND_EXCEEDING;
				
			}
		}
		
		return AppErrorEnums.APP_OK;
		
	}
	
}
