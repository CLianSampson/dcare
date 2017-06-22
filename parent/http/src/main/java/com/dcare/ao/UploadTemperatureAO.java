/**
 * 
 */
package com.dcare.ao;

import java.util.List;



/**
 * @author yaotaxi
 *
 */
public class UploadTemperatureAO {
	private List<MemberTemperature>  allTemperatures;
	
	public List<MemberTemperature> getAllTemperatures() {
		return allTemperatures;
	}
	public void setAllTemperatures(List<MemberTemperature> allTemperatures) {
		this.allTemperatures = allTemperatures;
	}


	public class MemberTemperature{
		private int familyUserId;
		
		private int zone;
		
		private List<String> temp;  //一次性上传13个小时的温度数据，一个小时内包含60条数据

		public int getFamilyUserId() {
			return familyUserId;
		}

		public void setFamilyUserId(int familyUserId) {
			this.familyUserId = familyUserId;
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
