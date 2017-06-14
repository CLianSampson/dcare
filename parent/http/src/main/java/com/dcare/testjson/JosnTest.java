package com.dcare.testjson;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

public class JosnTest {
	
	
	public String getTime(String json){
		
		Information information  = JSON.parseObject(json, Information.class);
		
		
		ResultData resultData = information.getResult_data();
		List<Map<String, Object>>  list = resultData.getOrderList();
		
		for (Map<String, Object> map : list) {
			Set<String> set = map.keySet();
			
			for(String timeKey : set )   {     
			    System.out.println(timeKey );  
			    
			    System.out.println(map.get(timeKey));
			}  
			
		}
		
		
		return null;
	}
	
	
	 public static void main(String args[]) { 
	       JosnTest jsonTest = new JosnTest();
	       
	       String json = "{\"message\": \"获取成功!\",\"result_code\": \"0\",\"result_data\": {\"totalPage\": 10, \"orderList\": [{\"2017-02\": [{\"id\": 10532, \"startAdd\": \"华丰商务中心A座\", \"endAdd\": \"白石洲(地铁站)\", \"paymoney\": 10,\"addTime\": 1488264082560} ] } ]}}";
	 
	       jsonTest.getTime(json);
	 } 
	
}
