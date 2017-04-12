package com.hq.app.olaf.net.sign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Author Zale
 * @Date 16/7/18 上午11:21
 *
 */
public class AssemblyUtil {

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, Object> paraFilter(Map<String, Object> sArray) {
    	
        Map<String, Object> result = new HashMap<String, Object>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            Object value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type") || key.equalsIgnoreCase("authKey")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }


    /**
     * 把数组所有元素按照ascII排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = null;
            Object obj = params.get(key);
            if( obj instanceof ArrayList ){
                String temp = "[";
                for(int j =0; j< ((ArrayList) obj).size();j++ ){
                    Object o = ((ArrayList) obj).get(j);
                    if( o instanceof Map){
                        temp += "{"+createLinkString((Map<String,Object>)o)+"},";
                    }
                }
                temp = temp.substring(0,temp.length()-1) +"]";
                value = temp;
            }else if( obj instanceof Map) {
                value = "{"+createLinkString((Map<String, Object>) obj)+"}";
            }else if(obj instanceof String){
                value = (String)obj;
            }else{
                if( null != obj ){
                    value = obj.toString();
                }
            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
}
