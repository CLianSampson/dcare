package com.dcare.testjson;

import java.util.List;
import java.util.Map;

public class ResultData {
	private int totalPage;
	
	private List<Map<String, Object>>  orderList;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<Map<String, Object>> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Map<String, Object>> orderList) {
		this.orderList = orderList;
	}
	
	
	
}
