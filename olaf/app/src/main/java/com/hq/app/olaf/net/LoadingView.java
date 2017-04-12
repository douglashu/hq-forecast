package com.hq.app.olaf.net;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hq.component.network.service.CancelListener;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.utils.TextHelper;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16-5-7.
 */
public class LoadingView extends LinearLayout implements Progress {
    @Bind(R.id.error) LinearLayout errorLayout;
    @Bind(R.id.nodata) LinearLayout nodataLayout;
    @Bind(R.id.loading) LinearLayout loadLayout;
    @Bind(R.id.errorText) TextView errorText;
    @Bind(R.id.nodataText) TextView nodataText;
    @Bind(R.id.loadText) TextView loadText;

    private RetryListener retryListener;
    private String emptyText = "空数据";
    private boolean isRetryable = false;


    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_loading, this);
        ButterKnife.bind(view, this);
    }

    @Override
    public void onProgress(int progress, boolean done, boolean isUp) {

    }

    @Override
    public void showProgress() {
        setVisibility(VISIBLE);
        loadLayout.setVisibility(VISIBLE);
        errorLayout.setVisibility(GONE);
        nodataLayout.setVisibility(GONE);
    }

    @Override
    public void hideProgress() {
        setVisibility(GONE);
        loadLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
        nodataLayout.setVisibility(GONE);
    }

    @Override
    public void onError(String TAG, TaskResult error) {
        setVisibility(VISIBLE);
        loadLayout.setVisibility(GONE);
        nodataLayout.setVisibility(GONE);
        TaskStatus status = error.getStatus();
        if (status.isUnauthorized()) {
            errorLayout.setVisibility(GONE);
            //TODO 清除登录信息，跳转到登录页面
        } else {
            errorLayout.setVisibility(VISIBLE);
            TextHelper.setText(errorText, error.getError());
            if (retryListener != null) {
                errorLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retryListener.retry();
                    }
                });
            }
        }
    }

    @Override
    public void noData() {
        setVisibility(VISIBLE);
        loadLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
        nodataLayout.setVisibility(VISIBLE);
        TextHelper.setText(nodataText, emptyText);
        if (retryListener != null) {
            nodataLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    retryListener.retry();
                }
            });
        }
    }

    @Override
    public void setRetryListener(RetryListener retryListener) {
        this.retryListener = retryListener;
        if (retryListener != null)
            isRetryable = true;
    }

    @Override
    public void setCancelListener(CancelListener cancelListener) {

    }

    @Override
    public boolean isSetRetry() {
        return isRetryable;
    }

    @Override
    public boolean isSetCancel() {
        return false;
    }

    public boolean isShow() {
        return getVisibility() == VISIBLE && (loadLayout.getVisibility() == VISIBLE
                || errorLayout.getVisibility() == VISIBLE
                || nodataLayout.getVisibility() == VISIBLE);
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }
}
