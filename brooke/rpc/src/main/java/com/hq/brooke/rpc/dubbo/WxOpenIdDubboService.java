package com.hq.brooke.rpc.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 05/01/2017.
 */
public interface WxOpenIdDubboService {

    RespEntity getUserOpenId(HqRequest request);

}
