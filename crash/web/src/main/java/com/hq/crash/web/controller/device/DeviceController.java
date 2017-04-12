package com.hq.crash.web.controller.device;

import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.device.DeviceBindService;
import com.hq.crash.web.controller.common.BaseController;
import com.hq.louis.model.req.DeviceBindReq;
import com.hq.louis.model.req.DeviceunBindReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoyang on 06/02/2017.
 */
@RestController
public class DeviceController extends BaseController {

    @Autowired
    private DeviceBindService deviceBindService;

    @RequestMapping(value = "/device", method = RequestMethod.PUT)
    public ResponseEntity bindDevice(HttpServletRequest request, @RequestBody DeviceBindReq deviceBindReq) {
        UserSession userSession = getUserSession(request);
        deviceBindReq.setKey(userSession.getAppId() + ":" + userSession.getUserId());
        deviceBindReq.setGrpId(userSession.getActiveMch());
        this.deviceBindService.bind(deviceBindReq, userSession);
        return getMessageResp("设备绑定成功");
    }

    @RequestMapping(value = "/device", method = RequestMethod.DELETE)
    public ResponseEntity unBindDevice(HttpServletRequest request) {
        UserSession userSession = getUserSession(request);
        DeviceunBindReq deviceunBindReq = new DeviceunBindReq();
        deviceunBindReq.setKey(userSession.getAppId() + ":" + userSession.getUserId());
        this.deviceBindService.unbind(
                deviceunBindReq, userSession.getAppId(), userSession.getUserId());
        return getMessageResp("设备解绑成功");
    }

}
