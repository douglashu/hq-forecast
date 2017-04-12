package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.request.AlipaySystemOauthTokenRequest;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：17/1/10 上午8:08
 */
public interface IAlipaySystemOauthTokenValidate {

    /**
     * 验证使用auth_code换取接口access_token及用户userId接口参数
     * @param model
     */
    public void validate(AlipaySystemOauthTokenRequest model);
}
