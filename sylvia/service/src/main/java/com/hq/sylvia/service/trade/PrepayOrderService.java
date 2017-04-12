package com.hq.sylvia.service.trade;

import com.hq.diego.model.req.OrderTradeReq;
import com.hq.scrati.common.constants.trade.TradeTypes;
import com.hq.sid.service.entity.response.QrCodeRsp;
import com.hq.sylvia.model.req.PrepayOrderReq;
import com.hq.sylvia.service.baseinfo.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 08/02/2017.
 */
@Service
public class PrepayOrderService {

    @Autowired
    private QrCodeService qrCodeService;

    public OrderTradeReq createPrepayOrder(PrepayOrderReq orderReq, String payChannel) {
        QrCodeRsp storeCode = this.qrCodeService.getQrStoreCode(orderReq.getCodeId());
        OrderTradeReq tradeReq = new OrderTradeReq();
        tradeReq.setMchId(storeCode.getMchId());
        tradeReq.setTradeType(TradeTypes.H5_JSAPI.getCode());
        tradeReq.setPayChannel(payChannel);
        tradeReq.setTerminalId("STC@" + storeCode.getId());
        tradeReq.setOperatorId(String.valueOf(storeCode.getId()));
        tradeReq.setOperatorName(storeCode.getName());
        tradeReq.setOpenId(orderReq.getOpenId());
        tradeReq.setIpAddress("0.0.0.0");
        tradeReq.setFeeType("CNY");
        tradeReq.setTotalAmount(orderReq.getTotalAmount());
        if(StringUtils.isEmpty(tradeReq.getTitle())) {
            if(StringUtils.isEmpty(storeCode.getStoreName())) {
                tradeReq.setTitle(storeCode.getMchShortName());
            } else {
                tradeReq.setTitle(storeCode.getMchShortName() + "-" + storeCode.getStoreName());
            }
        }
        if(StringUtils.isEmpty(tradeReq.getBody())) {
            tradeReq.setBody(tradeReq.getTitle());
        }
        tradeReq.setCustom(null);
        tradeReq.settStoreId(null);
        return tradeReq;
    }

}
