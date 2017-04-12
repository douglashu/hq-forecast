package com.hq.crash.service.merc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.crash.service.common.ProvCityService;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.MercResp;
import com.hq.sid.service.entity.response.QrCodeRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyang on 11/02/2017.
 */
@Service
public class MchService extends CrashCommonService {

    @Autowired
    private ProvCityService provCityService;

    public MercResp getMchInfo(UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        request.setBizContent("{coreId:" + userSession.activeMchInfo().getCoreId() + "}");
        MercResp mercResp = invoke("sid.merc.getinfo", "1.0", request, MercResp.class);
        if (mercResp != null) {
            String[] names = this.provCityService
                    .getNames(mercResp.getBusiLicenseProv(), mercResp.getBusiLicenseCity());
            if (names != null && names.length == 2) {
                mercResp.setBusiProvName(names[0]);
                mercResp.setBusiCityName(names[1]);
            }
            mercResp.clearSecures();
        }
        return mercResp;
    }

    public List<QrCodeRsp> getStoreCodes(UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        Map<String, Object> reqParams = MapBuilder
                .create("coreId", userSession.activeMchInfo().getCoreId())
                .add("page", 1).add("pageSize", 50).get();
        request.setBizContent(JSON.toJSONString(reqParams));
        JSONObject jsonObject = invoke("sid.qrcode.pages", "1.0", request, JSONObject.class);
        if (jsonObject.containsKey("list")) {
            return jsonObject.getJSONArray("list").toJavaList(QrCodeRsp.class);
        } else {
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> getMchRatesInfo(UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        JSONArray jsonArray = invoke("rudy.mch.channel.query", "1.0", request, JSONArray.class);
        BigDecimal wxRate = null;
        BigDecimal aliRate = null;
        BigDecimal thirdRate = null;
        for (int i=0; i<jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!"NORMAL".equals(jsonObject.getString("state"))) continue;
            if ("ALIPAY".equals(jsonObject.getString("channel"))) {
                aliRate = jsonObject.getBigDecimal("codePayRate");
            } else if ("WEIXIN_PAY".equals(jsonObject.getString("channel"))) {
                wxRate = jsonObject.getBigDecimal("codePayRate");
            } else {
                BigDecimal rate = jsonObject.getBigDecimal("codePayRate");
                if (thirdRate == null) {
                    thirdRate = rate;
                } else {
                    if (rate.compareTo(thirdRate) < 0) {
                        thirdRate = rate;
                    }
                }
            }
        }
        if (wxRate == null) {
            wxRate = thirdRate;
        } else {
            if (thirdRate != null) {
                if (thirdRate.compareTo(wxRate) < 0) {
                    wxRate = thirdRate;
                }
            }
        }
        if (aliRate == null) {
            aliRate = thirdRate;
        } else {
            if (thirdRate != null) {
                if (thirdRate.compareTo(aliRate) < 0) {
                    aliRate = thirdRate;
                }
            }
        }
        List<Map<String, Object>> prods = new ArrayList<>();
        prods.add(MapBuilder
                .create("name", "微信收款")
                .add("channel", PayChannels.WEIXIN_PAY.getCode())
                .add("rate", BigDecimal.valueOf(0.006))
                .add("discountRate", (wxRate != null? wxRate: BigDecimal.valueOf(0.006)))
                .add("state", 1).get());
        prods.add(MapBuilder.create("name", "支付宝收款")
                .add("channel", PayChannels.ALI_PAY.getCode())
                .add("rate", BigDecimal.valueOf(0.006))
                .add("discountRate", (aliRate != null? aliRate: BigDecimal.valueOf(0.006)))
                .add("state", 1).get());
        return prods;
    }

}
