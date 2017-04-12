package com.hq.scrati.framework.service.impl;

import java.io.UnsupportedEncodingException;

import com.hq.scrati.framework.service.MonitorService;
import com.hq.scrati.framework.stat.StatTesbHourStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hq.scrati.framework.stat.StatTesbServiceStatus;

@Component("monitorService")
public class MonitorServiceImpl implements MonitorService {

	public static final Logger LOGGER = LoggerFactory.getLogger(MonitorServiceImpl.class);

	@Autowired
	StatTesbServiceStatus statTesbServiceStatus;
	
	@Autowired
	StatTesbHourStatus statTesbHourStatus;
	
	@Override
	public void prepareMetricDatum(String serviceCode, String version) {
		try {
			statTesbServiceStatus.setStatisticItem(serviceCode, version);
			statTesbHourStatus.setStatisticItem(serviceCode, version);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException:",e);
		}	
	}

	@Override
	public void resultMetricDatum(String serviceCode, String version,boolean flags,long latency,Object response) {
		long responseSize=0;
		if(null!=response){
			responseSize=response.toString().getBytes().length;
		}
		statTesbServiceStatus.setStatisticItem(serviceCode, version, flags, latency,responseSize);
		statTesbHourStatus.setStatisticItem(serviceCode, version, flags, latency, responseSize);
	}

	@Override
	public void slaMetricDatum(String serviceCode, String version) {
		try {
			statTesbServiceStatus.setSLAStatisticItem(serviceCode, version);
			statTesbHourStatus.setSLAStatisticItem(serviceCode, version);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException:",e);
		}	
		
	}

}
