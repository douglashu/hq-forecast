package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.util.StringUtils;

/**
 * @�����ƣ�com.hq.sid.alipay.gateway.service.common
 * @�����ˣ�yyx
 * @����ʱ�䣺17/1/10 ����8:09
 */
public class AlipaySystemOauthTokenValidate implements IAlipaySystemOauthTokenValidate{
    @Override
    public void validate(AlipaySystemOauthTokenRequest model) {
        if( null == model ){
            throw new ParamValueException("��AlipaySystemOauthTokenRequest����֤ʹ��auth_code��ȡ�ӿ�access_token���û�userId�ӿڲ�������Ϊ��");
        }

        if (StringUtils.isBlank(model.getGrantType())) {
            throw new ParamValueException("��grant_type����Ȩ���Ͳ���Ϊ��");
        }

        if (StringUtils.isBlank(model.getCode())&&StringUtils.isBlank(model.getRefreshToken())) {
            throw new ParamValueException("��code | refresh_token����Ȩ�� | ˢ�����Ʋ���ͬʱΪ��");
        }
    }
}
