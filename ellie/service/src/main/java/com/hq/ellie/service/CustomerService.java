package com.hq.ellie.service;

import com.github.pagehelper.PageInfo;
import com.hq.ellie.service.entity.request.CustomerRequest;
import com.hq.ellie.service.entity.request.condition.CustomerDetailRequest;
import com.hq.ellie.service.entity.request.condition.pagination.CustomerPaginationRequest;
import com.hq.ellie.service.entity.response.CustomerResponse;

/**
 * 
 * @author Yan  
 * @date 2016年12月12日  
 * @version V1.0
 */
public interface CustomerService {

	/**
	 * 支付完成后，顾客信息新增
	 * @param request
	 * @return
	 */
	public boolean addCustomer(CustomerRequest request);
	
	/**
	 * 支付完成后，顾客信息更新
	 * @param request
	 * @return
	 */
	public boolean uptCustomer(CustomerRequest request);
	
	/**
	 * 户支付完成后，调用顾客接口afterPaySuccessed
	 * 根据openId、plat_type、mchId来区分是否已经存在顾客信息，不存在则新增，否则累加当前顾客到店次数并更新顾客最后到店时间。
	 * @param request
	 * @return
	 */
	public boolean afterPaySuccessed(CustomerRequest request);
	
	/**
	 * 绑定商户编号，可根据平台类型查询列表，并按照lastvisit_time降序（时间线展示顾客流）;
	 * 时间区间过滤，用于今日顾客，本周顾客及本月顾客进行统计分组;
	 * @param request
	 * @return
	 */
	public PageInfo<CustomerResponse> getAllCustomer(CustomerPaginationRequest request);
	
	/**
	 * 绑定商户编号，查询顾客到店次数及最后一次到店时间
	 * @param request
	 * @return
	 */
	public CustomerResponse getCustomer(CustomerDetailRequest request);

    /**
     * 根据openId查询客户信息
     * @param openId
     * @return
     */
    public CustomerResponse getCustomerByOpenId(String openId);
}
