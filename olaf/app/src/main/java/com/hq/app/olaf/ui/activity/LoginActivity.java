package com.hq.app.olaf.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.activity.passwd.ForgetPwdActitivy;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Role;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.bean.order.Order;
import com.hq.app.olaf.ui.bean.page.Pager;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.util.SharedPrefUtil;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.network.service.impl.QueueTask;
import com.hq.component.utils.JSON;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liaob on 2016/4/22.
 */
@Layout(layoutId = R.layout.activity_login)
public class LoginActivity extends HqPayActivity {

    @Bind(R.id.et_phone) EditText etPhone;
    @Bind(R.id.et_pwd) EditText etPwd;

    private HttpService httpService;
    //private HttpTask getChanglle;
    private HttpTask login;

    private final static String LOGIN_NAME = "login_name";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        httpService = HttpService.getInstance();
        String loginName = SharedPrefUtil.getInstance().load(LOGIN_NAME);
        if (!TextUtils.isEmpty(loginName)) {
            etPhone.setText(loginName);
        }
    }

    private boolean checkLogin() {
        String phone = etPhone.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(phone) && phone.replaceAll(" ", "").length() != 11) {
            showSnackBar("请输入11位手机号码");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showSnackBar("请输入登录密码");
            return false;
        }
        if (pwd.length() < 6) {
            showSnackBar("密码长度最少6位");
            return false;
        }
        return true;
    }

    @OnClick(R.id.btn_login)
    public void login() {
        //MobclickAgent.onEvent(this, "LoginButton");
        if (checkLogin()) {
            String phone = etPhone.getText().toString().replaceAll(" ", "");
            String pwd = etPwd.getText().toString();

            Session.clear();

           // getChanglle = httpService.getChallenge(phone);
            login = httpService.postSessions(phone, pwd);
            LoadingDialog loadingDialog = new LoadingDialog(this);
            TextView textView = (TextView) loadingDialog.findViewById(R.id.loadingText);
            textView.setText("正在登录中...");
           // NetTools.excuteQueue(loginCallback, loadingDialog, getChanglle, login);
            NetTools.excuteQueue(loginCallback, loadingDialog,  login);
            isOpenUpdateDialog = true;
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    ThreadPoolTool.getInstance().cancelAll();
                    //getChanglle.cancel();
                    login.cancel();
                }
            });

        }
    }

    /**
     * 登录响应处理
     */
    private NetTools.CallBack loginCallback = new NetTools.CallBack() {
        @Override
        public boolean doBackground(QueueTask queueTask, TaskResult taskResult) {
            if (taskResult != null) {
                if (taskResult.isTaskResult(login)) {//登录成功保存session
                    if (taskResult.isSuccess()) {

                    }
                }
            }
            return true;
        }

        /**
         * UI运行
         * @param taskResult
         */
        public void onComplete(TaskResult taskResult) {
            if (taskResult.isSuccess()) {
            //if (!taskResult.isSuccess()) {
                if (taskResult.isTaskResult(login)) {//查询进件信息

                    if (taskResult.getCode()>=200 && taskResult.getCode()<300) {
                        Session.clear();
                        Session session = JSONObject.parseObject((String) taskResult.getResult(), new TypeReference<Session>() {
                        }.getType());

                        if(session!=null){
                            session.save();
                        }

                        //MobclickAgent.onEvent(LoginActivity.this, "LoginSuccess");
                        /*SharedPreferences preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
                        Boolean isFirstIn = preferences.getBoolean("isFirstIn", true);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isFirstIn", false);
                        editor.apply();*/
                        SharedPrefUtil.getInstance().save(LOGIN_NAME, etPhone.getText().toString());
                        forward(NewIndexActivity.class);
                        finish();
                    }


                }
            } else if (!taskResult.isCancel()) {
                //登录错误消息提示
                RequestFailedHandler.handleFailed(etPwd, taskResult);
            }
        }

        @Override
        public void onComplete(Map<String, TaskResult> taskResults) {
            isOpenUpdateDialog = false;
        }
    };


    @OnClick(R.id.btn_register)
    public void register() {
        //MobclickAgent.onEvent(this, "RegisterButton");
        forward(RegisterActivity.class);


    }

    @OnClick(R.id.text_forget_pwd)
    public void forgetPwd() {
        //MobclickAgent.onEvent(this, "ForgetLoginpwdButton");
        forward(ForgetPwdActitivy.class);

    }

    private boolean isOpenUpdateDialog;
    private boolean isExit;

    /**
     * 重载按键侦听
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isOpenUpdateDialog) {
            isOpenUpdateDialog = false;
            return super.dispatchKeyEvent(event);
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                returnToHome();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 两次返回退出
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            showSnackBar("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            getApp().exitApp();
        }
    }

}
