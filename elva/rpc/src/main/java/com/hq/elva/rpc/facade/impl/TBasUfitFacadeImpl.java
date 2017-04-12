package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasUfit;
import com.hq.elva.rpc.facade.TBasUfitFacade;
import com.hq.elva.service.ITBasUfitService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class TBasUfitFacadeImpl implements TBasUfitFacade {
	
	public static final Logger logger = Logger.getLogger(TBasUfitFacadeImpl.class);

	@Resource
	private ITBasUfitService iTBasUfitService;
	/**
	 * 根据银行卡号获取卡宾信息
	 * @param cardNo
	 * @return
	 */
	@Override
	public RespEntity<Map<String, String>> getTBasUfit(String cardNo) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			TBasUfit tBasUfit = iTBasUfitService.getTBasUfit(cardNo);
			map.put("bnkTyp", tBasUfit.getBnkTyp());
			map.put("crdFlg", tBasUfit.getCrdFlg());
			map.put("crdLen", tBasUfit.getCrdLen());
			map.put("crdNm", tBasUfit.getCrdNm());
			map.put("crdOfs", tBasUfit.getCrdOfs());
			map.put("crdTrk", tBasUfit.getCrdTrk());
			map.put("expDtFlg", tBasUfit.getExpDtFlg());
			map.put("fitCtt", tBasUfit.getFitCtt());
			map.put("fitLen", tBasUfit.getFitLen());
			map.put("fitNo", tBasUfit.getFitNo());
			map.put("fitOfs", tBasUfit.getFitOfs());
			map.put("fitTrk", tBasUfit.getFitTrk());
			map.put("intMod", tBasUfit.getIntMod());
			map.put("fitId", String.valueOf(tBasUfit.getFitId()));
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<Map<String, String>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",map);
		}
		return new RespEntity<Map<String, String>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",map);	
	
	}

}
