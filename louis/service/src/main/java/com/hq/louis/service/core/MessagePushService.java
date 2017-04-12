package com.hq.louis.service.core;

import com.hq.louis.model.platform.AppPushInfo;
import com.hq.louis.model.req.Message;
import com.hq.louis.model.req.MessagePushReq;
import com.hq.louis.repository.model.PushDevice;
import com.hq.louis.service.PushDeviceService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import java.util.ArrayList;
import java.util.List;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 07/02/2017.
 */
@Service
public class MessagePushService {

    @Autowired
    private MessageTransferStation station;

    @Autowired
    private AppPushInfoService appPushInfoService;

    @Autowired
    private PushDeviceService pushDeviceService;

    public String push(MessagePushReq pushReq) {
        if (pushReq == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if (pushReq.getMessage() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "推送消息不能为空");
        }
        AppPushInfo pushInfo = this.appPushInfoService.getAppPushInfo(pushReq.getAppId());
        if (pushInfo == null) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "推送平台配置信息不存在");
        }
        //
        String grpId = pushReq.getMessage().getGrpId();
        List<PushDevice> pushDeviceList;
        if (StringUtils.isNotBlank(grpId)) {
            pushDeviceList = this.pushDeviceService.getDeviceTokenByGrpId(grpId);
        } else if (pushReq.getMessage().getAddress() != null
                && pushReq.getMessage().getAddress().size() > 0){
            pushDeviceList = this.pushDeviceService.getDeviceTokenByKeys(pushReq.getMessage().getAddress());
        } else {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "推送消息地址不能为空");
        }
        if (pushDeviceList == null || pushDeviceList.size() == 0) {
            throw new CommonException(CommonErrCode.BUSINESS, "没有找到可推送的设备");
        }
        //
        List<PushDevice> iosDevice = new ArrayList<>();
        List<PushDevice> androidDevice = new ArrayList<>();
        pushDeviceList.stream().forEach(o -> {
            if (o.getOsplatform().toLowerCase().equals("ios")) {
                iosDevice.add(o);
            } else if (o.getOsplatform().toLowerCase().equals("android")) {
                androidDevice.add(o);
            }
        });
        //
        List<Message> messages = new ArrayList<>();
        if (iosDevice.size() > 0) {
            Message message = new Message();
            BeanUtils.copyProperties(pushReq.getMessage(), message);
            message.setDevices("");
            message.setOsPlatform("IOS");
            if (iosDevice.size() == 1) {
                message.setType("single");
                message.setDevices(iosDevice.get(0).getDeviceToken());
            } else {
                message.setType("multi");
                iosDevice.stream().filter(o -> o != null).forEach(x -> {
                    message.setDevices(message.getDevices() + "," + x.getDeviceToken().trim());
                });
                message.setDevices(message.getDevices().substring(1));
            }
            messages.add(message);
        }
        if (androidDevice.size() > 0) {
            Message message = new Message();
            BeanUtils.copyProperties(pushReq.getMessage(), message);
            message.setDevices("");
            message.setOsPlatform("Android");
            if (androidDevice.size() == 1) {
                message.setType("single");
                message.setDevices(androidDevice.get(0).getDeviceToken());
            } else {
                message.setType("multi");
                androidDevice.stream().filter(o -> o != null).forEach(x -> {
                    message.setDevices(message.getDevices() + "," + x.getDeviceToken());
                });
                message.setDevices(message.getDevices().substring(1));
            }
            messages.add(message);
        }
        MsgPushTask task = this.station.queueMsgPushTask(messages, pushInfo);
        return task.getId();
    }

}
