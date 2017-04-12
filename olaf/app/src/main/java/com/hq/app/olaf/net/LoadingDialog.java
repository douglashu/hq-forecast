package com.hq.app.olaf.net;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

import com.hq.component.network.service.CancelListener;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;
import com.hq.app.olaf.R;


/**
 * Created by huwentao on 16-4-25.
 */
public class LoadingDialog extends MaterialDialog implements Progress {
    private CancelListener cancelListener;

    protected LoadingDialog(Builder builder) {
        super(builder);
    }

    public LoadingDialog(Context context) {
        super(new Builder(context)
                .customView(R.layout.layout_loading_progress, true)
        );
        setOnCancelListener(new OnCancelListener(){
            @Override
            public void onCancel(DialogInterface dialog) {
                if (cancelListener != null)
                    cancelListener.cancel();
            }
        });
    }

    @Override
    public void onProgress(int progress, boolean done, boolean isUp) {

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
    public void onError(String TAG, TaskResult error) {

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
