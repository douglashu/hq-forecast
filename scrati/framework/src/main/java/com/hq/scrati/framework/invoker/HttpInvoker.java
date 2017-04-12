package com.hq.scrati.framework.invoker;

import com.alibaba.fastjson.JSON;
import com.hq.esc.inf.entity.Constants;

import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Zale on 16/8/16.
 */
public abstract class HttpInvoker {
    protected void setCustomHeader(URLConnection urlConnection,Map<String,Object> params){
        if(params!=null){
            for(Map.Entry<String,Object> entry:params.entrySet()){
                if(entry.getKey().startsWith(Constants.HTTP_HEAD_PREFIX)){
                    Object val = entry.getValue();
                    String valStr;
                    if(val instanceof String){
                        valStr = (String)val;
                    }else{
                        valStr = JSON.toJSONString(val);
                    }
                    urlConnection.setRequestProperty(entry.getKey().replace(Constants.HTTP_HEAD_PREFIX,""),valStr);
                }
            }
        }
    }
}
