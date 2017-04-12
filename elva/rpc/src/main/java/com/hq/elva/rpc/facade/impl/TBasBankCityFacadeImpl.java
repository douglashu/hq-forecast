package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasBankCity;
import com.hq.elva.rpc.facade.TBasBankCityFacade;
import com.hq.elva.service.ITBasBankCityService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasBankCityFacadeImpl implements TBasBankCityFacade {

	
	public static final Logger logger = Logger.getLogger(TBasBankCityFacadeImpl.class);
	
	@Resource
	private ITBasBankCityService iTBasBankCityService;

	/**
	 * 根据银联省份信息获取银联地市
	 * @return
	 */
	@Override
	public RespEntity<List<Map<String, String>>> getBankCity(String provCode) {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasBankCity> list = iTBasBankCityService.getBankCity(provCode);
			for(int i=0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("cityCd", list.get(i).getCityCd());
				map.put("cityNm", list.get(i).getCityNm());
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);
	}

	
	/**
	 * 根据银联地市信息获取银联区县
	 * @return
	 */
	@Override
	public RespEntity<List<Map<String, String>>> getBankCounty(String cityCode) {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasBankCity> list = iTBasBankCityService.getBankCounty(cityCode);
			for(int i=0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("cityCd", list.get(i).getCityCd());
				map.put("cityNm", list.get(i).getCityNm());
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);
	}
	
	
	public ITBasBankCityService getiTBasBankCityService() {
		return iTBasBankCityService;
	}


	public void setiTBasBankCityService(ITBasBankCityService iTBasBankCityService) {
		this.iTBasBankCityService = iTBasBankCityService;
	}

}
