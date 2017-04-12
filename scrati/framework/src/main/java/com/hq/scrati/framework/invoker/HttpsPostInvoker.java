package com.hq.scrati.framework.invoker;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.hq.esc.entity.generate.TesbHttp;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.log.Logger;
import org.springframework.stereotype.Component;


@Component
public class HttpsPostInvoker {
	/**
	 * 调用https请求服务端
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
    public Object invokeMethod(TesbHttp tesbHttp, List<Object> parameters) throws Exception{
    	String rsp = null; 
    	HttpsURLConnection conn=null;
    	DataOutputStream out=null;
        try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			String url = "";
	    	StringBuilder param = new StringBuilder();
			if(tesbHttp.getUrl().indexOf("?")<0){
	    		url = tesbHttp.getUrl();
			}else{
				url = tesbHttp.getUrl().substring(0,tesbHttp.getUrl().indexOf("?"));
				param.append(tesbHttp.getUrl().substring(tesbHttp.getUrl().indexOf("?")+1));
			}
			for(int i = 0;i<parameters.size();i++){
	    		param.append(parameters.get(i)).append("&");
			}
			URL console = new URL(url);
			conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setDoOutput(true);
			conn.setDoInput(true);   
	        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
	        conn.setRequestProperty("connection", "Keep-Alive");
	        conn.setRequestProperty("User-Agent", "stargate");
	        conn.setRequestProperty("logId", Logger.getRootLogger().getLogId());
	        conn.setConnectTimeout(tesbHttp.getTimeout().intValue());
			conn.connect();
			out = new DataOutputStream(conn.getOutputStream());
			out.write(param.substring(0, param.length()-1).getBytes(tesbHttp.getCharset()));
			// 刷新、关闭
			out.flush();
			out.close();
			rsp = getResponseAsString(conn,tesbHttp);
	        
        }catch(Exception e){  
            throw e;  
        }finally {  
            if (out != null) {  
                out.close();  
            }  
            if (conn != null) {  
                conn.disconnect();  
            }  
        }
	    if("2".equals(tesbHttp.getOriginal())){
	    	return rsp.toString();
	    }else{
	    	 RespEntity<Map<String, Object>> respEntity =new RespEntity<Map<String, Object>>("0000");
	         Map<String, Object> ext = new HashMap<String, Object>();
	         ext.put("result", rsp.toString());
	         respEntity.setExt(ext);
	         return respEntity;
	    }
	
    }
    
    protected static String getResponseAsString(HttpURLConnection conn,TesbHttp tesbHttp) throws IOException {  
        String charset = tesbHttp.getCharset();  
        InputStream es = conn.getErrorStream();  
        if (es == null) {  
            return getStreamAsString(conn.getInputStream(), charset);  
        } else {  
            String msg = getStreamAsString(es, charset);  
            if (msg==null||"".equals(msg)) {  
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());  
            } else {  
                throw new IOException(msg);  
            }  
        }  
    }
  
    private static String getStreamAsString(InputStream stream, String charset) throws IOException {  
        try {  
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));  
            StringWriter writer = new StringWriter();  
  
            char[] chars = new char[256];  
            int count = 0;  
            while ((count = reader.read(chars)) > 0) {  
                writer.write(chars, 0, count);  
            }  
  
            return writer.toString();  
        } finally {  
            if (stream != null) {  
                stream.close();  
            }  
        }  
    } 
    
    private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}  
	
//	public Object invokeMethod(TesbHttp tesbHttp, List<Object> parameters) throws Exception{
//		 String result = "";
//	     HttpClient httpClient = null;  
//	     HttpPost httpPost = null; 
//	     try {
//	    	httpClient = new SSLClient(); 
//	    	// 请求超时
//	    	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, tesbHttp.getTimeout().intValue());
//            // 读取超时
//	    	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, tesbHttp.getTimeout().intValue());
//	    	httpClient.getParams().setParameter(CoreConnectionPNames.SO_KEEPALIVE,"Keep-Alive");
//	    	String url = "";
//	    	StringBuilder param = new StringBuilder();
//	    	if(tesbHttp.getUrl().indexOf("?")<0){
//	    		url = tesbHttp.getUrl();
//			}else{
//				url = tesbHttp.getUrl().substring(0,tesbHttp.getUrl().indexOf("?"));
//				param.append(tesbHttp.getUrl().substring(tesbHttp.getUrl().indexOf("?")+1));
//			}
//	    	httpPost = new HttpPost(url);
//	    
//	    	//设置参数  
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//	    	for(int i = 0;i<parameters.size();i++){
//	    		String[] paraArr = parameters.get(i).toString().split("=");
//                list.add(new BasicNameValuePair(paraArr[0],paraArr[1]));
//			}
//	    	if(list.size() > 0){  
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,tesbHttp.getCharset());  
//                httpPost.setEntity(entity);  
//            }
//	    	
//            HttpResponse response = httpClient.execute(httpPost);  
//            if(response != null){  
//                HttpEntity resEntity = response.getEntity();  
//                if(resEntity != null){  
//                    result = EntityUtils.toString(resEntity,tesbHttp.getCharset());  
//                }  
//            }
//	    } catch (Exception e) {
//	    	throw new Exception(e.getMessage());
//        }
//		 RespEntity respEntity =new RespEntity("0000");
//         Map<String, Object> ext = new HashMap<String, Object>();
//         ext.put("result", result.toString());
//         respEntity.setExt(ext);
//         return respEntity;
//        
//	}

}
