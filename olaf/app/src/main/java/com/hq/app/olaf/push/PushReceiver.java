package com.hq.app.olaf.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.util.SharedPrefUtil;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.orhanobut.logger.Logger;
import com.umeng.message.MsgConstant;
import com.umeng.message.common.UmengMessageDeviceConfig;

/**
 * Created by huwentao on 16/11/4.
 */

public class PushReceiver extends BroadcastReceiver {

    public PushReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String deviceToken = intent.getStringExtra("deviceToken");
        SharedPrefUtil.getInstance().save("deviceToken", deviceToken);
        if (!TextUtils.isEmpty(deviceToken)) {
            HttpTask httpTask = HttpService.getInstance().management_push_bind(deviceToken);
            NetTools.excute(httpTask, new NetTools.CallBack() {
                @Override
                public void onComplete(TaskResult taskResult) {
                    Logger.d("Register deviceToken Result:%s", taskResult);
                }
            });
        }
        String pkgName = HqPayApplication.getAppContext().getPackageName();
        String info = String.format("deviceToken:%s\nSdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                deviceToken,
                MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(context),
                UmengMessageDeviceConfig.getAppVersionName(context));
        Logger.d("应用包名:" + pkgName + "\n" + info);

    }
}
