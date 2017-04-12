package com.hq.diego.gateway.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 02/01/2017.
 */
public interface TradeQueryDubboService {

    RespEntity query(HqRequest request);

    RespEntity queryOne(HqRequest request);

}
