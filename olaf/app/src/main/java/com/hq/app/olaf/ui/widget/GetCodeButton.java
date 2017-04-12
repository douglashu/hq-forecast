package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;

import com.hq.app.olaf.R;


/**
 * Created by liaob on 2016/4/21.
 */
public class GetCodeButton extends RelativeLayout {
    private ProgressBar progressBar;
    private TextView textView;
    private String getcode = "获取验证码";
    private String retryGetCode = "重新获取验证码";
    private String waitSeconds = "请等待";
    private TimeCount time;
    private String mobile, type;//手机号
    private RelativeLayout layout;
    public final static int COMMONT_TYPE = 0;
    public final static int FORGOT_PAY_TYPE = 1;
    public final static int CREATE_ACCOUNT = 2;
    public static final int FINANCE_PAY_TYPE = 3;

    public GetCodeButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_get_code, this);
        layout = (RelativeLayout) findViewById(R.id.layout);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(GONE);
        textView = (TextView) findViewById(R.id.text_code);
        textView.setText(getcode);
        time = new TimeCount(60000, 1000);
    }

    public void start(String mobile, String type) {
        start(mobile, type, COMMONT_TYPE);
    }

    public void start(String mobile, String type, int codeType) {

        this.mobile = mobile;
        this.type = type;
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            Snackbar.make(this, "请输入手机号", Snackbar.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(VISIBLE);
        textView.setText(waitSeconds);
        this.setClickable(false);
        this.setEnabled(false);
//        this.setAlpha(0.33f);
        if (codeType == COMMONT_TYPE) {
            getCode();
        }
    }

    public void getCode() {
        NetTools.excute(HttpService.getInstance().getPasscode(mobile, type), null, new NetTools.CallBack() {
            @Override
            public void onComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    startCode();
                    Snackbar.make(GetCodeButton.this,"短信验证码发送成功", Snackbar.LENGTH_SHORT).show();
                    return;
                } else {
                    RequestFailedHandler.handleFailed(GetCodeButton.this, taskResult);
                }
                complete();
            }
        });
    }


    public void startCode() {
//        this.setAlpha(1f);
        this.setEnabled(false);
        textView.setText("倒计时");
        time.start();

    }

    public void complete() {
        progressBar.setVisibility(GONE);
        this.setClickable(true);
        this.setEnabled(true);
        textView.setText(retryGetCode);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            complete();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程

            textView.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        layout.setEnabled(enabled);
    }
}
