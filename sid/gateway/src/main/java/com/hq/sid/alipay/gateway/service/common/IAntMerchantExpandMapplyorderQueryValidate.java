package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.domain.AntMerchantExpandMapplyorderQueryModel;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：16/12/12 下午10:26
 */
public interface IAntMerchantExpandMapplyorderQueryValidate {

    /**
     * 验证商户入驻单查询接口参数
     * @param model
     */
    public void validate(AntMerchantExpandMapplyorderQueryModel model);
}
