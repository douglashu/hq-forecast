package com.hq.diego.gateway.service.core.wx;

import com.alibaba.fastjson.JSON;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.model.Goods;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.gateway.service.core.common.CommonPayService;
import com.hq.diego.model.req.wx.WxOrderPrepayReq;
import com.hq.diego.model.route.ChannelRoute;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyang on 23/11/2016.
 */
public class WxCommonService extends CommonPayService {

    public WxOrderPrepayReq createPrepayOrder(OrderTradeReq orderReq, ChannelRoute route) {
        WxOrderPrepayReq req = new WxOrderPrepayReq();
        req.setSub_appid(route.getAppId());
        req.setSub_mch_id(route.getMchId());
        req.setDevice_info(orderReq.getTerminalId());
        req.setBody(orderReq.getTitle());
        req.setDetail(getDetailJsonStr(orderReq.getGoodsList()));
        req.setOut_trade_no(orderReq.getOrderId());
        req.setFee_type("CNY");
        req.setTotal_fee(orderReq.getTotalAmount());
        req.setSpbill_create_ip(orderReq.getIpAddress());
        // req.setGoods_tag(orderReq.getGoodsTag());
        req.setAttach(orderReq.getCustom());
        DateTime now = DateTime.now();
        req.setTime_start(now.toString("YYYYMMddHHmmss"));
        DateTime expireTime = now.plusMinutes(GatewayConfig.ORDER_DEFAULT_EXPIRE_MINUTES);
        req.setTime_expire(expireTime.toString("YYYYMMddHHmmss"));
        req.setNotify_url(GatewayConfig.WX_NOTIFY_URL);
        return req;
    }

    public String getDetailJsonStr(List<Goods> goodsList) {
        if(goodsList == null || goodsList.size() == 0) return null;
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> goodsDetail = new ArrayList<>();
        for(Goods goods: goodsList) {
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("goods_id", goods.getId());
            goodsMap.put("goods_name", goods.getTitle());
            goodsMap.put("quantity", goods.getQuantity());
            goodsMap.put("price", goods.getPrice());
            if(StringUtils.isNotEmpty(goods.getCategory())) {
                goodsMap.put("goods_category", goods.getCategory());
            }
            if(StringUtils.isNotEmpty(goods.getThirdId())) {
                goodsMap.put("wxpay_goods_id", goods.getThirdId());
            }
            if(StringUtils.isNotEmpty(goods.getBody())) {
                goodsMap.put("body", goods.getBody());
            }
            goodsDetail.add(goodsMap);
        }
        result.put("goods_detail", goodsDetail);
        return JSON.toJSONString(result);
    }

}
