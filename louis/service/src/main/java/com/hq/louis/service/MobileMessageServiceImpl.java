package com.hq.louis.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

@Service("mobileMessageService")
public class MobileMessageServiceImpl implements MobileMessageService {
	private final static Logger logger = LoggerFactory.getLogger(MobileMessageServiceImpl.class);
	private String url = "http://si.800617.com:4400/SendLenSms.aspx";
	private String un = "hnclxx-1";
	private String pwd = "f1345b";
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String sendMobile(String dest,String str){
		String responseCode = SEND_SUCCESS;//短信接口调用响应码
		HttpClient client = new HttpClient();
		StringBuffer buff = new StringBuffer();
		try{
			buff.append(this.url+"?");
			buff.append("un="+this.un+"&");
			buff.append("pwd="+this.pwd+"&");
			buff.append("mobile="+dest+"&");
			buff.append("msg="+URLEncoder.encode(str,"gbk"));
			HttpMethod method = new GetMethod(buff.toString());
			int i = client.executeMethod(method);
			if(i==200){
				String res = new String(method.getResponseBody());
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		        DocumentBuilder builder = factory.newDocumentBuilder();  
		        ByteArrayInputStream is = new ByteArrayInputStream(res.getBytes());
		        Document document =  builder.parse(is);
				Element root = document.getDocumentElement();
				NodeList nodeList = root.getElementsByTagName("Result");
				res = nodeList.item(0).getTextContent();
				if(SEND_SUCCESS.equals(res)){
					logger.debug("手机["+dest+"]短信发送成功");
				}else{
					responseCode = res;
					throw new RuntimeException("发送短信失败:res="+res);
				}
			}else{
				responseCode = method.getResponseBodyAsString();
				throw new RuntimeException("发送短信失败:"+method.getResponseBody());
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return responseCode;
	}

}
