/**
 * 
 */
package com.dcare.service.impl;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dcare.common.code.AttributeConst;
import com.dcare.dao.HealthDO;
import com.dcare.po.Health;
import com.dcare.service.HealthService;

/**
 * @author yaotaxi
 *
 */
@Service
@Transactional
public class HealthServiceImpl implements HealthService  {
	@Autowired
	private HealthDO healthDO;
	
	
	public void start() {
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				try {
					
					getDataFromThirdServer();
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		thread.start();
		
	}
	
	
	
	public void getDataFromThirdServer() {
		HttpClient httpClient  = new HttpClient();
		// 设置代理服务器地址和端口      
		//client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port); 
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https 
		
		String healthString = "健康";
		String newString = null;
		try {
		    newString = java.net.URLEncoder.encode(healthString,"utf-8");
			System.out.println(newString);
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String urlString = "http://www.tngou.net/api/search?name=lore&keyword="+newString;

		
         HttpMethod method=new GetMethod(urlString);
         //使用POST方法
         //HttpMethod method = new PostMethod("http://java.sun.com");
         try {
			httpClient.executeMethod(method);
			
			 //打印服务器返回的状态
	         System.out.println(method.getStatusLine());
	       
	         //数据量大时最好使用getResponseBodyAsStream()
	         //String responseString = method.getResponseBodyAsString();  
	    
	         InputStream inputStream = method.getResponseBodyAsStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(
                     inputStream));
             StringBuffer stringBuffer = new StringBuffer();
             String str = "";
             while ((str = br.readLine()) != null) {
                 stringBuffer.append(str);
             }
             String  response = stringBuffer.toString();
	         
	         ResponseThird responseThird = (ResponseThird) JSON.parseObject(response, ResponseThird.class);
	         
	         System.out.println("after json :"+ responseThird);
	         
	         List<com.dcare.service.impl.HealthServiceImpl.ResponseThird.HealthThird> list = responseThird.getTngou();
	         for (com.dcare.service.impl.HealthServiceImpl.ResponseThird.HealthThird healthThird : list) {
				Health health = new Health();
				health.setId(healthThird.getId());
				health.setCount(healthThird.getCount());
				health.setCreateTime(new Date());
				health.setDescription(healthThird.getDescription());
				health.setFcount(healthThird.getFcount());
				health.setImg(healthThird.getImg());
				health.setLoreclass(healthThird.getLoreclass());
				health.setKeywords(healthThird.getKeywords());
				health.setMessage(healthThird.getMessage());
				health.setRcount(healthThird.getRcount());
				health.setTime(healthThird.getTime());
				health.setTitle(healthThird.getTitle());
				
				healthDO.insertSelective(health);
				
			}
	         
	         //释放连接
	         method.releaseConnection();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	
	
	
	public static class ResponseThird{
		private Boolean status;
		private int   total;
		private List<HealthThird> tngou;
		@Override
		public String toString() {
			return "ResponseThird [status=" + status + ", total=" + total
					+ ", tngou=" + tngou + "]";
		}
		
		public Boolean getStatus() {
			return status;
		}
		public void setStatus(Boolean status) {
			this.status = status;
		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public List<HealthThird> getTngou() {
			return tngou;
		}
		public void setTngou(List<HealthThird> tngou) {
			this.tngou = tngou;
		}
		
		
		public static class HealthThird{
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
			@Override
			public String toString() {
				return "HealthThird [id=" + id + ", title=" + title
						+ ", infoclass=" + loreclass + ", img=" + img
						+ ", description=" + description + ", keywords=" + keywords
						+ ", message=" + message + ", count=" + count + ", fcount="
						+ fcount + ", rcount=" + rcount + ", time=" + time + "]";
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
			
			
			
		}
		

	}






	
	public List<Health> getHealthInfo(int classify, int pageNo) {
		int currentPage = 0;
		if (pageNo!=0) {
			currentPage = (pageNo -1)*AttributeConst.DEFAULT_PAGE_SIZE;
		}
		
		return healthDO.selectById(classify, currentPage, AttributeConst.DEFAULT_PAGE_SIZE);
	}






	
	






	






	





	
	
}
