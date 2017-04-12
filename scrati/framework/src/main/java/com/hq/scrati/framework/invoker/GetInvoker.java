package com.hq.scrati.framework.invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hq.esc.entity.generate.TesbHttp;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.log.Logger;
import org.springframework.stereotype.Component;


@Component
public class GetInvoker extends HttpInvoker{

	/**
	 * 调用http请求服务端
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public Object invokeMethod(TesbHttp tesbHttp, List<Object> parameters,Map<String,Object> headTmp) throws Exception{
		String result = "";  
        BufferedReader in = null;  
        try {
        	StringBuilder url = new StringBuilder(tesbHttp.getUrl());
        	if(url.indexOf("?")<0){
				 url.append("?");
			 }
			 if(!"\\&".equals(url.substring(url.length() -1))&&!"?".equals(url.substring(url.length() -1))){
				 url.append("&");
			 }
        	for(int i = 0;i<parameters.size();i++){
        		url.append(parameters.get(i)).append("&");
			} 
            URL realUrl = new URL(url.substring(0, url.length()-1));  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charset", tesbHttp.getCharset());
            conn.setRequestProperty("logId", Logger.getRootLogger().getLogId());
            setCustomHeader(conn,headTmp);
            conn.setConnectTimeout(tesbHttp.getTimeout().intValue());
            // 建立实际的连接  
            conn.connect(); 
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "/n" + line;  
            }  
        } catch (Exception e) {  
        	throw new Exception(e.getMessage());
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
            	throw new Exception(ex.getMessage());
            }  
        }  
	    if("2".equals(tesbHttp.getOriginal())){
	    	return result.toString();
	    }else{
	    	 RespEntity<Map<String, Object>> respEntity =new RespEntity<Map<String, Object>>("0000");
	         Map<String, Object> ext = new HashMap<String, Object>();
	         ext.put("result", result.toString());
	         respEntity.setExt(ext);
	         return respEntity;
	    }
	}
	
	public static void main(String args[]){
		System.out.println("我的家?测试数据".indexOf("?"));
	}

}
