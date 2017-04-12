package com.hq.buck.rpc.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 03/01/2017.
 */
public interface InAppDubboService {

    RespEntity getMyInApps(HqRequest request);

    RespEntity getMyInAppCats(HqRequest request);

    RespEntity updateMyInApps(HqRequest request);

}
