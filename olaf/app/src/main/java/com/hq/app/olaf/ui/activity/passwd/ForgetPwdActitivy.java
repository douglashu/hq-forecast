package com.hq.app.olaf.ui.activity.passwd;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.util.AppUtil;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.ui.widget.GetCodeButton;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liaob on 2016/4/22.
 */
@Layout(layoutId = R.layout.activity_forgetpwd)
public class ForgetPwdActitivy extends HqPayActivity {

    @Bind(R.id.eidt_phone) EditText eidtPhone;
    @Bind(R.id.getcodebutton) GetCodeButton getcodebutton;
    @Bind(R.id.eidt_code) EditText eidtCode;
    @Bind(R.id.eidt_pwd) EditText eidtPwd;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.showEye)
    ImageView showEye;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
    }

    private boolean check() {
        String phone = eidtPhone.getText().toString();
        String code = eidtCode.getText().toString();
        String pwd = eidtPwd.getText().toString();

        if (TextUtils.isEmpty(phone) || phone.replaceAll(" ", "").length() != 11) {
            showSnackBar("请输入正确的手机号码");
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            showSnackBar("请输入6位验证码");
            return false;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || AppUtil.checkAllNumPwd(pwd) || AppUtil.checkAllABCPwd(pwd)) {
            showSnackBar("密码为6-16位，字母、数字或符号组合");
            return false;
        }
        return true;
    }

    @OnClick(R.id.getcodebutton)
    public void getCode() {
        getcodebutton.start(eidtPhone.getText().toString().replaceAll(" ", ""), "1");
    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        if (check()) {
            String phone = eidtPhone.getText().toString().replaceAll(" ", "");
            String code = eidtCode.getText().toString();
            String pwd = eidtPwd.getText().toString();
            HttpTask putAccount = HttpService.getInstance().putAccount(phone, code, pwd);
            NetTools.excute(putAccount, new LoadingDialog(this), new HqNetCallBack(getRootView()) {
                @Override
                public void doComplete(TaskResult taskResult) {
                    if (taskResult.isSuccess()) {
                        new MaterialDialog.Builder(ForgetPwdActitivy.this)
                                .title("提示")
                                .content("重置密码成功,请重新登录")
                                .cancelable(false)
                                .negativeText("确定")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Session.clear();
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).build().show();
                    }
                }
            });
        }
    }

    private boolean isEyeOpen = false;

    @OnClick(R.id.showEye)
    public void showEye() {
        if (!isEyeOpen) {
            isEyeOpen = true;
            showEye.setImageResource(R.drawable.icon_eye_open);
            //如果选中，显示密码
            eidtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isEyeOpen = false;
            showEye.setImageResource(R.drawable.icon_eye_close);
            //否则隐藏密码
            eidtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

}
