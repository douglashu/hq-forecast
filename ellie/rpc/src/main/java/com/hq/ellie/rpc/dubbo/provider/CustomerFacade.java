package com.hq.ellie.rpc.dubbo.provider;

import com.hq.ellie.service.entity.response.CustomerResponse;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * 
 * @author Yan  
 * @date 2016年12月12日  
 * @version V1.0
 */
public interface CustomerFacade {

	/**
	 * 户支付完成后，调用顾客接口afterPaySuccessed
	 * 根据openId、plat_type、mchId来区分是否已经存在顾客信息，不存在则新增，否则累加当前顾客到店次数并更新顾客最后到店时间。
	 * @param hqRequest
	 * @return
	 */
	public RespEntity<String> afterPaySuccessed(HqRequest hqRequest);
	
	/**
	 * 绑定商户编号，可根据平台类型查询列表，并按照lastvisit_time降序（时间线展示顾客流）;
	 * 时间区间过滤，用于今日顾客，本周顾客及本月顾客进行统计分组;
	 * @param hqRequest
	 * @return
	 */
	public RespEntity<String> getAllCustomer(HqRequest hqRequest);
	
	/**
	 * 绑定商户编号，查询顾客到店次数及最后一次到店时间
	 * @param hqRequest
	 * @return
	 */
	public RespEntity<String> getCustomer(HqRequest hqRequest);

    /**
     * openId查询顾客信息
     * @param hqRequest
     * @return
     */
    public CustomerResponse getCustomerByOpenId(HqRequest hqRequest);
}
