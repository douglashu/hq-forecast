package com.hq.peaches.demo.dubbo.provider.impl;

import java.util.HashMap;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.peaches.demo.dubbo.provider.DemoService;


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
