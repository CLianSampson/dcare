/**
 * 
 */
package com.dcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.common.code.AttributeConst;
import com.dcare.dao.UpgradeDO;
import com.dcare.po.Upgrade;
import com.dcare.service.VersionCodeService;

/**
 * @author chenlian
 *
 */
@Service
@Transactional
public class VersionCodeServiceImpl implements VersionCodeService {
	
	@Autowired
	private UpgradeDO upgradeDO;

	public Upgrade getMaxVsersion(Integer vCode, Integer platform) {
		Upgrade search = new Upgrade();
		search.setVersion(vCode);
		search.setPlatform(platform);
	

		Upgrade result = upgradeDO.selectMaxVersion(search);
		
		return result;
		
	}

	public Upgrade selectByKey(Integer id) {
		
		return null;
	}

	public Integer update(Upgrade service) {
		
		return null;
	}

	public Integer insert(Upgrade service) {
		
		return null;
	}

	public Integer delete(Integer id) {
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	

//	@Override
//	public Upgrade getMaxVsersion(Integer vCode , Integer platform , Integer type,Integer appType) {
//
//		//   Auto-generated method stub
//		Upgrade search = new Upgrade();
//		search.setVersion(vCode);
//		search.setPlatform(platform);
//		search.setType(type);
//		search.setAppType(appType);
//
//		Upgrade result = upGradeMapper.selectMaxVersion(search);
//		
//		return result;
//
//	}
//	
//	
////	@Override
////	public List<UpGrade> selectAndPage(PageModel<UpGrade> page) {
////		if (0 == page.getPageSize()) {
////			page.setPageSize(AttributeConst.DEFAULT_PAGE_SIZE);
////		}
////		String aoJson = JsonUtil.toJson(page.getBody());
////		UpGrade record = JsonUtil.fromJson(aoJson, UpGrade.class);
////		return upGradeMapper.selectAndPage(record, page.getPageNo(), page.getPageSize());
////	}
//
//	
//	@Override
//	public Upgrade selectByKey(Integer id) {
//		return upgradeDO.selectByKey(id);
//	}
//
//
//	@Override
//	public Integer update(Upgrade service) {
//		return upgradeDO.update(service);
//	}
//
//
//	@Override
//	public Integer insert(Upgrade service) {
//		return upgradeDO.insert(service);
//	}
//
//
//	@Override
//	public Integer delete(Integer id) {
//		return upgradeDO.deleteByKey(id);
//	}

	
}






