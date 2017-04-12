package com.hq.app.olaf.net;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.component.network.service.CancelListener;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class uploadView extends MaterialDialog implements Progress {
    private CancelListener cancelListener;

    protected uploadView(Builder builder) {
        super(builder);
    }

    public uploadView(Context context) {
        super(new Builder(context)
                .content("正在上传")//标题
                .progress(false, 100)//水平，最大，左边显示
                .cancelable(true)
                .cancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        uploadView uploadView = (uploadView) dialog;
                        if (uploadView.cancelListener != null)
                            uploadView.cancelListener.cancel();
                    }
                })
        );

    }


    @Override
    public void onProgress(int progress, boolean done, boolean isUp) {
        if (progress < 100) {
            setProgress(progress);
            mProgressLabel.setText(progress + "%");
        } else {
            setProgress(100);
            mProgressLabel.setText(100 + "%");
        }
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