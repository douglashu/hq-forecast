package com.hq.diego.gateway.service.core.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.hq.diego.gateway.constant.config.GatewayConfig;
import com.hq.diego.gateway.constant.url.AliPayUrl;
import com.hq.diego.gateway.service.core.common.PrepayOrderGenService;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.req.ali.AliOrderPrepayReq;
import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.diego.model.route.ChannelRoute;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by zhaoyang on 29/11/2016.
 */
@Service("AliH5PayService")
public class AliH5PayService implements PrepayOrderGenService {

    private static final Logger logger = Logger.getLogger(AliH5PayService.class);

    @Override
    public OrderPrepayResp genPrepayOrder(OrderTradeReq tradeReq, ChannelRoute route) {
        final AliOrderPrepayReq prepayReq = new AliOrderPrepayReq();
        prepayReq.setOut_trade_no(String.valueOf(System.currentTimeMillis()));
        prepayReq.setTotal_amount(BigDecimal.valueOf(1));
        prepayReq.setSubject("hello world");
        // prepayReq.setProduct_code("QUICK_WAP_PAY");

        try {


            AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
            request.setBizContent(JSON.toJSONString(prepayReq));
            AlipayClient alipayClient = new DefaultAlipayClient(AliPayUrl.getGatewayUrl(), GatewayConfig.ALIPAY_APP_ID
                    , GatewayConfig.ALIPAY_APP_PKCS8_PRIVATE_KEY, "JSON", "utf-8", GatewayConfig.ALIPAY_PKCS8_PUBLIC_KEY);
            String form = alipayClient.pageExecute(request).getBody();
            System.out.println(form);



        } catch (Throwable th) {
            logger.warn("<<< Create Ali H5 PAY Fail", th);

            th.printStackTrace();


        }



        return null;
    }
}
