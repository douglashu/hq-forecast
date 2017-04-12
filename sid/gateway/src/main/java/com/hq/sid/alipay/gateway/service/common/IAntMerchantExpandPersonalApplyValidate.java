package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.domain.AntMerchantExpandPersonalApplyModel;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：16/12/12 下午11:01
 */
public interface IAntMerchantExpandPersonalApplyValidate {

    /**
     * 验证个体工商户入驻接口参数
     * @param model
     */
    public void validate(AntMerchantExpandPersonalApplyModel model);
}
