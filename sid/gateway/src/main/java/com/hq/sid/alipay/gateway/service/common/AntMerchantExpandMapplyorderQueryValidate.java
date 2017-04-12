package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.domain.AntMerchantExpandMapplyorderQueryModel;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.util.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：16/12/12 下午10:22
 */
@Component
public class AntMerchantExpandMapplyorderQueryValidate implements IAntMerchantExpandMapplyorderQueryValidate {

    @Override
    public void validate(AntMerchantExpandMapplyorderQueryModel model) {
        if( null == model ){
            throw new ParamValueException("【AntMerchantExpandMapplyorderQueryModel】商户入驻单查询接口参数不能为空");
        }
        if (StringUtils.isEmpty(model.getOrderNo())) {
            throw new ParamValueException("【order_no】支付宝端商户入驻申请单据号不能为空");
        }
    }

}
