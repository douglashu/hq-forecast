package com.hq.app.olaf.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.app.olaf.BuildConfig;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.DownLoadView;
import com.hq.app.olaf.net.DownloadTask;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.bean.setting.AppVersion;
import com.hq.app.olaf.ui.bean.setting.CheckVer;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.app.olaf.ui.widget.MenuItemView;
import com.hq.app.olaf.util.HomeVersionDownLoad;
import com.hq.app.olaf.util.SharedPrefUtil;
import com.hq.component.annotation.Layout;
import com.hq.component.base.AppActManager;
import com.hq.component.base.BaseActivity;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.JSON;
import com.hq.component.utils.TextHelper;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 关于
 */
@Layout(layoutId = R.layout.activity_about)
public class AboutActivity extends HqPayActivity {

    @Bind(R.id.help) MenuItemView help;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.versionCode) TextView versionCode;
//    HttpTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        help.addParams("url", HttpService.getInstance().getHelperUrl());
        initToolbar(toolbar);
        TextHelper.setText(versionCode, "当前版本号：%s", getVerName());

    }

    @OnClick({R.id.checkVersion})
    public void checkVersion() {
//        task = HttpService.getInstance().CheckVer(getVerName());
        new HomeVersionDownLoad(this).initStart(false);
    }

    CheckVer checkVer;
    AppVersion verContent;

//    @OnClick(R.id.logout)
    public void logout() {
        showTipsDialog("确定退出当前登录?", "确定", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                HttpTask logout = HttpService.getInstance().deleteSessions();
                NetTools.excute(logout, new LoadingDialog(AboutActivity.this), new NetTools.CallBack() {
                    @Override
                    public void onComplete(TaskResult taskResult) {
                        Session.clear();
                        ThreadPoolTool.getInstance().cancelAll();
                        showSnackBar("操作员已退出");
                        AppActManager.getInstance().exit();
                        forward(LoginActivity.class);
                    }
                });
            }
        });
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public int getVerCode() {
        int verCode = -1;
        try {

            verCode = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 安装
     */
    private void installApk(File file) {

        if (!file.exists()) {
            Log.i("DownLoadReceive", "文件不存在");
            return;
        }
        // 通过Intent安装apk文件，自动打开安装界面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        this.startActivity(intent);
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVerName() {
        String verName = "";
        try {
            verName = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }

    String orderInfo = "app_id=2016110202475821&biz_content=%7B%22out_trade_no%22%3A%221478151806625%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22iPhone+6S%22%2C%22timeout_express%22%3A%2290m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22body%22%3A%22%5B%7B%5C%22body%5C%22%3A%5C%22iPhone+6S+32G+%E9%BB%91%E8%89%B2%5C%22%2C%5C%22category%5C%22%3A%5C%22%E7%94%B5%E5%AD%90%E4%BA%A7%E5%93%81%5C%22%2C%5C%22id%5C%22%3A%5C%221478151806625%5C%22%2C%5C%22price%5C%22%3A1%2C%5C%22quantity%5C%22%3A1%2C%5C%22title%5C%22%3A%5C%22iPhone+6S%5C%22%7D%5D%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwww.baidu.com&sign_type=RSA&timestamp=2016-11-03+13%3A43%3A26&verson=1.0&sign=W30ZayNkiIUX7h56SiMsMoxkgl29oI32O3FwA/5jgFGEy6nr8HKI8a90ahAnbvbpNRiTHXHuc9ieetHCX7ZSW1Su1kfvkATYlRnDSPyU6fsrk5L1jocJsid5SD8Jr27V6eN2olYUN1KpW3ny6Plym8nS/Q7fxkZ/Au+Gq/pbVKI=";


    private AliPayHandler mHandler;

    /*支付宝支付结果码*/
    private static final String PAY_OK = "9000";// 支付成功
    private static final String PAY_WAIT_CONFIRM = "8000";// 交易待确认
    private static final String PAY_NET_ERR = "6002";// 网络出错
    private static final String PAY_CANCLE = "6001";// 交易取消
    private static final String PAY_FAILED = "4000";// 交易失败
    private static final int PAY_RESULT = 2000;// 交易失败

    /*内部类，处理支付宝支付结果*/
    static class AliPayHandler extends Handler {
        private SoftReference<BaseActivity> activitySoftReference;// 使用软引用防止内存泄漏

        public AliPayHandler(BaseActivity activity) {
            activitySoftReference = new SoftReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = activitySoftReference.get();

            String payResult = (String) msg.obj;
            Log.d("alipay statusCode", "statusCode = " + payResult);

        }
    }



}
