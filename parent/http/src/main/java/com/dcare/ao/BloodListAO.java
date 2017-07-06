package com.dcare.ao;

import java.util.Date;

import org.apache.log4j.Logger;

import com.dcare.controller.FamilyController;

public class BloodListAO {
	private static Logger logger = Logger.getLogger(BloodListAO.class);
	
	private int id;
	
	private int pageNo;

	private int type; // type = 1，按页码筛选  ，type=2按 startTime,endTime筛选
	
	private Date startTime;
	
	private Date endTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean checkAO(){
		if (type!=1 && type!=2) {
			logger.error("获取血压参数错误，type：" + type);
			return false;
		}
		
		
		if (type==1 && pageNo<0) {
			logger.error("获取血压参数错误，pageNo：" + pageNo);
			return false;
		}
		
		if (type==2 && (null==startTime && null==endTime)) {
			logger.error("获取血压参数错误，startTime：" + startTime  + "endTime :" + endTime);
			return false;
		}
		
		return true;
	}
}
