package com.hq.app.olaf.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayHtmlActivity;
import com.hq.app.olaf.util.AppUtil;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.ui.widget.GetCodeButton;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liaob on 2016/4/22.
 * 注册
 */
@Layout(layoutId = R.layout.activity_register)
public class RegisterActivity extends HqPayActivity implements View.OnClickListener {
    @Bind(R.id.eidt_phone) EditText eidtPhone;
    @Bind(R.id.text_code) TextView textCode;
    @Bind(R.id.getcodebutton) GetCodeButton getcodebutton;
    @Bind(R.id.eidt_code) EditText eidtCode;
    @Bind(R.id.eidt_pwd) EditText eidtPwd;
    @Bind(R.id.cb_agree) CheckBox cbAgree;
    @Bind(R.id.btn_register) Button btnRegister;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.eye) ImageView eye;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getcodebutton.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        initToolbar(toolbar);
    }

    private boolean check() {
        String phone = eidtPhone.getText().toString();
        String code = eidtCode.getText().toString();
        String pwd = eidtPwd.getText().toString();
        if (!cbAgree.isChecked()) {
            showSnackBar("请同意《慧钱注册协议》");
            return false;
        }
        if (TextUtils.isEmpty(phone) || phone.replaceAll(" ", "").length() != 11) {
            showSnackBar("请输入正确的手机号码");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showSnackBar("请输入登录密码");
            return false;
        }
        if (pwd.length() < 6 || AppUtil.checkAllNumPwd(pwd) || AppUtil.checkAllABCPwd(pwd)) {
            showSnackBar("密码为6-16位，字母、数字或符号组合");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (check()) {
                    String phone = eidtPhone.getText().toString().replaceAll(" ", "");
                    HttpTask httpTask = HttpService.getInstance().getAccountCheckExists(phone);
                    NetTools.excute(httpTask, new LoadingDialog(this), new NetTools.CallBack() {
                        @Override
                        public void onComplete(TaskResult taskResult) {
                            if (taskResult.isSuccess()) {
                                String result = (String) taskResult.getResult();
                                JSONObject resultJson = JSON.parseObject(result);
                                boolean exists = resultJson.getBoolean("exists");
                                if (!exists) {
                                    showMessageTips();
                                } else {
                                    showExistMessageTips();
                                }
                            } else {
                                RequestFailedHandler.handleFailed(eidtPhone, taskResult);
                            }
                        }
                    });
                }
                break;
        }
    }

    /**
     * 短信发送提示
     */
    private void showMessageTips() {
        new InfoDialog(RegisterActivity.this,
                "确认手机号码", "短信校验码将发送到您的手机\n+86" + eidtPhone.getText().toString())
                .setContentColor(R.color.black)
                .setCancel("取消", new InfoDialog.OnClickListener() {
                    @Override
                    public void onClick(InfoDialog infoDialog, View button) {
                        infoDialog.dismiss();
                    }
                }).setConfirm("确定", new InfoDialog.OnClickListener() {
            @Override
            public void onClick(InfoDialog infoDialog, View button) {
                infoDialog.dismiss();
                String phone = eidtPhone.getText().toString().replaceAll(" ", "");
                String pwd = eidtPwd.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                bundle.putString("pwd", pwd);
                forward(RegisterSecondActivity.class, bundle);
            }
        }).setDialogCancelable(false)
                .show();
    }

    /**
     * 账号重复提示
     */
    private void showExistMessageTips() {
        new InfoDialog(RegisterActivity.this,
                "提示", "注册账号已存在,请更换手机号码")
                .setContentColor(R.color.black)
                .setCancel("取消", new InfoDialog.OnClickListener() {
                    @Override
                    public void onClick(InfoDialog infoDialog, View button) {
                        infoDialog.dismiss();
                    }
                }).setConfirm("确定", new InfoDialog.OnClickListener() {
            @Override
            public void onClick(InfoDialog infoDialog, View button) {
                infoDialog.dismiss();
            }
        }).setDialogCancelable(false)
                .show();
    }

    @OnClick(R.id.protocol)
    public void toAgreementPager() {
        String registerAgreementUrl = HttpService.getInstance().getRegisterAgreementUrl();
        Bundle bundle = new Bundle();
        bundle.putString("url", registerAgreementUrl);
        forward(HqPayHtmlActivity.class, bundle);
    }

    private boolean isEyeOpen = false;

    @OnClick(R.id.eye)
    public void eye() {
        if (!isEyeOpen) {
            isEyeOpen = true;
            eye.setImageResource(R.drawable.icon_eye_open);
            //如果选中，显示密码
            eidtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isEyeOpen = false;
            eye.setImageResource(R.drawable.icon_eye_close);
            //否则隐藏密码
            eidtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
