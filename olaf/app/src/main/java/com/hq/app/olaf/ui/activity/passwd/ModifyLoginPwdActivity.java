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
import com.hq.app.olaf.ui.widget.CheckTextGroup;
import com.hq.component.annotation.Layout;
import com.hq.component.base.AppActManager;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;

import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.activity_modify_login_pwd)
public class ModifyLoginPwdActivity extends HqPayActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.checkedGroup) CheckTextGroup checkedGroup;
    @Bind(R.id.originPwd) EditText originPwd;
    @Bind(R.id.newPwd) EditText newPwd;
    private boolean isEyeOpen1 = false;
    private boolean isEyeOpen2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
    }

    private boolean check() {
        String code = originPwd.getText().toString();
        String pwd = newPwd.getText().toString();

        if (TextUtils.isEmpty(code)) {
            showSnackBar("请输入原登陆密码");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showSnackBar("请输入新登录密码");
            return false;
        }
        if (newPwd.length() < 6 || AppUtil.checkAllNumPwd(pwd) || AppUtil.checkAllABCPwd(pwd)) {
            showSnackBar("密码为6-16位，字母、数字或符号组合");
            return false;
        }

        return true;
    }

    @OnClick(R.id.commit)
    public void commit() {
        if (check()) {
//            String phone = Session.load().getMobile();
            String originP = originPwd.getText().toString();
            String pwd = newPwd.getText().toString();
            HttpTask task = HttpService.getInstance().pubModfyOriginPass(originP, pwd);
            NetTools.excute(task, new LoadingDialog(this), new HqNetCallBack(getRootView()) {
                @Override
                public void doComplete(TaskResult taskResult) {
                    if (taskResult.isSuccess()) {
                        showTipsDialog("修改密码成功,请重新登录",
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

    @Bind(R.id.showEye1) ImageView showEye1;
    @Bind(R.id.showEye2) ImageView showEye2;

    @OnClick(R.id.showEye1)
    public void showEye1() {
        if (!isEyeOpen1) {
            isEyeOpen1 = true;
            showEye1.setImageResource(R.drawable.icon_eye_open);
            //如果选中，显示密码
            originPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isEyeOpen1 = false;
            showEye1.setImageResource(R.drawable.icon_eye_close);
            //否则隐藏密码
            originPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.showEye2)
    public void showEye2() {
        if (!isEyeOpen2) {
            isEyeOpen2 = true;
            showEye2.setImageResource(R.drawable.icon_eye_open);
            //如果选中，显示密码
            newPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isEyeOpen2 = false;
            showEye2.setImageResource(R.drawable.icon_eye_close);
            //否则隐藏密码
            newPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
