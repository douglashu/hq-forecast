package com.hq.scrati.framework.invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public class PostInvoker extends HttpInvoker{

	public static final Logger logger = Logger.getLogger(PostInvoker.class);
	/**
	 * 调用http请求服务端
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public Object invokeMethod(TesbHttp tesbHttp, List<Object> parameters,Map<String,Object> headTmp) throws Exception{
		
		 StringBuilder result = new StringBuilder("");
		 PrintWriter out = null;
	     BufferedReader in = null;
	     try {
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
	    	URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("logId",Logger.getRootLogger().getLogId());
            conn.setRequestProperty("Charset", tesbHttp.getCharset());
            setCustomHeader(conn,headTmp);
            conn.setConnectTimeout(tesbHttp.getTimeout().intValue());
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param.substring(0, param.length()-1));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),tesbHttp.getCharset()));
            String line;
            while ((line = in.readLine()) != null) {
            	result.append(line);
            }
	    } catch (Exception e) {
	    	logger.info("",e);
        	throw new Exception(e.getMessage());
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
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

}
