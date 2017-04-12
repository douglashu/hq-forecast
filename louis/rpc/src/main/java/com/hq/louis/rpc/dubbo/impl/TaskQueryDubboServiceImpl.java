package com.hq.louis.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.louis.rpc.dubbo.TaskQueryDubboService;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 07/02/2017.
 */
@Service(interfaceName = "com.hq.louis.rpc.dubbo.TaskQueryDubboService", version = "1.0")
public class TaskQueryDubboServiceImpl extends DubboBaseService implements TaskQueryDubboService {

    @Override
    public RespEntity queryTask(HqRequest request) {
        return null;
    }

}
