/**
 * 
 */
package com.dcare.service;

import java.util.List;

import com.dcare.po.Goods;

/**
 * @author yaotaxi
 *
 */
public interface GoodsService {
	List<Goods> getGoods(int pageNo);
}
