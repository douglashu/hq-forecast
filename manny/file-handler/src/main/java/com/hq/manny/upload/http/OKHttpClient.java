package com.hq.manny.upload.http;

import com.alibaba.dubbo.common.json.JSON;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Zale on 16/8/10.
 */
public class OKHttpClient {
    private static OkHttpClient client = new OkHttpClient();
    private static MediaType JSONType= MediaType.parse("application/json; charset=utf-8");
    public static byte[] getFile(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().bytes();
        }

    }
    public static String postForm(String url, Map<String,String> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();

        if(params!=null){
            for(Map.Entry<String,String> entry: params.entrySet()){
                String val = entry.getValue();

                builder.addEncoded(entry.getKey(),val);
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public static String postJSON(String url, Map<String,String> params) throws IOException {
        RequestBody body = RequestBody.create(JSONType, JSON.json(params));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
//    public static void main(String[] args) throws IOException {
//        byte[] b = OKHttpClient.getFile("http://172.19.80.27:11680/name/1479799077316830.jpg");
//        FileOutputStream out = new FileOutputStream("/Users/zale/1.jpg");
//        out.write(b);
//        out.flush();
//        out.close();
//    }


}
