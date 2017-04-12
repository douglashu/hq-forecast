package com.hq.app.olaf.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.DownLoadView;
import com.hq.app.olaf.net.DownloadTask;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.ui.activity.LoadingActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.setting.AppVersion;
import com.hq.app.olaf.ui.bean.setting.CheckVer;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.component.date.DateTime;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jyl on 2017/2/25.
 */

public class HomeVersionDownLoad {

    private HttpTask checkVersion;
    private CheckVer checkVer;
    private AppVersion verContent;

    private HqPayActivity activity;

    public  HomeVersionDownLoad(HqPayActivity activity){
        this.activity=activity;
    }


    /**
     * 检查版本,预加载数据
     */
    public void initStart(final boolean isAuto) {
        try {
            ThreadPoolTool.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    checkVersion = HttpService.getInstance().CheckVer(getVerName());

                    NetTools.excute(checkVersion, null, new NetTools.CallBack() {
                        @Override
                        public void onComplete(TaskResult taskResult) {
                            if (taskResult.isSuccess()) {
                                //checkVersion != null && checkVersion.getTAG().equals(taskResult.getTag())
                                String jsonResult = taskResult.getResult().toString();
                                checkVer = JSON.parseObject(jsonResult, CheckVer.class);
                                //判断有无更新———checkVer.isFindNew()
                                if (checkVer.isNewVersionFound()) {
                                    verContent = checkVer.getAppVersion();
                                    if (verContent.isForce()) {
                                        // 强制更新
                                        new InfoDialog(activity, true, "发现新版本", checkVer.getAppVersion().getWhatsNew())
                                                .setContentColor(R.color.black)
                                                .setCancel("退出", new InfoDialog.OnClickListener() {
                                                    @Override
                                                    public void onClick(InfoDialog infoDialog, View button) {
                                                        activity.getApp().exitApp();
                                                    }
                                                }).setConfirm("下载更新", downloadUpdate)
                                                .setDialogCancelable(false)
                                                .show();
                                        //保存更新为1  强制更新
                                        SharedPrefUtil.getInstance().save("checkDownVersionToday", "1");
                                        return;
                                    } else {
                                        // 非强制更新
                                        new InfoDialog(activity, true, "发现新版本", checkVer.getAppVersion().getWhatsNew())
                                                .setContentColor(R.color.black)
                                                .setCancel("取消", new InfoDialog.OnClickListener() {
                                                    @Override
                                                    public void onClick(InfoDialog infoDialog, View button) {
                                                        infoDialog.dismiss();
                                                        //queryMerchantInfo();
                                                    }
                                                }).setConfirm("下载更新", downloadUpdate)
                                                .setDialogCancelable(true)
                                                .show();
                                    }
                                } else {
                                    if(!isAuto) {
                                        new InfoDialog(activity, "提示", "当前已经是最新版")
                                                .setContentColor(R.color.black)
                                                .setConfirm("确定", new InfoDialog.OnClickListener() {
                                                    @Override
                                                    public void onClick(InfoDialog infoDialog, View button) {

                                                    }
                                                }).show();
                                    }
                                    //queryMerchantInfo();
                                }
                            } else {
//                    RequestFailedHandler.handleFailed(getRootView(), taskResult);
                                //queryMerchantInfo();
                            }
                        }
                    });
                }
            });
            //保存更新日期为今天
            SharedPrefUtil.getInstance().save("checkDownVersionToday", DateTime.now(TimeZone.getDefault()).format("YYYYMMDDhh"));
        } catch (Exception e) {
            Logger.e(e, e.getMessage());
        }
    }

    /**
     * 下载更新
     */
    private InfoDialog.OnClickListener downloadUpdate = new InfoDialog.OnClickListener() {
        String fileName = "";

        @Override
        public void onClick(InfoDialog infoDialog, View button) {
            String url = verContent.getUrl();
//            String url = "http://onmu3dl36.bkt.clouddn.com/app-debug.apk?attname=&e=1490888851&token=RmUi4UlfPOTIpTORNkyUe4uqMj99LFUfP7qt_QJF:AdUizUDg46Vp5YjLY3BaxVtLvbg";
            final HttpTask download = HttpService.getInstance().download("hq/", url, fileName);
            DownLoadView progressDownLoadView = new DownLoadView(activity, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    ThreadPoolTool.getInstance().cancel(download);
                    File file = ((DownloadTask) download).getFile();
                    if (file != null) {
                        file.deleteOnExit();
                    }
                    activity.showSnackBar("取消下载");
                    //取消下载 加载数据
                    //queryMerchantInfo();
                }
            });
            progressDownLoadView.setCancelable(false);
            NetTools.excute(download, progressDownLoadView, new NetTools.CallBack() {

                @Override
                public void onComplete(TaskResult taskResult) {
                    if (taskResult.isSuccess()) {
                        installApk((File) taskResult.getResult());
                        //判断是否强制直接退出应用程序
                        if (verContent.isForce()) {
                            activity.getApp().exitApp();
                        }
                    } else {
                        RequestFailedHandler.handleFailed(activity.getRootView(), taskResult);
                    }
                }
            });
        }
    };

    private void installApk(File file) {
        if (!file.exists()) {
            Log.i("DownLoadReceive", "文件不存在");
            return;
        }
        // 通过Intent安装apk文件，自动打开安装界面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }


    public int getVerCode() {
        int verCode = -1;
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVerName() {
        String verName = "";
        try {
            verName = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }
}
