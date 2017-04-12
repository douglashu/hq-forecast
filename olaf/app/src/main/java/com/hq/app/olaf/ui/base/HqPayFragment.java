package com.hq.app.olaf.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.hq.app.olaf.R;
import com.hq.component.base.BaseFragment;
import com.hq.component.network.service.ThreadPoolTool;

/**
 * Created by huwentao on 16/6/14.
 */
public abstract class HqPayFragment extends BaseFragment {
    protected void showSnackBar(String content) {
        HqPayActivity anyPayActivity = (HqPayActivity) getActivity();
        anyPayActivity.showSnackBar(content);
    }

    protected void showSnackBar(View view, String content) {
        HqPayActivity anyPayActivity = (HqPayActivity) getActivity();
        anyPayActivity.showSnackBar(view, content);
    }

    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    @Override
    public void forward(Class targetClass) {
        super.forward(targetClass);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    @Override
    public void forward(Class targetClass, Bundle bundle) {
        super.forward(targetClass, bundle);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    @Override
    public void forwardForResult(Class targetClass, int requestCode) {
        super.forwardForResult(targetClass, requestCode);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    @Override
    public void forwardForResult(Class targetClass, Bundle bundle, int requestCode) {
        super.forwardForResult(targetClass, bundle, requestCode);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    /**
     * @return
     */
    protected HqPayApplication getApp() {
        return (HqPayApplication) getActivity().getApplication();
    }

    /**
     * @return
     */
    protected HqPayActivity getAppActivity() {
        return (HqPayActivity) getActivity();
    }

    @Override
    public void onDestroy() {
        ThreadPoolTool.getInstance().cancelAll();
        super.onDestroy();
    }

    public void onStorageGranted() {

    }

    public void onStorageDenied() {

    }

    public void onCamraGranted() {

    }

    public void onCamraDenied() {

    }

    public void onCallGranted() {

    }

    public void onCallDenied() {

    }

    public void onLocationGranted() {

    }

    public void onLocationDenied() {

    }
}
