package com.hq.louis.rpc.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 07/02/2017.
 */
public interface MessagePushDubboService {

    RespEntity pushMessage(HqRequest request);

}
