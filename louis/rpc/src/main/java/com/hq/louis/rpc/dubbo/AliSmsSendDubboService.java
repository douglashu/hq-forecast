package com.hq.louis.rpc.dubbo;

import com.hq.louis.model.resp.SMSResp;
import com.hq.scrati.model.HqRequest;

/**
 * Created by Zale on 2017/2/15.
 */
public interface AliSmsSendDubboService {
    SMSResp send(HqRequest hqRequest);
}
