package com.hq.buck.rpc.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public interface AppDubboService {

    RespEntity saveApp(HqRequest request);

    RespEntity getApp(HqRequest request);

}
