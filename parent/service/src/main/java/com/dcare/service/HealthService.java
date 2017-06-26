/**
 * 
 */
package com.dcare.service;

import java.util.List;

import com.dcare.po.Health;

/**
 * @author yaotaxi
 *
 */
public interface HealthService {
	
	void getDataFromThirdServer();
	
	void start();
	
	List<Health> getHealthInfo(int classify,int pageNo);
}
