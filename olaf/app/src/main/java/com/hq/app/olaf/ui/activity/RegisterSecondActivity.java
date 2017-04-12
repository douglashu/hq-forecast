package com.hq.app.olaf.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayHtmlActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.ui.widget.GetCodeButton;
import com.hq.component.annotation.Layout;
import com.hq.component.base.AppActManager;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.JSON;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.activity_register_second)
public class RegisterSecondActivity extends HqPayActivity implements View.OnClickListener {
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
        eidtPhone.setText(getIntent().getExtras().getString("phone"));
        getcodebutton.start(eidtPhone.getText().toString(), "0");
    }

    private boolean check() {
        String phone = eidtPhone.getText().toString();
        String code = eidtCode.getText().toString();
        if (!cbAgree.isChecked()) {
            showSnackBar("请同意《卡友支付商户协议》");
            return false;
        }
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showSnackBar("请输入正确的手机号码");
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            showSnackBar("请输入6位验证码");
            return false;
        }
        return true;
    }

    /**
     * 调用注册接口
     */
    private void register() {
//String encypt=RSA.encrypt(pwd, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVgV64QwTf7FCUETp64TJJy/GIox8vEPsJKiwfLTNwUNOXFH5SeEJ/kY7F2SNnRk/ZUTLzl1eTV0U3AGqhZSoJBHTBnwlQM6fdNVuR30J3U9lgtFXZL84aX9Ke3Ft8ZGYnjd+sjpz9cdvjFXpXt/RKYBwM8Ul4PCDPqUxEAtlaWQIDAQAB",  "utf-8");
        String phone = eidtPhone.getText().toString();
        String code = eidtCode.getText().toString();
        String pwd = getIntent().getStringExtra("pwd");
        HttpTask postAccount = HttpService.getInstance().postAccount(phone, code, pwd);
        NetTools.excute(postAccount, new LoadingDialog(this), new NetTools.CallBack() {
            @Override
            public void onComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    //MobclickAgent.onEvent(RegisterSecondActivity.this, "RegisterSuccess");
                    Session session = JSON.parseObject(taskResult.getResult().toString(), Session.class);
                    session.save();
                    showSnackBar("注册成功");
                    AppActManager.getInstance().exit();
                    forward(NewIndexActivity.class);
                } else {
                    RequestFailedHandler.handleFailed(getRootView(), taskResult);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (check()) {
                    register();

                }
                break;
            case R.id.getcodebutton:
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                getcodebutton.start(eidtPhone.getText().toString(), "0");
                break;

        }
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
