package com.hq.diego.gateway.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 22/03/2017.
 */
public interface MchChannelDubboService {

    RespEntity updateCache(HqRequest request);

    RespEntity getMchChannels(HqRequest request);

    RespEntity saveMchChannel(HqRequest request);

    RespEntity updateMchChannel(HqRequest request);

    RespEntity deleteMchChannel(HqRequest request);

}
