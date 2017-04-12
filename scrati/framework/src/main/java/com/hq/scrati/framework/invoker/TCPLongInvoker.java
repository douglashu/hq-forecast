package com.hq.scrati.framework.invoker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hq.esc.entity.generate.TesbTcp;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.tcp.TCPLongClient;
import org.springframework.stereotype.Component;


@Component("tcpLongInvoker")
public class TCPLongInvoker {
	/**
	 * 调用http请求服务端
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public Object invokeMethod(TesbTcp tesbTcp, List<Object> parameters) throws Exception{
		 StringBuilder params = new StringBuilder(tesbTcp.getPrefix());
		 for(int i = 0;i<parameters.size();i++){
			 params.append(parameters.get(i));
			 if(i<parameters.size()-1){
				 params.append(tesbTcp.getSeparator());
			 }
		 }
		 params.append(tesbTcp.getPostfix());
		 
		 String returnParams = TCPLongClient
				 .getClinet(tesbTcp.getIp()+"_"+tesbTcp.getPort()).send(params.toString(), tesbTcp.getTimeout().intValue(), tesbTcp.getPostfix());
         RespEntity respEntity =new RespEntity("0000");
         Map<String, Object> ext = new HashMap<String, Object>();
         ext.put("result", returnParams);
         respEntity.setExt(ext);
         return respEntity;
    }	
}
