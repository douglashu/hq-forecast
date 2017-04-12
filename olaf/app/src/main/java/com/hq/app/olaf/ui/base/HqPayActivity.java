package com.hq.app.olaf.ui.base;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import com.hq.app.olaf.ui.activity.LoginActivity;
import com.hq.app.olaf.ui.activity.shoukuan.ShoukuanActivity;
import com.hq.component.base.BaseActivity;
import com.hq.uicomponet.utils.SnackbarTool;
import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;

import java.util.List;

import com.hq.app.olaf.R;


/**
 * Created by huwentao on 16/6/14.
 */
public class HqPayActivity extends BaseActivity {
    private Context mContext;
    private TextView actTitle;
    private View mRootView;
    private HqPayApplication mApp;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**
         * 设置返回键事件
         */
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        mContext = this;
        mApp = (HqPayApplication) getApplication();
        actTitle = (TextView) findViewById(R.id.title);
        mRootView = findViewById(R.id.rootLayout);
    }

    protected void setRootView(View view) {
        mRootView = view;
    }

    public View getRootView() {
        return mRootView;
    }

    public void showSnackBar(String content) {
        if (mRootView != null) {
            SnackbarTool.show(mRootView, content);
        }
    }

    protected void showSnackBar(View view, String content) {
        if (view != null) {
            SnackbarTool.show(view, content);
        }
    }

    public Context getAppContext() {
        return mContext;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initToolbar(Toolbar toolbar) {
        super.initToolbar(toolbar);
        setActTitle(getTitle());
        initToolbarClickListener(toolbar);
    }

    public void initToolbarWithoutListener(Toolbar toolbar){
        super.initToolbar(toolbar);
        setActTitle(getTitle());
    }
    public void setActTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) return;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            if (actTitle != null)
                actTitle.setText(title);
        }
    }

    /**
     * @param tips
     * @param callback
     */
    public void showTipsDialog(String tips, MaterialDialog.SingleButtonCallback callback) {
        showTipsDialog(tips, null, null, "确定", callback, true, null);
    }

    /**
     * @param tips
     * @param callback
     */
    public void showTipsDialog(String tips, String positiveText, MaterialDialog.SingleButtonCallback callback) {
        showTipsDialog(tips, "取消", null, positiveText, callback, true, null);
    }

    /**
     * @param tips
     * @param nCallback 取消按钮
     * @param pCallback 确定按钮
     */
    public void showTipsDialog(String tips,
                               MaterialDialog.SingleButtonCallback nCallback,
                               MaterialDialog.SingleButtonCallback pCallback) {
        showTipsDialog(tips, "取消", nCallback, "确定", pCallback, true, null);
    }

    /**
     * @param tips
     * @param negativeText     取消按钮
     * @param nCallback        取消按钮
     * @param positiveText     确定按钮
     * @param pCallback        确定按钮
     * @param isCancelable
     * @param onCancelListener
     */
    public void showTipsDialog(String tips,
                               String negativeText,
                               MaterialDialog.SingleButtonCallback nCallback,
                               String positiveText,
                               MaterialDialog.SingleButtonCallback pCallback,
                               boolean isCancelable,
                               DialogInterface.OnCancelListener onCancelListener) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content(tips)
                .negativeText(negativeText)
                .onNegative(nCallback)
                .positiveText(positiveText)
                .onPositive(pCallback)
                .cancelable(isCancelable)
                .cancelListener(onCancelListener).build().show();
    }


    private MaterialDialog materialDialog;

    protected void dismissLoadingDialog() {
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
    }
    protected void changeDialogText(String title) {
        if (materialDialog != null&&!TextUtils.isEmpty(title)) {
            materialDialog.show();
            materialDialog.setTitle(title);
        }
    }

    /**
     * 显示弹窗
     * @param title
     */
    protected void showLoadingDialog(String title) {
        try
        {
            if (materialDialog == null) {
                materialDialog = new MaterialDialog.Builder(mContext)
                        .title(title)
                        .content(R.string.please_wait)
                        .progress(true, 0)
                        .progressIndeterminateStyle(false).build();
                materialDialog.setCancelable(false);
                materialDialog.show();
            } else {
                materialDialog.show();
                materialDialog.setTitle(title);
            }
        }catch (Exception e)
        {
            Logger.wtf(e.toString());
        }


    }

    public HqPayApplication getApp() {
        return mApp;
    }

    public void setApp(HqPayApplication mApp) {
        this.mApp = mApp;
    }

    @Override
    protected void onDestroy() {
//        ThreadPoolTool.getInstance().cancelAll();
        super.onDestroy();
    }

    public final static int STORAGE_PERMISSION = 10011;

    /**
     * 检查是否需要授权
     *
     * @return true 已授权,false 未授权
     */
    public boolean requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int granded = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (granded != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                    return false;
                }
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public final static int CAMERA_PERMISSION = 10010;

    /**
     * 检查是否需要授权
     *
     * @return true 已授权,false 未授权
     */
    public boolean requestCamraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int granded = checkSelfPermission(Manifest.permission.CAMERA);
            if (granded != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    return false;
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public final static int CALL_PHONE_PERMISSION = 10012;

    /**
     * 检查是否需要授权
     *
     * @return true 已授权,false 未授权
     */
    public boolean requestCallPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int granded = checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (granded != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
                    return false;
                }
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public final static int LOCATION_PERMISSION = 10013;

    /**
     * 检查是否需要授权
     *
     * @return true 已授权,false 未授权
     */
    public boolean requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int granded = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (granded != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, LOCATION_PERMISSION);
                    return false;
                }
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, LOCATION_PERMISSION);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        switch (requestCode) {
            case STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onStorageGranted();
                } else {
                    onStorageDenied();
                }
                if (!isOpenFragmentPermissionGranted) return;
                if (fragmentList == null || fragmentList.size() == 0) return;
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof HqPayFragment) {
                        HqPayFragment payFragment = (HqPayFragment) fragment;
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            payFragment.onStorageGranted();
                        } else {
                            payFragment.onStorageDenied();
                        }
                    }
                }
                break;
            case CALL_PHONE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCallGranted();
                } else {
                    onCallDenied();
                }
                if (!isOpenFragmentPermissionGranted) return;
                if (fragmentList == null || fragmentList.size() == 0) return;
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof HqPayFragment) {
                        HqPayFragment payFragment = (HqPayFragment) fragment;
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            payFragment.onCallGranted();
                        } else {
                            payFragment.onCallDenied();
                        }
                    }
                }
                break;
            case CAMERA_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCamraGranted();
                } else {
                    onCamraDenied();
                }
                if (!isOpenFragmentPermissionGranted) return;
                if (fragmentList == null || fragmentList.size() == 0) return;
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof HqPayFragment) {
                        HqPayFragment payFragment = (HqPayFragment) fragment;
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            payFragment.onCamraGranted();
                        } else {
                            payFragment.onCamraDenied();
                        }
                    }
                }
                break;
            case LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onLocationGranted();
                } else {
                    onLocationDenied();
                }
                if (!isOpenFragmentPermissionGranted) return;
                if (fragmentList == null || fragmentList.size() == 0) return;
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof HqPayFragment) {
                        HqPayFragment payFragment = (HqPayFragment) fragment;
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            payFragment.onLocationGranted();
                        } else {
                            payFragment.onLocationDenied();
                        }
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean isOpenFragmentPermissionGranted = false;//是否打开Fragment联动获得授权响应

    /**
     * 是否打开Fragment联动获得授权响应
     *
     * @param isOpen
     */
    public void setOpenFragmentPermissionGranted(boolean isOpen) {
        this.isOpenFragmentPermissionGranted = isOpen;
    }

    /**
     * Fragment联动获得授权响应状态
     *
     * @return
     */
    public boolean isOpenFragmentPermissionGranted() {
        return isOpenFragmentPermissionGranted;
    }

    public void onLocationDenied() {

    }

    public void onLocationGranted() {

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

    @Override
    public void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    public void forward(Class targetClass) {
        super.forward(targetClass);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    @Override
    public void forward(Class targetClass, Bundle bundle) {
        super.forward(targetClass, bundle);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    @Override
    public void forwardForResult(Class targetClass, int requestCode) {
        super.forwardForResult(targetClass, requestCode);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    @Override
    public void forwardForResult(Class targetClass, Bundle bundle, int requestCode) {
        super.forwardForResult(targetClass, bundle, requestCode);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }


    protected void returnToLogin(){
        forward(LoginActivity.class);
        finish();
    }
    protected void returnToHome() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(home);
    }

    private void initToolbarClickListener(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
            }
        });
    }
}
