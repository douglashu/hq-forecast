package com.hq.app.olaf.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.app.olaf.ui.activity.LoginActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.component.base.AppActManager;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.uicomponet.utils.SnackbarTool;
import com.orhanobut.logger.Logger;

/**
 * Created by huwentao on 16/9/24.
 */

public class RequestFailedHandler {

    /**
     * APP错误消息处理
     *
     * @param mRootView
     * @param taskResult
     */
    public static String handleFailedMsg(final View mRootView, TaskResult taskResult) {
        String message="服务器异常，请稍后重试";
        TaskStatus status = taskResult.getStatus();
        switch (status) {
            case Failure:
                if (!TextUtils.isEmpty(taskResult.getError())) {
                    message =taskResult.getError();
                } else {
                    Logger.e((String) taskResult.getResult());
                    message = "网络加载失败，请稍后重试";
                }
                break;
            case Unauthorized:
                Session.clear();
                Logger.e((String) taskResult.getResult());
                if (TextUtils.isEmpty(taskResult.getError())) {
                    taskResult.setError("登录失效，请重新登录！");
                }
                new MaterialDialog.Builder(mRootView.getContext())
                        .title("提示")
                        .content(taskResult.getError())
                        .positiveText("重新登录")
                        .cancelable(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                ThreadPoolTool.getInstance().cancelAll();
                                AppActManager.getInstance().exit();
                                Intent intent = new Intent(mRootView.getContext(), LoginActivity.class);
                                mRootView.getContext().startActivity(intent);
                            }
                        }).show();
                break;
            case Forbidden:
                if (!TextUtils.isEmpty(taskResult.getError())) {
                    showSnackBar(mRootView, taskResult.getError());
                } else {
                    Logger.e((String) taskResult.getResult());
                    message = "网络加载失败，请稍后重试";
                }
                break;
            case ServerError:
                if (!TextUtils.isEmpty(taskResult.getError())) {
                    message =taskResult.getError();
                } else {
                    Logger.e((String) taskResult.getResult());
                    message ="服务器异常，请稍后重试";
                }
                break;
            case TimeOut:
                message ="网络连接超时，请稍后重试";
                break;
            case DataEmpty:
//                showSnackBar(mRootView, taskResult.getError());
                break;
            case noNet:
                message = "当前没有可用的网络";
                break;
        }
        return message;
    }

    /**
     * APP错误消息处理
     *
     * @param mRootView
     * @param taskResult
     */
    public static boolean handleFailed(final View mRootView, TaskResult taskResult) {
        boolean hasError = true;
        TaskStatus status = taskResult.getStatus();
        switch (status) {
            case Failure:
                if (!TextUtils.isEmpty(taskResult.getError())) {
                    showSnackBar(mRootView, taskResult.getError());
                } else {
                    Logger.e((String) taskResult.getResult());
                    showSnackBar(mRootView, "网络加载失败，请稍后重试");
                }
                break;
            case Unauthorized:
                Session.clear();
                Logger.e((String) taskResult.getResult());
                if (TextUtils.isEmpty(taskResult.getError())) {
                    taskResult.setError("登录失效，请重新登录！");
                }
                InfoDialog infodialog = new InfoDialog(mRootView.getContext(), "账号警告", taskResult.getError())
                        .setConfirm("重新登录", new InfoDialog.OnClickListener() {
                            @Override
                            public void onClick(InfoDialog dialog, View button) {
                                dialog.dismiss();
                                ThreadPoolTool.getInstance().cancelAll();
                                AppActManager.getInstance().exit();
                                Intent intent = new Intent(mRootView.getContext(), LoginActivity.class);
                                mRootView.getContext().startActivity(intent);
                            }
                        });
                infodialog.setCancelable(false);
                infodialog.show();
                break;
            case Forbidden:
                if (!TextUtils.isEmpty(taskResult.getError())) {
                    showSnackBar(mRootView, taskResult.getError());
                } else {
                    Logger.e((String) taskResult.getResult());
                    showSnackBar(mRootView, "网络加载失败，请稍后重试");
                }
                break;
            case ServerError:
                if (!TextUtils.isEmpty(taskResult.getError())) {
                    showSnackBar(mRootView, taskResult.getError());
                } else {
                    Logger.e((String) taskResult.getResult());
                    showSnackBar(mRootView, "服务器异常，请稍后重试");
                }
                break;
            case TimeOut:
                showSnackBar(mRootView, "网络连接超时，请稍后重试");
                break;
            case noNet:
                showSnackBar(mRootView, "当前没有可用的网络");
                break;
            default:
                hasError = false;
                break;
        }
        return hasError;
    }

    private static void showSnackBar(View mRootView, String content) {
        if (mRootView != null) {
            SnackbarTool.show(mRootView, content);
        }
    }
}
