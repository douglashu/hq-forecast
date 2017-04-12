package com.hq.elva.rpc.facade.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hq.elva.dao.generate.TBasAlimccMapper;
import com.hq.elva.entity.generate.TBasAlimcc;
import com.hq.elva.entity.generate.TBasAlimccExample;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasMcc;
import com.hq.elva.rpc.facade.TBasMccFacade;
import com.hq.elva.service.ITBasMccService;
import com.hq.scrati.common.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasMccFacadeImpl implements TBasMccFacade {

	
	public static final Logger logger = Logger.getLogger(TBasMccFacadeImpl.class);

	@Resource
	private ITBasMccService iTBasMccService;
	@Autowired
	private TBasAlimccMapper tBasAlimccMapper;
	
	
	/**
	 * 根据mcc大类获取mcc码，其中大类可以为空
	 * @param typId
	 * @return
	 */
	@Override
	public RespEntity<List<Map<String, String>>> getTBasMccs(String typId) {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasMcc> list = iTBasMccService.getTBasMccs(typId);
			for(int i = 0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("busCls", list.get(i).getBusCls());
				map.put("busDesc", list.get(i).getBusDesc());
				map.put("indCls", list.get(i).getIndCls());
				map.put("mccCd", list.get(i).getMccCd());
				map.put("typId", list.get(i).getTypId());
				map.put("mercClsNm", list.get(i).getMercClsNm());
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);	
	}

	/**
	 * 根据mccCd获取mcc详情
	 * @param mccCd
	 * @return
	 */
	@Override
	public RespEntity<Map<String, String>> getTBasMcc(String mccCd) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			TBasMcc tBasMcc = iTBasMccService.getTBasMcc(mccCd);
			map.put("busCls", tBasMcc.getBusCls());
			map.put("busDesc", tBasMcc.getBusDesc());
			map.put("indCls", tBasMcc.getIndCls());
			map.put("mccCd", tBasMcc.getMccCd());
			map.put("typId", tBasMcc.getTypId());
			map.put("mercClsNm", tBasMcc.getMercClsNm());
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<Map<String, String>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",map);
		}
		return new RespEntity<Map<String, String>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",map);	
	
	}

	@Override
	public List<TBasAlimcc> getAliMcc(String pMccCd) {
		if(StringUtils.isBlank(pMccCd)){
			pMccCd = "0";
		}
		TBasAlimccExample example = new TBasAlimccExample();
		example.createCriteria().andPMccCdEqualTo(pMccCd);
		List<TBasAlimcc> list = tBasAlimccMapper.selectByExample(example);
		return list;
	}

	public ITBasMccService getiTBasMccService() {
		return iTBasMccService;
	}

	public void setiTBasMccService(ITBasMccService iTBasMccService) {
		this.iTBasMccService = iTBasMccService;
	}

}
