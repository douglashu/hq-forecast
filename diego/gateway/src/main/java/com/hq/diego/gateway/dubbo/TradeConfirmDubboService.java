package com.hq.diego.gateway.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 30/01/2017.
 */
public interface TradeConfirmDubboService {

    RespEntity onTradeNotify(HqRequest request);

}
