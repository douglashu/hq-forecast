package com.hq.scrati.framework.invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hq.esc.entity.generate.TesbHttp;
import com.hq.esc.inf.entity.Constants;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.InfException;
import org.springframework.stereotype.Component;

import com.hq.scrati.common.log.Logger;

@Component
public class RestFulInvoker extends HttpInvoker{
	/**
	 * 调用http请求服务端
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public Object invokeMethod(TesbHttp tesbHttp, List<Object> parameters, Map<String, Object> headTmp) throws Exception{
		 StringBuilder result = new StringBuilder("");
		 HttpURLConnection httpConnection=null;
		 try {
			 StringBuilder url = new StringBuilder(tesbHttp.getUrl());
			 if(!"\\/".equals(url.substring(url.length() -1))){
				 url.append("/");
			 }
			 for(int i = 0;i<parameters.size();i++){
				 url.append(parameters.get(i)).append("/");
			 }
             URL restServiceURL = new URL(url.toString());
             httpConnection = (HttpURLConnection) restServiceURL.openConnection();
             httpConnection.setRequestMethod(tesbHttp.getMethod());
             httpConnection.setRequestProperty("Accept", "application/json");
             httpConnection.setRequestProperty("logId",Logger.getRootLogger().getLogId());
			 setCustomHeader(httpConnection,headTmp);
             httpConnection.setConnectTimeout(tesbHttp.getTimeout().intValue());
             if (httpConnection.getResponseCode() != 200) {
            	 httpConnection.disconnect();
            	 throw new InfException(Constants.SERVICE_UNAVAILABLE,"HTTP Request Failed with Error code "+httpConnection.getResponseCode()," ");
             }
             BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream()),tesbHttp.getCharset()));
             String output;
             while ((output = responseBuffer.readLine())!= null) {
            	 result.append(output);
             }
             
        } catch (MalformedURLException e) {
        	throw new Exception(e.getMessage());
        } catch (IOException e) {
        	throw new Exception(e.getMessage());
        }finally{
        	if(httpConnection!=null){
        		httpConnection.disconnect();
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
}
