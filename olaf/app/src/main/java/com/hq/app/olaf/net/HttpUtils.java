package com.hq.app.olaf.net;

import android.os.Build;
import android.text.TextUtils;

import org.json.JSONException;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/20.
 */

public class HttpUtils {

     public final static String SERVER_ADDRESS = "http://59.110.172.75:8081/crash";//生产服务器IP
//     public final static String SERVER_ADDRESS = "http://101.200.38.204:8082/crash";//测试服务器IP
    public final static String appId = "001";//调用方注册的应用ID

    /*应用平台*/
    private static String osPlatform = "Android";

    /*设备名称*/
    public static String deviceName = Build.BRAND;

    public static Map<String, Object> getCommonParams(Map<String, Object> params) {
        //JSONObject params = new JSONObject();
        try {

            params.put("appId", appId);
            params.put("osPlatform", osPlatform);
            params.put("deviceName", deviceName);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return params;
    }

    /**
     * 判断响应结果是否为空数据
     *
     * @param body
     * @return
     * @throws JSONException
     */
    public static  boolean isEmpty(String body) throws JSONException {
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
