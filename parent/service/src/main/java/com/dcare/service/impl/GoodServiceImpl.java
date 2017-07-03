/**
 * 
 */
package com.dcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcare.common.code.AttributeConst;
import com.dcare.dao.GoodsDO;
import com.dcare.po.Goods;
import com.dcare.service.GoodsService;

/**
 * @author yaotaxi
 *
 */
@Service
public class GoodServiceImpl implements GoodsService {
	@Autowired
	private GoodsDO goodsDO;

	
	public List<Goods> getGoods(int pageNo) {
		int currentPage = 0;
		if (pageNo - 1 > 0) {
			currentPage = (pageNo - 1) * AttributeConst.DEFAULT_PAGE_SIZE;
		}
		
		int totalPage = currentPage + AttributeConst.DEFAULT_PAGE_SIZE;
		
		return goodsDO.selectLimitPageNo(currentPage, totalPage);
		
		
	}

}
