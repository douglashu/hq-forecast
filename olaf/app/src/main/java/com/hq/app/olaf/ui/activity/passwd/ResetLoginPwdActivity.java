package com.hq.app.olaf.ui.activity.passwd;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.activity.LoginActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.util.AppUtil;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.ui.widget.GetCodeButton;
import com.hq.component.annotation.Layout;
import com.hq.component.base.AppActManager;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;

import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.activity_reset_login_pwd)
public class ResetLoginPwdActivity extends HqPayActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.eidt_code) EditText eidtCode;
    @Bind(R.id.newPwd) EditText newPwd;
    @Bind(R.id.showEye) ImageView showEye;
    @Bind(R.id.getcodebutton) GetCodeButton getcodebutton;

    private boolean check() {
        String code = eidtCode.getText().toString();
        String pwd = newPwd.getText().toString();

        if (TextUtils.isEmpty(code)) {
            showSnackBar("请输入验证码");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showSnackBar("请输入登录密码");
            return false;
        }
        if (newPwd.length() < 6 || AppUtil.checkAllNumPwd(pwd) || AppUtil.checkAllABCPwd(pwd)) {
            showSnackBar("密码为6-16位，字母、数字或符号组合");
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
    }

    @OnClick(R.id.commit)
    public void commit() {
        if (check()) {
            String phone = Session.load().getMobile();
            String code = eidtCode.getText().toString();
            String pwd = newPwd.getText().toString();
            HttpTask putAccount = HttpService.getInstance().putAccount(phone, code, pwd);
            NetTools.excute(putAccount, new LoadingDialog(this), new HqNetCallBack(getRootView()) {
                @Override
                public void doComplete(TaskResult taskResult) {
                    if (taskResult.isSuccess()) {
                        //MobclickAgent.onEvent(ResetLoginPwdActivity.this, "ResetLoginpwdSuccess");
                        showTipsDialog("重置密码成功,请重新登录",
                                new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Session.clear();
                                        dialog.dismiss();
                                        AppActManager.getInstance().exit();
                                        forward(LoginActivity.class);
                                    }
                                });
                    }
                }
            });
        }
    }

    @OnClick(R.id.getcodebutton)
    public void getCode() {
        String phone = Session.load().getMobile();
        getcodebutton.start(phone, "1");
    }

    private boolean isEyeOpen = false;

    @OnClick(R.id.showEye)
    public void showEye() {
        if (!isEyeOpen) {
            isEyeOpen = true;
            showEye.setImageResource(R.drawable.icon_eye_open);
            //如果选中，显示密码
            newPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isEyeOpen = false;
            showEye.setImageResource(R.drawable.icon_eye_close);
            //否则隐藏密码
            newPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

}
