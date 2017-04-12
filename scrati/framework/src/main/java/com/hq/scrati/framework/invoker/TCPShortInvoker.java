package com.hq.scrati.framework.invoker;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hq.esc.entity.generate.TesbTcp;
import com.hq.esc.inf.entity.RespEntity;
import org.springframework.stereotype.Component;

@Component("tcpShortInvoker")
public class TCPShortInvoker {
	/**
	 * 调用http请求服务端
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public Object invokeMethod(TesbTcp tesbTcp, List<Object> parameters) throws Exception{
		Socket socket=null;
		 String message = null;  
		 BufferedReader in = null;  
         PrintWriter out = null;
         BufferedReader line = null;
		 try {
			 StringBuilder params = new StringBuilder(tesbTcp.getPrefix()==null?"":tesbTcp.getPrefix());
			 for(int i = 0;i<parameters.size();i++){
				 params.append(parameters.get(i));
				 if(i<parameters.size()-1){
					 params.append(tesbTcp.getSeparator());
				 }
			 }
			 params.append(tesbTcp.getPostfix()==null?"":tesbTcp.getPostfix());
			 socket = new Socket(tesbTcp.getIp(), tesbTcp.getPort());
			 out = new PrintWriter(socket.getOutputStream(), true); 
             line = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(params.toString().getBytes("UTF-8"))));
             message = line.readLine();
             out.println(message);
             
             socket.setSoTimeout(tesbTcp.getTimeout().intValue());
             in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
             StringBuilder returnParams = new StringBuilder();
             String reline;
             while ((reline=in.readLine()) != null) {  
                 if (tesbTcp.getPostfix()!=null&&reline.indexOf(tesbTcp.getPostfix()) != -1) {//遇到结束符就结束接收
                	returnParams.append(reline); 
                 	break;  
                 }  
                 returnParams.append(reline);  
              } 
             RespEntity respEntity =new RespEntity("0000");
             Map<String, Object> ext = new HashMap<String, Object>();
             ext.put("result", returnParams.toString());
             respEntity.setExt(ext);
             return respEntity;
        } catch (MalformedURLException e) {
        	throw new Exception(e.getMessage());
        } catch (IOException e) {
        	throw new Exception(e.getMessage());
        }finally{
        	if(socket!=null){
        		socket.close();
        	}
        	if(in!=null){
        		in.close();
        	}
        	if(out!=null){
        		out.close();
        	}
        	if(line!=null){
        		line.close();
        	}
        }
	}	
}
