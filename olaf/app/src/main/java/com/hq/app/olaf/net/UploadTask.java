package com.hq.app.olaf.net;

import android.content.Context;
import android.text.TextUtils;

import com.hq.component.network.exception.NetError;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.JSON;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;

/**
 * Created by huwentao on 16-4-25.
 */
public abstract class UploadTask extends HttpTask {
    private Context context;
    private File uploadFile = null;

    public UploadTask(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public UploadTask(Context context) {
        this.context = context;
    }

    @Override
    public boolean before() {
        if (!uploadFile.exists()) {
            getResult().setNetError(new NetError("待上传文件未找到或不存在"));
        }
        return uploadFile.exists();
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void after() {
        TaskResult taskResult = getResult();
        Response response = taskResult.getResponse();
        TaskStatus status = taskResult.getStatus();
        if (status.isSuccess()) {
            try {
                String bodyStr = response.body().string();
                if (isEmpty(bodyStr)) {
                    setDataEmpty();
                } else {
                    taskResult.setResult(bodyStr);
                }
            } catch (IOException e) {
                failure();
            } catch (JSONException e) {
                Logger.d(e.getMessage(), e);
            }
        } else if (status.isFailure()) {
            if (response != null) {
                try {
                    String message = response.body().string();
                    taskResult.setResult(message);
                    if (!TextUtils.isEmpty(message) && JSON.isJsonString(message)) {
                        JSONObject object = new JSONObject(message);
                        message = object.getString("msg");
                        taskResult.setError(message);
                    }
                } catch (IOException e) {
                    Logger.e(e, e.getMessage());
                    failure();
                } catch (JSONException e) {
                    Logger.e(e, e.getMessage());
                }
            }
        }
        String result = (String) taskResult.getResult();
        if (!TextUtils.isEmpty(result) && JSON.isJsonString(result)) {
            Logger.d("请求状态：status(%s)", taskResult.getStatus());
            Logger.json((String) taskResult.getResult());
        } else {
            Logger.d("请求状态：status(%s)\t请求结果:(%s)", taskResult.getStatus(), taskResult.getNetError());
        }
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
                || "Null".equals(body);
    }
}
