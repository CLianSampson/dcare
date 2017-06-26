
package com.dcare.ao;

import java.util.List;



/**
 * @author yaotaxi
 *
 */
public  class UploadTemperatureAO {
	private List<MemberTemperature>  allTemperatures;
	
	public List<MemberTemperature> getAllTemperatures() {
		return allTemperatures;
	}
	public void setAllTemperatures(List<MemberTemperature> allTemperatures) {
		this.allTemperatures = allTemperatures;
	}


	public static class MemberTemperature{
		private int id; //家庭用户id
		
		private int zone;
		
		private List<String> temp;  //一次性上传13个小时的温度数据，一个小时内包含60条数据

		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getZone() {
			return zone;
		}

		public void setZone(int zone) {
			this.zone = zone;
		}

		public List<String> getTemp() {
			return temp;
		}

		public void setTemp(List<String> temp) {
			this.temp = temp;
		}
	}
	
}




