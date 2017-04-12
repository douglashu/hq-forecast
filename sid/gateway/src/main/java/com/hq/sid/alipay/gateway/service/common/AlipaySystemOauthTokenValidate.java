package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.util.StringUtils;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：17/1/10 上午8:09
 */
public class AlipaySystemOauthTokenValidate implements IAlipaySystemOauthTokenValidate{
    @Override
    public void validate(AlipaySystemOauthTokenRequest model) {
        if( null == model ){
            throw new ParamValueException("【AlipaySystemOauthTokenRequest】验证使用auth_code换取接口access_token及用户userId接口参数不能为空");
        }

        if (StringUtils.isBlank(model.getGrantType())) {
            throw new ParamValueException("【grant_type】授权类型不能为空");
        }

        if (StringUtils.isBlank(model.getCode())&&StringUtils.isBlank(model.getRefreshToken())) {
            throw new ParamValueException("【code | refresh_token】授权码 | 刷新令牌不能同时为空");
        }
    }
}
