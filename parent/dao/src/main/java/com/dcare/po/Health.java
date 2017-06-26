/**
 * 
 */
package com.dcare.po;

import java.util.Date;

/**
 * @author yaotaxi
 *
 */
public class Health {
	private int globalId;
	
	
	//以下为第三方提供
	private int id;
	private String title;//资讯标题
	private int loreclass;//分类
	private String img;//图片
	private String description;//描述
	private String keywords;//关键字
	private String message;//资讯内容
	private int count ;//访问次数
	private int fcount;//收藏数
	private int rcount;//评论读数
	private long time;
	//以上为第三方提供
	
	private Date createTime;
	
	public int getGlobalId() {
		return globalId;
	}
	public void setGlobalId(int globalId) {
		this.globalId = globalId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getLoreclass() {
		return loreclass;
	}
	public void setLoreclass(int loreclass) {
		this.loreclass = loreclass;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getFcount() {
		return fcount;
	}
	public void setFcount(int fcount) {
		this.fcount = fcount;
	}
	public int getRcount() {
		return rcount;
	}
	public void setRcount(int rcount) {
		this.rcount = rcount;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
