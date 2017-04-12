package com.hq.buck.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.buck.model.app.AppVersion;
import com.hq.buck.rpc.dubbo.AppVersionDubboService;
import com.hq.buck.service.app.AppVersionService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaoyang on 09/02/2017.
 */
@Service(interfaceName = "com.hq.buck.rpc.dubbo.AppVersionDubboService", version = "1.0")
public class AppVersionDubboServiceImpl
        extends DubboBaseService implements AppVersionDubboService {

    @Autowired
    private AppVersionService appVersionService;

    @Override
    public RespEntity createVersion(HqRequest request) {
        try {
            AppVersion appVersion = parseRequest(request, AppVersion.class);
            this.appVersionService.createVersion(appVersion);
            return getSuccessResp("应用版本创建成功");
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

    @Override
    public RespEntity checkVersion(HqRequest request) {
        try {
            JSONObject jsonObject = JSON.parseObject(request.getBizContent());
            Object respObj = this.appVersionService
                    .checkVersion(
                        jsonObject.getString("appId"),
                        jsonObject.getString("platform"),
                        jsonObject.getString("version")
                    );
            return getSuccessResp(respObj);
        } catch (Throwable th) {
            return getErrorResp(th);
        }
    }

}
