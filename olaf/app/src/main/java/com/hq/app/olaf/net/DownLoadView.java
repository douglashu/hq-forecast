package com.hq.app.olaf.net;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.component.network.service.CancelListener;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class DownLoadView extends MaterialDialog implements Progress {
    private CancelListener cancelListener;

    protected DownLoadView(Builder builder) {
        super(builder);


    }

    public DownLoadView(Context context, SingleButtonCallback callback//取消按钮事件处理
                        ) {
        super(new Builder(context)
                .content("正在下载")//标题
                .progress(false, 100)//水平，最大，左边显示
                .negativeText("取消下载")
                .onNegative(callback)
        );

    }


    @Override
    public void onProgress(int i, boolean b, boolean b1) {
        setProgress(i);
        mProgressLabel.setText(i + "%");
        setCancelable(false);

    }

    @Override
    public void showProgress() {
        show();
    }

    @Override
    public void hideProgress() {
        dismiss();
    }

    @Override
    public void onError(String s, TaskResult taskResult) {

    }

    @Override
    public void noData() {

    }

    @Override
    public void setRetryListener(RetryListener retryListener) {

    }

    @Override
    public void setCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    @Override
    public boolean isSetRetry() {
        return false;
    }

    @Override
    public boolean isSetCancel() {
        return false;
    }
}