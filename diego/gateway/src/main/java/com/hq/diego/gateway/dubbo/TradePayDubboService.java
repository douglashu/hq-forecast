package com.hq.diego.gateway.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public interface TradePayDubboService {

    RespEntity pay(HqRequest request);

}
