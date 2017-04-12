package com.hq.ellie.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hq.ellie.dao.generate.CustomerMapper;
import com.hq.ellie.entity.generate.Customer;
import com.hq.ellie.entity.generate.CustomerExample;
import com.hq.ellie.service.CustomerService;
import com.hq.ellie.service.entity.request.CustomerRequest;
import com.hq.ellie.service.entity.request.condition.CustomerDetailRequest;
import com.hq.ellie.service.entity.request.condition.pagination.CustomerPaginationRequest;
import com.hq.ellie.service.entity.response.CustomerResponse;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.common.util.ValidateUtils;

/**
 * @author Yan
 * @date 2016年12月12日
 * @version V1.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	private Logger logger = Logger.getLogger();

	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public boolean addCustomer(CustomerRequest request) {
		this.customerRequestValidate(request);

		Customer entity = new Customer();
		BeanUtils.copyProperties(request, entity);

		entity.setVisit_num(1);
		entity.setLastvisit_time(Calendar.getInstance().getTime());
		try {
			customerMapper.insert(entity);
		} catch (Exception e) {
			logger.error(e.fillInStackTrace());
			throw new BusinessException("数据库异常");
		}
		return Boolean.TRUE;
	}

	@Override
	public boolean uptCustomer(CustomerRequest request) {
		this.customerRequestValidate(request);

		CustomerDetailRequest detailRequest = new CustomerDetailRequest();
		detailRequest.setOpenid(request.getOpenid());
		detailRequest.setMchId(request.getMchId());
		detailRequest.setPlat_type(request.getPlat_type());
		CustomerResponse customerResponse = this.getCustomer(detailRequest);

		Customer entity = new Customer();
		BeanUtils.copyProperties(request, entity, new String[] { "opendid", "plat_type", "mchId" });

		entity.setVisit_num(customerResponse.getVisit_num() + 1);
		entity.setLastvisit_time(Calendar.getInstance().getTime());
		try {
			customerMapper.updateByExampleSelective(entity, getSingleCustomerExample(request));
		} catch (Exception e) {
			logger.error(e.fillInStackTrace());
			throw new BusinessException("数据库异常");
		}
		return Boolean.TRUE;
	}

	@Override
	public boolean afterPaySuccessed(CustomerRequest request) {
		if (customerMapper.countByExample(getSingleCustomerExample(request)) > 0) {
			this.uptCustomer(request);
		} else {
			this.addCustomer(request);
		}
		return Boolean.TRUE;
	}

	@Override
	public PageInfo<CustomerResponse> getAllCustomer(CustomerPaginationRequest request) {

		CustomerExample example = new CustomerExample();
		CustomerExample.Criteria criteria = example.createCriteria();

		if (!ValidateUtils.isStrEmpty(request.getMchId())) {
			criteria.andMchIdLike("%" + request.getMchId() + "%");
		}
		if (!ValidateUtils.isStrEmpty(request.getOpenid())) {
			criteria.andOpenidEqualTo(request.getOpenid());
		}
		if (request.getPlat_type()!=null) {
			criteria.andPlat_typeEqualTo(request.getPlat_type());
		}
		if (null != request.getBeginLastVisitTime()) {
			criteria.andLastvisit_timeGreaterThanOrEqualTo(request.getBeginLastVisitTime());
		}
		if (null != request.getEndLastvisitTime()) {
			criteria.andLastvisit_timeLessThanOrEqualTo(request.getEndLastvisitTime());
		}

        example.setOrderByClause("lastvisit_time desc");
        RowBounds rowBounds = new RowBounds(request.getPage(), request.getPageSize());
        List<CustomerResponse> rsps = new ArrayList<>();
        List<Customer> records = customerMapper.selectByExampleWithRowbounds(example, rowBounds);
        CollectionUtils.copyCollections(records, rsps, CustomerResponse.class);
        PageInfo<CustomerResponse> page = new PageInfo<>(rsps);
		return page;
	}

	@Override
	public CustomerResponse getCustomer(CustomerDetailRequest request) {
		List<Customer> customer = customerMapper.selectByExampleWithRowbounds(getSingleCustomerExample(request),
				new RowBounds(0, 1));
		if (CollectionUtils.isEmpty(customer)) {
			throw new ParamValueException("没有对应的顾客信息");
		} else {
			CustomerResponse response = new CustomerResponse();
			BeanUtils.copyProperties(CollectionUtils.findFirst(customer), response);
			return response;
		}
	}

    @Override
    public CustomerResponse getCustomerByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new ParamValueException("支付宝或者微信的openId不能为空");
        } else {
        	CustomerExample customerExample = new CustomerExample();
            CustomerExample.Criteria criteria = customerExample.createCriteria();
            criteria.andOpenidEqualTo( openId );
            
            PageHelper.startPage(1, 1);
        	List<Customer> customer = customerMapper.selectByExample(customerExample);
    		if (CollectionUtils.isEmpty(customer)) {
    			throw new ParamValueException("没有对应的顾客信息");
    		} else {
    			CustomerResponse response = new CustomerResponse();
    			BeanUtils.copyProperties(CollectionUtils.findFirst(customer), response);
    			return response;
    		}
        }
    }

    /**
	 * 获取某个顾客对应的CustomerExample实例
	 * 
	 * @param request
	 * @return
	 */
	private CustomerExample getSingleCustomerExample(CustomerDetailRequest request) {
		CustomerExample example = new CustomerExample();
		example.createCriteria().andOpenidEqualTo(request.getOpenid()).andMchIdEqualTo(request.getMchId())
				.andPlat_typeEqualTo(request.getPlat_type());
		return example;
	}

	/**
	 * 获取某个顾客对应的CustomerExample实例
	 * 
	 * @param request
	 * @return
	 */
	private CustomerExample getSingleCustomerExample(CustomerRequest request) {
		CustomerExample example = new CustomerExample();
		example.createCriteria().andOpenidEqualTo(request.getOpenid()).andMchIdEqualTo(request.getMchId())
				.andPlat_typeEqualTo(request.getPlat_type());
		return example;
	}

	private void customerRequestValidate(CustomerRequest request) {
		if (null == request || ValidateUtils.isStrEmpty(request.getOpenid())
				|| ValidateUtils.isStrEmpty(request.getMchId()) || request.getPlat_type()==null) {
			throw new ParamValueException("传入参数错误");
		}
	}
}
