package com.hq.ellie.demo.dubbo.provider.impl;

import java.util.HashMap;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.ellie.demo.dubbo.provider.DemoService;
import com.hq.esc.inf.entity.RespEntity;

@Service(interfaceClass=com.hq.ellie.demo.dubbo.provider.DemoService.class,version="1.0")
public class DemoServiceImpl implements DemoService {
	public Object sayHello(String value) {  
		System.out.println( "Hello " + value);
		RespEntity resp = new RespEntity();
		resp.setKey("0000");
		HashMap<String, Object> map =new HashMap<String, Object>();
		map.put("name", "zhouyang");
		resp.setExt(map);
		return resp;   
	} 
	
	public static void main(String args[]){
		String aaa = "1111111111111";
		System.out.println(aaa);
	}
}
