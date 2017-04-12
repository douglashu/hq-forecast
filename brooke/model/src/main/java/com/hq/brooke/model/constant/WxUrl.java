package com.hq.brooke.model.constant;

import com.hq.scrati.common.util.UrlParamUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 25/12/2016.
 */
public class WxUrl {

    /*
        获取 Access Token
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public static String getAccessTokenUrl(String appId, String secret) {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("grant_type", "client_credential");
        return ACCESS_TOKEN_URL + "?" + UrlParamUtil.createLinkString(params);
    }

    /*
        获取 Jsapi Ticket
     */
    private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    public static String getJsapiTicketUrl(String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("type", "jsapi");
        return JSAPI_TICKET_URL + "?" + UrlParamUtil.createLinkString(params);
    }

    /*
        获取用户在微信下Openid
     */
    private static final String WX_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String getUserOpenIdUrl(String appId, String appSecret, String authCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("code", authCode);
        params.put("grant_type", "authorization_code");
        return WX_OPENID_URL + "?" + UrlParamUtil.createLinkString(params);
    }

    /*
        获取用户基本信息
     */
    private static final String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info";
    public static String getUserInfoUrl(String accessToken, String openId) {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", openId);
        params.put("lang", "zh_CN");
        return  GET_USER_INFO + "?" + UrlParamUtil.createLinkString(params);
    }















    /*
        发送模板消息
     */
    private static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    public static String getSendTemplateMessageUrl(String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        return SEND_TEMPLATE_MESSAGE + "?" + UrlParamUtil.createLinkString(params);
    }

    /*
        批量获取用户基本信息
     */
    private static final String BATCH_GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
    public static String getBatchGetUserInfoUrl(String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        return  BATCH_GET_USER_INFO + "?" + UrlParamUtil.createLinkString(params);
    }

    /*
        获取网页授权Access Token
     */
    private static final String GET_USER_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String getGetUserAccessTokenUrl(String appId, String secret, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        return GET_USER_ACCESS_TOKEN + "?" + UrlParamUtil.createLinkString(params);
    }


    /*
        刷新网页授权Access Token
     */
    private static final String REFRESH_USER_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    public static String getRefreshUserAccessTokenUrl(String appId, String refreshToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appId);
        params.put("refresh_token", refreshToken);
        params.put("grant_type", "authorization_code");
        return REFRESH_USER_ACCESS_TOKEN + "?" + UrlParamUtil.createLinkString(params);
    }

    /*
        验证某一微信用户网页授权Access Token 是否有效
     */
    private static final String VALIDATE_USER_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/auth";
    public static String getValidateUserAccessTokenUrl(String accessToken, String openId) {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", openId);
        return VALIDATE_USER_ACCESS_TOKEN + "?" + UrlParamUtil.createLinkString(params);
    }




    //
    // -----------------  华丽的分割线  ------------------
    //

    /*
        公众号向粉丝发送现金红包
     */
    private static final String SEND_RED_PACKET = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
    public static String getSendRedPacketUrl() {
        return SEND_RED_PACKET;
    }

    /*

     */
    private static final String GET_RED_PACKETS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";
    public static String getGetRedPackets() {
        return GET_RED_PACKETS;
    }


}
