/**
 * 
 */
package com.dcare.service;

import com.dcare.po.Upgrade;

/**
 * @author cl
 *
 */
public interface VersionCodeService {
	
	public Upgrade getMaxVsersion( Integer vCode , Integer platform);

//	List<Upgrade> selectAndPage(PageModel<Upgrade> page);
	
	Upgrade selectByKey(Integer id);
	
	Integer update(Upgrade service);
	
	Integer insert(Upgrade service);
	
	Integer delete(Integer id);
}
