package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.request.AlipaySystemOauthTokenRequest;

/**
 * @�����ƣ�com.hq.sid.alipay.gateway.service.common
 * @�����ˣ�yyx
 * @����ʱ�䣺17/1/10 ����8:08
 */
public interface IAlipaySystemOauthTokenValidate {

    /**
     * ��֤ʹ��auth_code��ȡ�ӿ�access_token���û�userId�ӿڲ���
     * @param model
     */
    public void validate(AlipaySystemOauthTokenRequest model);
}
