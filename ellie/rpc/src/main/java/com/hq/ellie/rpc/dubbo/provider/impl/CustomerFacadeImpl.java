package com.hq.ellie.rpc.dubbo.provider.impl;

import com.github.pagehelper.PageInfo;
import com.hq.ellie.service.entity.request.CustomerOpenIdRequest;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.ellie.rpc.dubbo.provider.CustomerFacade;
import com.hq.ellie.service.CustomerService;
import com.hq.ellie.service.entity.request.CustomerRequest;
import com.hq.ellie.service.entity.request.condition.CustomerDetailRequest;
import com.hq.ellie.service.entity.request.condition.pagination.CustomerPaginationRequest;
import com.hq.ellie.service.entity.response.CustomerResponse;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.framework.rpc.helper.RpcReturnHelper;
import com.hq.scrati.model.HqRequest;

/**
 * 
 * @author Yan
 * @date 2016年12月12日
 * @version V1.0
 */
@Service(version = "1.0", interfaceClass = CustomerFacade.class)
public class CustomerFacadeImpl implements CustomerFacade {

	private Logger logger = Logger.getLogger();

	@Autowired
	private CustomerService customerService;

	@Override
	public RespEntity<String> afterPaySuccessed(HqRequest hqRequest) {
		CustomerRequest request = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<CustomerRequest>() {});
		if (null == request) {
			return RpcReturnHelper.getRespEntityParamError();
		} else {
			try {
				Boolean flag = customerService.afterPaySuccessed(request);
				return RpcReturnHelper.getSuccessRespEntity(JSONObject.toJSONString(flag));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return RpcReturnHelper.getFailRespEntity(e.getMessage());
			}
		}
	}

	@Override
	public RespEntity<String> getAllCustomer(HqRequest hqRequest) {
		CustomerPaginationRequest request = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<CustomerPaginationRequest>() {});
		if (null == request) {
			return RpcReturnHelper.getRespEntityParamError();
		} else {
			try {
				PageInfo<CustomerResponse> page = customerService.getAllCustomer(request);
				return RpcReturnHelper.getSuccessRespEntity(JSONObject.toJSONString(page));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return RpcReturnHelper.getFailRespEntity(e.getMessage());
			}
		}
	}

	@Override
	public RespEntity<String> getCustomer(HqRequest hqRequest) {
		CustomerDetailRequest request = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<CustomerDetailRequest>() {});
		if (null == request) {
			return RpcReturnHelper.getRespEntityParamError();
		} else {
			try {
				CustomerResponse customerResponse = customerService.getCustomer(request);
				return RpcReturnHelper.getSuccessRespEntity(JSONObject.toJSONString(customerResponse));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return RpcReturnHelper.getFailRespEntity(e.getMessage());
			}
		}
	}

    @Override
    public CustomerResponse getCustomerByOpenId(HqRequest hqRequest) {
        CustomerOpenIdRequest req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<CustomerOpenIdRequest>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传入错误");
        }

        if (StringUtils.isBlank(req.getOpenId())) {
            throw new ParamValueException(RpcReturnHelper.RpcResult.REQUEST_PARAM_ERROR.getValue());
        }
        try {
            CustomerResponse rsp = customerService.getCustomerByOpenId(req.getOpenId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

}
