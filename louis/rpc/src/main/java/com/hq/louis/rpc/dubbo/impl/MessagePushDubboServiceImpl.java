package com.hq.louis.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.louis.model.req.MessagePushReq;
import com.hq.louis.rpc.dubbo.MessagePushDubboService;
import com.hq.louis.service.core.MessagePushService;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 07/02/2017.
 */
@Service(interfaceName = "com.hq.louis.rpc.dubbo.MessagePushDubboService", version = "1.0")
public class MessagePushDubboServiceImpl extends DubboBaseService implements MessagePushDubboService {

    @Autowired
    private MessagePushService pushService;

    @Override
    public RespEntity pushMessage(HqRequest request) {
        try {
            MessagePushReq pushReq = parseRequest(request, MessagePushReq.class);
            String taskId = this.pushService.push(pushReq);
            return getSuccessResp(MapBuilder.create("taskId", taskId).get());
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
