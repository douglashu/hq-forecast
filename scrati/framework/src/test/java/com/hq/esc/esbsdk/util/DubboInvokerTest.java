package com.hq.esc.esbsdk.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.json.JSON;
import com.hq.scrati.framework.FrameworkInvoker;

/**
 * Created by Zale on 16/8/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test-esbsdk.xml")
public class DubboInvokerTest {
    @Autowired
    private FrameworkInvoker frameworkInvoker;
    @Test
    public void invoke() throws Exception {
    	Map<String, String> map = new HashMap<String, String>();
		String str=JSON.json(map).toString();
		Map<String, Object> map1 = new HashMap<>();
		map1.put("reqMap", "{}");
		Object entity=null;
	//	for(int i=0;i<1000;i++){
	//		Thread.sleep(10000);
			entity =  frameworkInvoker.invoke("ORD001", "1.0", map1);
	
	//	}
		System.out.println(JSON.json(entity).toString());
	
    }

}