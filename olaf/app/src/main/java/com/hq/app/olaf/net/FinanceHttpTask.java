package com.hq.app.olaf.net;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.NetworkUtils;
import com.hq.uicomponet.utils.SnackbarTool;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by mokey on 2016/4/20.
 * 智能POS 请求头新增参数：  APP_TYPE=SMART_POS
 * 商户APP请求头新增参数：   APP_TYPE=MERCHANT_APP
 * 商户门户请求头新增参数：   APP_TYPE=MERCHANT_PORTAL
 */
public abstract class FinanceHttpTask extends HttpTask {
    public FinanceHttpTask() {
    }

    @Override
    public boolean before() {
//        getHttpRequest().addHeader("APP_TYPE", "SMART_POS");
        if (!NetworkUtils.isConnected(HqPayApplication.getAppContext())) {
            haveNoNet();
            return false;
        }
        return true;
    }

    @Override
    public void after() {
        TaskResult taskResult = getResult();
        Response response = taskResult.getResponse();
        TaskStatus status = taskResult.getStatus();
        Logger.d("请求状态：status(%s)", taskResult.getStatus());
        if (status.isSuccess()) {
            try {
                String bodyStr = response.body().string();
                if (TextUtils.isEmpty(bodyStr)) {
                    failure();
                } else {
                    JSONObject jsonObject = JSON.parseObject(bodyStr);
                    String key = jsonObject.getString("key");
                    String msg = jsonObject.getString("msg");
                    String extResult = jsonObject.getString("ext");
                    if (!"0000".equals(key)) {
                        if (!TextUtils.isEmpty(msg) && msg.contains("timeout")) {
                            timeout();
                        } else {
                            failure();
                            taskResult.setError(msg);
                        }
                    } else {
                        if (!TextUtils.isEmpty(extResult) && extResult.length() > 2) {
                            extResult = extResult.substring(0, extResult.length());
                            extResult = extResult.replace("\"{", "{").replace("}\"", "}");
                            bodyStr = extResult.replace("\\\"", "\"");
                            if (com.hq.component.utils.JSON.isJsonString(bodyStr)) {
                                Logger.json(bodyStr);
                            }
                            if ("0000".equals(key)) {
                                taskResult.setStatus(TaskStatus.success);
                                JSONObject jsonResult = JSON.parseObject(bodyStr);
                                if (jsonResult != null) {
                                    JSONObject result = jsonResult.getJSONObject("result");
                                    String retCode = result.getString("retCode");
                                    String retMsg = result.getString("retMsg");
                                    if ("0000".equals(retCode)) {
                                        taskResult.setResult(result);
                                    } else {
                                        failure();
                                        taskResult.setError(retMsg);
                                        taskResult.setResult(result);
                                    }
                                } else {
                                    failure();
                                }
                            } else {
                                if (!taskResult.isEmptyData()) {
                                    failure();
                                    taskResult.setError(msg);
                                } else {
                                    setDataEmpty();
                                }
                            }
                        } else {
                            setDataEmpty();
                        }
                    }
                }
            } catch (IOException e) {
                failure();
            }
        }
        if (status != TaskStatus.success) {
            Logger.d("请求异常: NetError==>%s\n Reqeust Error==>%s",
                    taskResult.getNetError(),
                    taskResult.getError());
        }
    }


    private static void showSnackBar(View view, String content) {
        if (view != null) {
            SnackbarTool.show(view, content);
        }
    }

    /**
     * 判断响应结果是否为空数据
     *
     * @param body
     * @return
     */
    private boolean isEmpty(String body) {
        if (TextUtils.isEmpty(body)) {
            return true;
        }
        return body.length() == 2
                || "null".equals(body)
                || "NULL".equals(body)
                || "Null".equals(body)
                || "[]".equals(body);
    }

}
