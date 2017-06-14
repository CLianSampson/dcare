package com.dcare.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.dao.TemperatureDO;
import com.dcare.po.Temperature;
import com.dcare.service.TemperatureService;

@Service
@Transactional
public class TemperatureServiceImpl implements TemperatureService {
	
	@Autowired
	private TemperatureDO temperatureDO;
	
	public List<Temperature> geTemperaturesByFamilyUserId(int familyUserId, Date time) {
		
		return temperatureDO.selectByFamilyUerIdAndDate(familyUserId, time);
	}

}
