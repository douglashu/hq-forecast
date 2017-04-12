package com.hq.tony.service;

import com.hq.tony.service.entity.common.AccessToken;

/**
 * @包名称：com.hq.tony.service
 * @创建人：yyx
 * @创建时间：17/3/4 下午2:03
 */
public interface WeixinAccesstokenService {

    /**
     * 获取access_token
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public  AccessToken getAccessToken(String appid,String appsecret);
}
