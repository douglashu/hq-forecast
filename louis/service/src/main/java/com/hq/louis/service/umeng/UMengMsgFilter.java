package com.hq.louis.service.umeng;

import com.hq.louis.model.req.Message;
import com.hq.louis.model.apns.APNs;
import com.hq.louis.model.apns.APNsAlert;
import com.hq.louis.model.constant.PushConstant;
import com.hq.louis.model.platform.AppPushInfo;
import com.hq.louis.model.umeng.UMengMsg;
import com.hq.louis.model.umeng.UMengMsgBody;
import com.hq.louis.model.umeng.UMengMsgPayload;
import com.hq.louis.model.umeng.UMengMsgSendPolicy;
import com.hq.scrati.common.constants.OSPlatform;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import java.util.HashMap;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public class UMengMsgFilter {

    public UMengMsg filter(Message message, AppPushInfo appPushInfo) {
        UMengMsg msg = new UMengMsg();
        msg.setPayload(new UMengMsgPayload());
        msg.setPolicy(new UMengMsgSendPolicy());
        msg.setTimestamp(String.valueOf(message.getTimestamp()));
        msg.setType(getType(message));
        msg.setDevice_tokens(message.getDevices());

        OSPlatform osPlatform = OSPlatform.fromString(message.getOsPlatform());
        if(OSPlatform.Android.equals(osPlatform)) {
            msg.setAppkey(appPushInfo.getAndroidAppKey());
            msg.getPayload().setBody(new UMengMsgBody());
            msg.getPayload().setDisplay_type("notification");
            msg.getPayload().getBody().setTicker(message.getTicker());
            msg.getPayload().getBody().setTitle(message.getTitle());
            msg.getPayload().getBody().setText(message.getContent());
            if(!StringUtils.isEmpty(message.getActivity())) {
                msg.getPayload().getBody().setAfter_open("go_activity");
                msg.getPayload().getBody().setActivity(message.getActivity());
            } else {
                msg.getPayload().getBody().setAfter_open("go_app");
            }
            if(message.getPlayLights() != null && !message.getPlayLights()) {
                msg.getPayload().getBody().setPlay_lights("false");
            }
            if(message.getPlaySound() != null && !message.getPlaySound()) {
                msg.getPayload().getBody().setPlay_sound("false");
            }
            if(message.getPlayVibrate() != null && !message.getPlayVibrate()) {
                msg.getPayload().getBody().setPlay_vibrate("false");
            }
            msg.getPayload().getBody().setSound(message.getSound());
            if(!StringUtils.isEmpty(message.getCustom())) {
                msg.getPayload().setExtra(new HashMap<>());
                msg.getPayload().getExtra().put("custom", message.getCustom());
            }
        } else if(OSPlatform.IOS.equals(osPlatform)) {
            msg.setAppkey(appPushInfo.getIosAppKey());
            msg.getPayload().setAps(new APNs());
            APNsAlert alert = new APNsAlert(message.getTitle(), message.getContent());
            alert.setSubtitle(message.getSubTitle());
            msg.getPayload().getAps().setAlert(alert);
            if(message.getBadge() != null && message.getBadge() > 0) {
                msg.getPayload().getAps().setBadge(message.getBadge());
            }
            msg.getPayload().getAps().setSound(message.getSound());
            if(!StringUtils.isEmpty(message.getCustom())) {
                msg.getPayload().setCustom(message.getCustom());
            }
        }
        if(message.getSendTime() != null) {
            String sendTime = (new DateTime(message.getSendTime())).toString("YYYY-MM-dd HH:mm:ss");
            msg.getPolicy().setStart_time(sendTime);
        }
        String expiryTime = (new DateTime(message.getExpiryTime())).toString("YYYY-MM-dd HH:mm:ss");
        msg.getPolicy().setExpire_time(expiryTime);
        msg.setThirdparty_id(message.getOuterId());
        msg.setDescription(message.getDescription());
        msg.setProduction_mode(appPushInfo.getProdMode()? "true": "false");
        return msg;
    }

    private String getType(Message pushMsg) {
        if(PushConstant.Type.SINGLE.equals(pushMsg.getType())) {
            return PushConstant.UMengType.UNICAST;
        } else if(PushConstant.Type.MULTI.equals(pushMsg.getType())) {
            return PushConstant.UMengType.LISTCAST;
        } else if(PushConstant.Type.ALL.equals(pushMsg.getType())) {
            return PushConstant.UMengType.BROADCAST;
        }
        return null;
    }
}
