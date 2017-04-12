package com.hq.ellie.service;

import java.util.Calendar;

import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hq.ellie.service.entity.request.CustomerRequest;
import com.hq.ellie.service.entity.request.condition.CustomerDetailRequest;
import com.hq.ellie.service.entity.request.condition.pagination.CustomerPaginationRequest;
import com.hq.ellie.service.entity.response.CustomerResponse;
import com.hq.scrati.common.constants.CommonConstants;

/** 
 * @author Yan  
 * @date 2016年12月15日  
 * @version V1.0  
 */
public class CustomerServiceImplTester extends SpringJUnitConfiger{

	@Autowired
	private CustomerService customerService;
	
	/*@Test
	public void addCustomerTest(){
		CustomerRequest request = new CustomerRequest();
		request.setOpenid("20161215");
		request.setMchId("20161215001");
		request.setPlat_type(CommonConstants.PlatType.ALI_PAY);
		customerService.addCustomer(request);
	}
	
	@Test
	public void uptCustomer(){
		CustomerRequest request = new CustomerRequest();
		request.setOpenid("20161215");
		request.setMchId("20161215001");
		request.setPlat_type(CommonConstants.PlatType.ALI_PAY);
		customerService.uptCustomer(request);
	}
	
	*/
	
	@Test
	public void afterPaySuccessed(){
		CustomerRequest request = new CustomerRequest();
		request.setOpenid("20161215");
		request.setMchId("20161215001");
			request.setPlat_type(1);
		customerService.afterPaySuccessed(request);
	}
	
	@Test
	public void getAllCustomer(){
		CustomerPaginationRequest request = new CustomerPaginationRequest();
		request.setOpenid("20161215");
		request.setMchId("20161215001");
		request.setPlat_type(1);
		request.setEndLastvisitTime(Calendar.getInstance().getTime());
		//request.setStartRecord(0);
		//request.setMaxRecords(100);
		PageInfo<CustomerResponse> rsp = customerService.getAllCustomer(request);
		
		System.out.println(rsp.getList());
	}
	
	@Test
	public void getCustomer(){
		CustomerDetailRequest request = new CustomerDetailRequest();
		request.setOpenid("20161215");
		request.setMchId("20161215001");
		request.setPlat_type(1);
		System.out.println(customerService.getCustomer(request));
	}
}
