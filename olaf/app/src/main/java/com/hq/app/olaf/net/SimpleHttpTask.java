package com.hq.app.olaf.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.NetworkUtils;
import com.hq.app.olaf.util.StringUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by mokey on 2016/4/20.
 * 智能POS 请求头新增参数：  APP_TYPE=SMART_POS
 * 商户APP请求头新增参数：   APP_TYPE=MERCHANT_APP
 * 商户门户请求头新增参数：   APP_TYPE=MERCHANT_PORTAL
 */
public abstract class SimpleHttpTask extends HttpTask {
    public SimpleHttpTask() {
    }

    @Override
    public boolean before() {
        if (!NetworkUtils.isConnected(HqPayApplication.getAppContext())) {
            haveNoNet();
            return false;
        }
        Session session = Session.load();
        if (session != null) {
            getHttpRequest().setHeader("AID", HttpUtils.appId);
            getHttpRequest().setHeader("TOKEN", session.getAuthToken());
            getHttpRequest().setHeader("UID", session.getUserId());
        }
        //getHttpRequest().setHeader("APP_TYPE", "MERCHANT_APP");
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
                }else if(bodyStr.startsWith("[") && bodyStr.endsWith("]")){
                    Logger.d(bodyStr);
                    taskResult.setResult(bodyStr);
                } else {
                    Logger.json(bodyStr);

                    try {
                        //openapi接口返回
                        HttpMessage message = JSON.parseObject(bodyStr, HttpMessage.class);
                        if ("0000".equals(message.getKey())||"000000".equals(message.getKey())) {
                            taskResult.setStatus(TaskStatus.success);
                            if (message.getExt() != null) {
                                String ext = message.getExt().toString();
                                if (isEmpty(ext)) {
                                    setDataEmpty();
                                } else {
                                    taskResult.setResult(ext);
                                }
                            } else {

                                taskResult.setResult(message.getMsg());
                            }
                        }else if(StringUtil.isEmpty(message.getKey())){
                            //无法格式化,交给内部程序解析
                            taskResult.setResult(bodyStr);
                        } else if ("401".equals(message.getKey())) {
                            unauthorized();
                            taskResult.setError(message.getMsg());
                        } else {
                            failure();
                            taskResult.setError(message.getMsg());
                        }
                    }catch (JSONException e){
                        Logger.d("成功返回解析json出错："+e.getMessage(), e);
                    }

                }
            } catch (Exception e) {
                failure();
            }
        }else if(status.isUnauthorized()){
            try {
                String bodyStr = response.body().string();
                if (!TextUtils.isEmpty(bodyStr)) {
                    com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(bodyStr);
                    String key=jsonObject.getString("key");
                    if(key.equals("400300")) {
                        HttpMessage message = JSON.parseObject(bodyStr, HttpMessage.class);
                        taskResult.setCode(TaskStatus.Unauthorized.getCode());
                        if (!StringUtil.isEmpty(message.getMsg())) {
                            taskResult.setError(message.getMsg());
                        }
                    }
                }
            } catch (Exception e) {
                Logger.d("失败返回解析json出错："+e.getMessage(), e);
                unauthorized();
            }
        }else if (taskResult.getStatus() != TaskStatus.success) {
            try {
                String bodyStr = response.body().string();
                if (!TextUtils.isEmpty(bodyStr)) {
                    com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(bodyStr);
                    HttpMessage message = JSON.parseObject(bodyStr, HttpMessage.class);
                    taskResult.setCode(TaskStatus.Failure.getCode());
                    if (!StringUtil.isEmpty(message.getMsg())) {
                        taskResult.setError(message.getMsg());
                    }
                }
            } catch (Exception e) {
                Logger.d("失败返回解析json出错："+e.getMessage(), e);
                failure();
            }
        }
    }

    private boolean isJsonString(String jsonStr) {
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                new JSONObject(jsonStr);
                return true;
            }
        } catch (JSONException e) {
        }
        return false;
    }

    /**
     * 判断响应结果是否为空数据
     *
     * @param body
     * @return
     * @throws JSONException
     */
    private boolean isEmpty(String body) throws JSONException {
        if (TextUtils.isEmpty(body)) {
            return true;
        }
        return body.length() == 2
                || "null".equals(body)
                || "NULL".equals(body)
                || "Null".equals(body)
                || "[]".equals(body) ||
                body.contains("empty");
    }


}
