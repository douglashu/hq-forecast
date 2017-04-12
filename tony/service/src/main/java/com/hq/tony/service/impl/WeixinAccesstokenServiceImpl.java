package com.hq.tony.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.tony.dao.generate.WeixinAccesstokenMapper;
import com.hq.tony.entity.generate.WeixinAccesstoken;
import com.hq.tony.entity.generate.WeixinAccesstokenExample;
import com.hq.tony.service.WeixinAccesstokenService;
import com.hq.tony.service.entity.common.AccessToken;
import com.hq.tony.service.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @包名称：com.hq.tony.service.impl
 * @创建人：yyx
 * @创建时间：17/3/4 下午2:03
 */
@Service
public class WeixinAccesstokenServiceImpl implements WeixinAccesstokenService{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private WeixinAccesstokenMapper weixinAccesstokenMapper;


    public  AccessToken getAccessToken(String appid,String appsecret) {
        if(StringUtils.isBlank(appid)){
            throw new ParamValueException("参数【appid】不能为空");
        }
        if(StringUtils.isBlank(appsecret)){
            throw new ParamValueException("参数【appsecret】不能为空");
        }

        WeixinAccesstoken accessTocken = null;
        List<WeixinAccesstoken> accessTockenList = weixinAccesstokenMapper.selectByExample(new WeixinAccesstokenExample());
        if(CollectionUtils.isNotEmpty( accessTockenList )){
            accessTocken = accessTockenList.get( 0 );
        }
        if(accessTocken!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date end = new java.util.Date();
            java.util.Date start = new java.util.Date(accessTocken.getAddtime().getTime());
            if(end.getTime()-accessTocken.getAddtime().getTime()>accessTocken.getExpiresIn()*1000){
                AccessToken accessToken = null;
                String requestUrl = WeixinUtil.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
                JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
                // 如果请求成功
                if (null != jsonObject) {
                    try {
                        accessToken = new AccessToken();
                        accessToken.setToken(jsonObject.getString("access_token"));
                        accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
                        //凭证过期更新凭证
                        WeixinAccesstoken accesstoken = new WeixinAccesstoken();
                        accesstoken.setId(accessTocken.getId());
                        accesstoken.setExpiresIn(jsonObject.getInteger("expires_in"));
                        accesstoken.setAccessToken(jsonObject.getString("access_token"));
                        weixinAccesstokenMapper.updateByPrimaryKeySelective(accesstoken);
                    } catch (Exception e) {
                        accessToken = null;
                        // 获取token失败
                        String wrongMessage = "获取token失败 errcode:{} errmsg:{}"+jsonObject.getInteger("errcode")+jsonObject.getString("errmsg");
                        logger.info(wrongMessage);
                    }
                }
                return accessToken;
            }else{

                AccessToken  accessToken = new AccessToken();
                accessToken.setToken(accessTocken.getAccessToken());
                accessToken.setExpiresIn(accessTocken.getExpiresIn());
                return accessToken;
            }
        }else{

            AccessToken accessToken = null;
            String requestUrl = WeixinUtil.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
            JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));

                    WeixinAccesstoken accesstoken = new WeixinAccesstoken();
                    accesstoken.setExpiresIn(jsonObject.getInteger("expires_in"));
                    accesstoken.setAccessToken(jsonObject.getString("access_token"));
                    weixinAccesstokenMapper.insert(accesstoken);

                } catch (Exception e) {
                    accessToken = null;
                    // 获取token失败
                    String wrongMessage = "获取token失败 errcode:{} errmsg:{}"+jsonObject.getInteger("errcode")+jsonObject.getString("errmsg");
                    logger.info(wrongMessage);
                }
            }
            return accessToken;
        }
    }
}
