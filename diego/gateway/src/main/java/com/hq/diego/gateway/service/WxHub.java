package com.hq.diego.gateway.service;

import com.hq.diego.gateway.service.redpacket.SendRedPacketReq;
import com.hq.diego.gateway.service.redpacket.WxRedPacketService;
import com.hq.diego.gateway.constant.WxOperateType;
import org.apache.log4j.Logger;

/**
 * Created by zhaoyang on 1/14/16.
 */
public class WxHub {

    private static final Logger logger = Logger.getLogger(WxHub.class);

    public static void main(String[] args) {
        // System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        /*
        WxAccessToken token = (new WxHub()).getAccessToken("wxfb14a4a90352655d", "f41be29c9d177bdc175663b837c5e495");
        System.out.println(token.getAppId());
        System.out.println(token.getValue());
        System.out.println(token.getExpiryTime());
        */
        /*
        SendRedPacketReq req = new SendRedPacketReq();
        req.setMch_id("1232422802");
        req.setMch_billno("12324228020000000000");
        req.setWxappid("wxf2c23edc34843d3c");
        req.setSend_name("汉拓信息");
        req.setRe_openid("ott1Ot8nXvN4u5mMOcc88yrXEFUY");
        req.setTotal_amount(100);
        req.setTotal_num(1);
        req.setWishing("新年快乐");
        req.setClient_ip("113.240.226.45");
        req.setAct_name("活动名称");
        req.setRemark("备注信息");
        SendRedPacketRsp rsp = (new WxRedPacketService()).sendRedPacket(req, "51c9dc2b399c4b3eacf2a4fa3cdf86ce");
        if(rsp != null) {
            System.out.println(rsp);
        }
        */
        SendRedPacketReq req = new SendRedPacketReq();
        req.setMch_id("1232422802");
        req.setMch_billno("12324228020000000000");
        req.setWxappid("wxf2c23edc34843d3c");
        (new WxRedPacketService()).getRedPackets(req, "51c9dc2b399c4b3eacf2a4fa3cdf86ce");
    }

    /*
    public WxAccessToken getAccessToken(String appId, String secret) {
        try {
            DateTime now = DateTime.now();
            String url = WxPayURL.getGetAccessTokenUrl(appId, secret);
            CloseableHttpClient client = HttpClientFactory.instance().getHttpClient(HttpClientID.GLOBAL);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse resp = client.execute(httpGet);
            try {  httpGet.releaseConnection(); } finally { }
            try {  resp.close(); } finally { }
            if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
                String jsonStr = EntityUtils.toString(resp.getEntity());
                if(jsonStr == null) {
                    logger.warn("Get wechat access token fail, the http response body is null.");
                    return null;
                }
                JSONObject respJSON = JSON.parseObject(jsonStr);
                if(respJSON == null) {
                    logger.warn("Get wechat access token fail, the response json format is invalid.");
                    return null;
                }
                if (respJSON.containsKey("access_token")) {
                    String accessToken = respJSON.getString("access_token");
                    Integer expiresIn = null;
                    if (respJSON.containsKey("expires_in")) {
                        expiresIn = respJSON.getInteger("expires_in");
                    }
                    if (expiresIn == null) {
                        expiresIn = GatewayConfig.ACCESS_TOKEN_EXPIRY_SECONDS;
                    }
                    WxAccessToken wxAccessToken = new WxAccessToken();
                    wxAccessToken.setAppId(appId);
                    wxAccessToken.setValue(accessToken);
                    wxAccessToken.setCreateTime(now.toDate());
                    wxAccessToken.setExpiryTime(now.plusSeconds(expiresIn).toDate());
                    return wxAccessToken;
                } else {
                    if (respJSON.containsKey("errcode")) {
                        int errCode = respJSON.getIntValue("errcode");
                        String errMsg = WxErrorCenter.getErrorMsgByCode(errCode);
                        logger.warn("Get wechat access token fail, the msg is " + errMsg + "[" + errCode + "].");
                    } else {
                        logger.warn("Get wechat access token fail, the response json is invalid.");
                    }
                }
            } else {
                logger.warn("Get wechat access token fail, the http status code is " + resp.getStatusLine().getStatusCode() + ".");
            }
        } catch (IOException ioe) {
            logger.warn("Get wechat access token fail that network has some problem.", ioe);
        }
        return null;
    }
    */

    public void processWxErrorResp(int errorCode, WxOperateType operateType) {
        // 45009 超限
        // 40001 无效token, 可能token已经在别处刷新, 这个不是最新或者是错误
        // 42001 token超时, token正确但是没刷新导致超时
        // 40014???????????? 不合法的access_token，请开发者认真比对access_token的有效性
        if(WxOperateType.GET_GLOBAL_ACCESS_TOKEN == operateType) {
            if(errorCode == 45009) {
            }
        } else {
        }
    }
}
