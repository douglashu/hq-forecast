package com.hq.app.olaf.net;

import android.content.Context;
import android.util.AttributeSet;

import com.hq.component.network.service.CancelListener;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;
import com.github.rahatarmanahmed.cpv.CircularProgressView;


/**
 * Created by huwentao on 16-4-22.
 */
public class ViewProgress extends CircularProgressView implements Progress {
    private boolean isLoading = true;

    public ViewProgress(Context context) {
        super(context);
    }

    public ViewProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onProgress(int progress, boolean done, boolean isUp) {
        setProgress(progress);
    }

    @Override
    public void showProgress() {
        setVisibility(VISIBLE);
        setProgress(0);
    }

    @Override
    public void hideProgress() {
        setProgress(0);
        if (isLoading) {
            setVisibility(GONE);
        }
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

    }

    @Override
    public boolean isSetRetry() {
        return false;
    }

    @Override
    public boolean isSetCancel() {
        return false;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
