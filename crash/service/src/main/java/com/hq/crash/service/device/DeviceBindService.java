package com.hq.crash.service.device;

import com.alibaba.fastjson.JSON;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.louis.model.req.DeviceBindReq;
import com.hq.louis.model.req.DeviceunBindReq;
import com.hq.scrati.common.constants.OSPlatform;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.model.HqRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 13/01/2017.
 */
@Service
public class DeviceBindService extends CrashCommonService {

    public void bind(DeviceBindReq deviceBindReq, UserSession userSession) {
        if (OSPlatform.fromString(deviceBindReq.getOsplatform()) == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "设备操作系统平台错误");
        }
        if (StringUtils.isEmpty(deviceBindReq.getDeviceToken())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "推送设备号不能为空");
        }
        HqRequest request = getHqRequest(userSession);
        deviceBindReq.setExpiryTime(userSession.getExpiryTime());
        request.setBizContent(JSON.toJSONString(deviceBindReq));
        invoke("louis.device.bind", "1.0", request, null);
    }

    public void unbind(DeviceunBindReq req, String appId, String userId) {
        HqRequest request = new HqRequest();
        request.setAppId(appId);
        request.setUserId(userId);
        request.setBizContent(JSON.toJSONString(req));
        invoke("louis.device.unbind", "1.0", request, null);
    }

}
