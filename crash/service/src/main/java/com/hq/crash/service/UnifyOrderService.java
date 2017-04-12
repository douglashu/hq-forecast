package com.hq.crash.service;

import com.hq.crash.service.common.CrashCommonService;
import com.hq.diego.model.req.OrderTradeReq;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 15/01/2017.
 */
@Service
public class UnifyOrderService extends CrashCommonService {

    public String unifyOrder(OrderTradeReq tradeReq) {
        // 此处需要到订单中心进行统一下单并获取到订单号
        // 由于订单中心正在建设中, 故此处暂时随机生成一个唯一订单号
        // 需要 @zale 完成订单中心建设
        return null;
    }


}
