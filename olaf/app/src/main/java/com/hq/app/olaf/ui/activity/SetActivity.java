package com.hq.app.olaf.ui.activity;

import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSONObject;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.widget.PaySucMsgSwitchView;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.annotation.Layout;
import com.hq.component.base.AppActManager;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.network.service.impl.QueueTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置
 */
@Layout(layoutId = R.layout.activity_setting)
public class SetActivity extends HqPayActivity {
    public final static int MSG_SWITCH = 1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.msgSwitchView)
    PaySucMsgSwitchView msgSwitchView;

    private JSONObject d0JsonObject;
    private HttpTask msgUpdate;
    private HttpTask msgQueryTask;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        handler = new Handler();
//        new CustomThread().start();
        msgQueryTask = HttpService.getInstance().management_msg_getResult();
        initData();

        d0JsonObject = (JSONObject) getApp().getCache("msgState");
        if(d0JsonObject!=null) {
            final boolean acceptTradePush = d0JsonObject.getBoolean("acceptTradePush");
            msgSwitchView.switchState(acceptTradePush, false);
        }else{
            msgSwitchView.switchState(true, true);
        }

        msgSwitchView.setSwitchListener(new PaySucMsgSwitchView.SwitchListener() {
            @Override
            public boolean onSwitch(int state) {
                boolean isOpen = false;
                switch (state) {
                    case PaySucMsgSwitchView.STATE_CLOSE:
                        isOpen = false;//更新状态
                        break;
                    case PaySucMsgSwitchView.STATE_OPEN:
                        isOpen = true;
                        break;
                }
                msgUpdate = HttpService.getInstance().management_msg_update(isOpen);
                final boolean finalIsOpen = isOpen;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        msgSwitchView.switchState(finalIsOpen, true);
                    }
                });
                NetTools.excuteQueue(new HqNetCallBack(getRootView()) {
                    @Override
                    public void dofinally(TaskResult taskResult) {
                        if (taskResult.isTaskResult(msgUpdate)) {
                            if (!taskResult.isSuccess()) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        msgSwitchView.switchState(!finalIsOpen, true);
                                    }
                                });
                            }else{
                                String result = "{'acceptTradePush':"+finalIsOpen+"}";
                                JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(result);
                                getApp().saveCache("msgState", jsonObject);
                            }
                        }
                    }

                    @Override
                    protected void doComplete(TaskResult taskResult) {
                        if (taskResult.isTaskResult(msgUpdate)) {
                                String result = "{'acceptTradePush':"+finalIsOpen+"}";
                                JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(result);
                                getApp().saveCache("msgState", jsonObject);
                        }
                    }
                }, new LoadingDialog(SetActivity.this), msgUpdate);
                return false;
            }
        });
    }
//    class CustomThread extends Thread {
//        @Override
//        public void run() {
//            //建立消息循环的步骤
//            Looper.prepare();//1、初始化Looper
//            handler = new Handler(){//2、绑定handler到CustomThread实例的Looper对象
//                public void handleMessage (Message msg) {//3、定义处理消息的方法
//                    switch(msg.what) {
//                        case MSG_SWITCH:
//                            msgSwitchView.switchState((Boolean) msg.obj, true);
//                            break;
//                    }
//                }
//            };
//            Looper.loop();//4、启动消息循环
//        }
//    }
    @OnClick(R.id.logout)
    public void logout() {
        showTipsDialog("确定退出当前登录?", "确定", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                HttpTask logout = HttpService.getInstance().deleteSessions();
                LoadingDialog loadingDialog =new LoadingDialog(SetActivity.this);
                TextView textView = (TextView) loadingDialog.findViewById(R.id.loadingText);
                textView.setText("正在退出登录...");
                NetTools.excute(logout, loadingDialog, new NetTools.CallBack() {
                    @Override
                    public void onComplete(TaskResult taskResult) {
                        Session.clear();
                        ThreadPoolTool.getInstance().cancelAll();
                        showSnackBar("操作员已退出");
                        AppActManager.getInstance().exit();
                        forward(LoginActivity.class);
                    }
                });
            }
        });
    }


    private void initData(){
        NetTools.excute(msgQueryTask,new LoadingDialog(SetActivity.this),new HqNetCallBack(getRootView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                        String result = (String) taskResult.getResult();
                        JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(result);
                        getApp().saveCache("msgState", jsonObject);

                }
            }
        } );
     }

}
