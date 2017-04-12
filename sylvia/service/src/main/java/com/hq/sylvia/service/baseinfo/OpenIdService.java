package com.hq.sylvia.service.baseinfo;

import com.alibaba.fastjson.JSON;
import com.hq.brooke.model.AliOpenId;
import com.hq.brooke.model.WxOpenId;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import com.hq.sylvia.service.common.SylviaCommonService;
import com.hq.sylvia.service.constant.SylviaThirdConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 12/01/2017.
 */
@Service
public class OpenIdService extends SylviaCommonService {

    public String getWxUserOpenId(String authCode) {
        if(StringUtils.isEmpty(authCode)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "授权码不能为空[authCode]");
        }
        HqRequest hqRequest = new HqRequest();
        MapBuilder builder = MapBuilder.create("authCode", authCode)
                .add("tAppId", SylviaThirdConfig.WX_APP_ID)
                .add("tAppSecret", SylviaThirdConfig.WX_APP_SECRET);
        hqRequest.setBizContent(JSON.toJSONString(builder.get()));
        WxOpenId wxOpenId = invoke("brooke.wx.user.openid", "1.0", hqRequest, WxOpenId.class);
        return wxOpenId.getOpenId();
    }

    public String getAliUserOpenId(String authCode) {
        if(StringUtils.isEmpty(authCode)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "授权码不能为空[authCode]");
        }
        HqRequest hqRequest = new HqRequest();
        hqRequest.setBizContent(JSON.toJSONString(
                MapBuilder.create("authCode", authCode).get()));
        AliOpenId aliOpenId = invoke("brooke.ali.user.openid", "1.0", hqRequest, AliOpenId.class);
        return aliOpenId.getUserId();
    }

}
