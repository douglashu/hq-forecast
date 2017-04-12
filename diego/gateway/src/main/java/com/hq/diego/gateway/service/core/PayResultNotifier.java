package com.hq.diego.gateway.service.core;

import com.hq.redis.pubsub.PubService;
import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.common.constants.Message;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by zhaoyang on 31/01/2017.
 */
@Service
public class PayResultNotifier {

    private static final Logger logger = Logger.getLogger(PayResultNotifier.class);

    @Autowired
    private PubService pubService;

    public void notifyPayResult(String mchId
            , Integer operatorId, String operatorName, String orderId
            , String payChannel, String tradeType, Long totalAmount) {
        try {
            Message message = new Message();
            message.setMchId(mchId);
            message.setOrderId(orderId);
            message.setPayChannel(payChannel);
            message.setTradeType(tradeType);
            message.setTotalAmount(BigDecimal.valueOf(totalAmount));
            message.setOperatorId(operatorId);
            message.setOperatorName(operatorName);
            message.setTimestamp(DateTime.now().getMillis());
            this.pubService.publish(message,"HQ_QRCODE_TRADE_TOPIC", Arrays.asList(RedisKeyConfigure.USER_CENTER_KEY,RedisKeyConfigure.TRADE_CENTER_KEY));
            logger.info("<<<<<< Publish Trade Result(OrderId=" + orderId
                    + ", Timestamp=" + message.getTimestamp());
        } catch (Throwable th) {
            logger.warn("<<<<<< Publish Trade Success Message Fail");
            logger.warn("<<<<<< MchId: " + mchId);
            logger.warn("<<<<<< OrderId: " + orderId);
            logger.warn("<<<<<< PayChannel: " + payChannel);
            logger.warn("<<<<<< TradeType: " + tradeType);
        }
    }
}
