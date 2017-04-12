package com.hq.diego.gateway.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 09/12/2016.
 */
public interface TradePollingDubboService {

    RespEntity polling(HqRequest request);

}
