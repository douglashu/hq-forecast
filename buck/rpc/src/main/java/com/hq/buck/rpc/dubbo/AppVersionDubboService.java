package com.hq.buck.rpc.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 09/02/2017.
 */
public interface AppVersionDubboService {

    RespEntity createVersion(HqRequest request);

    RespEntity checkVersion(HqRequest request);

}
