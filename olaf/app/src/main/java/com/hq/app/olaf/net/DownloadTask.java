package com.hq.app.olaf.net;

import android.os.Environment;
import android.text.TextUtils;

import com.hq.component.network.exception.NetError;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.impl.HttpTask;

import java.io.File;

import okhttp3.Headers;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by huwentao on 16-4-22.
 */
public abstract class DownloadTask extends HttpTask {
    private String savePath = null;
    private String fileName = null;
    File file = null;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {

        this.file = file;
    }

    public DownloadTask(String savePath, String fileName) {
        this.savePath = savePath;
        this.fileName = fileName;
    }

    @Override
    public boolean before() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File sdCardPath = Environment.getExternalStorageDirectory();
            File saveDrectory = new File(sdCardPath, savePath);
            if (!saveDrectory.exists()) {
                saveDrectory.mkdirs();
            }
            savePath = saveDrectory.getAbsolutePath();
        }
        return true;
    }

    @Override
    public void after() {
        TaskResult taskResult = getResult();
        if (taskResult.getStatus() != TaskStatus.Failure) {
            Response response = taskResult.getResponse();
            Headers headers = response.headers();
            String disposition = headers.get("Content-disposition");
            if (TextUtils.isEmpty(fileName)) {
                fileName = disposition.substring(disposition.indexOf("=") + 1, disposition.length());
            }
            fileName = fileName.replaceAll("\"", "").trim();
            BufferedSource source = taskResult.getResponse().body().source();
            try {
                file = new File(savePath, fileName);
                if (!file.exists()) file.createNewFile();
                BufferedSink sink = Okio.buffer(Okio.sink(file));
                sink.writeAll(source);
                sink.flush();
                sink.close();
                taskResult.setStatus(TaskStatus.success);
                taskResult.setResult(file);
            } catch (Exception e) {
                if (!taskResult.isCancel()) {
                    taskResult.setNetError(new NetError(e));
                    taskResult.setStatus(TaskStatus.Failure);
                } else {
                    taskResult.setError("已取消下载");
                }
            }
        }
    }
}
