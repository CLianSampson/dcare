/**
 * 
 */
package com.dcare.service;

import java.util.Date;
import java.util.List;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.po.BloodGlucose;
import com.dcare.po.BloodGlucoseTarget;

/**
 * @author sampson
 *
 */
public interface BloodGlucoseService {
	
	void addBloodGlucose(BloodGlucose bloodGlucose);
	
	AppErrorEnums update(BloodGlucose bloodGlucose);
	
	List<BloodGlucose> getBloodGlucose(int familyUserId, Date startTime,Date endTime);
	
	void addaddBloodGlucoseTarget(BloodGlucoseTarget bloodGlucoseTarget);
	
	BloodGlucoseTarget getBloodGlucoseTarget(int userId);

}
