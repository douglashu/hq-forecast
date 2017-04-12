package com.hq.diego.gateway.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 22/03/2017.
 */
public interface TradeChannelDubboService {

    RespEntity updateCache();

    RespEntity getTradeChannels();

    RespEntity createTradeChannel(HqRequest request);

    RespEntity updateTradeChannel(HqRequest request);

}
