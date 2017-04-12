package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.domain.AntMerchantExpandEnterpriseApplyModel;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：16/12/12 下午10:57
 */
public interface IAntMerchantExpandEnterpriseApplyValidate {

    /**
     * 验证企业级商户入驻接口参数
     * @param model
     */
    public void validate(AntMerchantExpandEnterpriseApplyModel model);
}
