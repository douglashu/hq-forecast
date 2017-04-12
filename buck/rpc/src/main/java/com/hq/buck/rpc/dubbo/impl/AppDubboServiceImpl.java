package com.hq.buck.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.buck.model.app.App;
import com.hq.buck.rpc.dubbo.AppDubboService;
import com.hq.buck.service.app.AppService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 21/12/2016.
 */
@Service(interfaceName = "com.hq.buck.rpc.dubbo.AppDubboService", version = "1.0")
public class AppDubboServiceImpl extends DubboBaseService implements AppDubboService {

    @Autowired
    private AppService appService;

    @Override
    public RespEntity saveApp(HqRequest request) {
        try {
            App app = parseRequest(request, App.class);
            this.appService.saveApp(app);
            return getSuccessResp("应用创建成功");
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    public RespEntity getApp(HqRequest request) {
        try {
            return getSuccessResp(this.appService.getApp(request.getAppId()));
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
