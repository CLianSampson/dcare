package com.dcare.service;

import java.util.Date;
import java.util.List;

import com.dcare.po.Temperature;

public interface TemperatureService {
	List<Temperature> geTemperaturesByFamilyUserId(int familyUserId, Date time);
}
