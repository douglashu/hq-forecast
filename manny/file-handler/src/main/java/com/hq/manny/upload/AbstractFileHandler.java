package com.hq.manny.upload;

import com.alibaba.fastjson.JSON;
import com.hq.scrati.framework.FrameworkInvoker;
import com.hq.manny.upload.annotation.UploadHandler;
import com.hq.manny.upload.http.OKHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zale on 2016/11/22.
 */
public abstract class AbstractFileHandler implements FileHandler {
    @Value("${file.remote.location}")
    private String filepath;
    @Value("${dubbo.port}")
    private String port;
    @Autowired
    private FrameworkInvoker frameworkInvoker;
    protected byte[] getFile(String fileName) throws IOException {
        return OKHttpClient.getFile(filepath+fileName);
    }

    protected void init() throws IOException {
        UploadHandler uh =  this.getClass().getAnnotation(UploadHandler.class);
        Map<String,String> params = new HashMap<>();
        params.put("code",uh.code());
        params.put("version",uh.version());
        params.put("type",uh.type());
        params.put("port",port);
        String rstStr = OKHttpClient.postJSON("http://localhost:8080/upload/handler/register",params);
        Map<String, String> map = JSON.parseObject(rstStr, HashMap.class);
        if(!map.get("code").equals("0000")){
            throw new RuntimeException(map.get("message"));
        }
    }

}
