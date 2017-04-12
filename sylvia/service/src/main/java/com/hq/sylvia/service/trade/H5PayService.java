package com.hq.sylvia.service.trade;

import com.alibaba.fastjson.JSON;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.scrati.common.constants.trade.TradeTypes;
import com.hq.scrati.model.HqRequest;
import com.hq.sylvia.model.req.PrepayOrderReq;
import com.hq.sylvia.service.common.SylviaCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 19/01/2017.
 */
@Service
public class H5PayService extends SylviaCommonService {

    @Autowired
    private PrepayOrderService prepayOrderService;

    public OrderPrepayResp createPrepayOrder(PrepayOrderReq orderReq, String payChannel) {
        OrderTradeReq tradeReq = this.prepayOrderService.createPrepayOrder(orderReq, payChannel);
        tradeReq.setTradeType(TradeTypes.H5_JSAPI.getCode());
        tradeReq.setOpenId(orderReq.getOpenId());
        HqRequest request = new HqRequest();
        request.setMchId(tradeReq.getMchId());
        request.setBizContent(JSON.toJSONString(tradeReq));
        return invoke("diego.trade.pay", "1.0", request, OrderPrepayResp.class);
    }

}
