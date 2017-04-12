/**
 * 
 */
package com.hq.manny.upd.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.hq.manny.upload.FileHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author Zale
 *
 */
@Component
public class UploadHandlerDispatcher  implements ApplicationContextAware{
	private static ApplicationContext CONTEXT;
	private static Map<Integer,String> HANDLER = new HashMap<Integer, String>();
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		CONTEXT = applicationContext;
	}
	
	public static FileHandler getHandler(int fileType){
		String cls = HANDLER.get(fileType);
		if(cls!=null){
			FileHandler handler;
			try {
				handler = (FileHandler) CONTEXT.getBean(Class.forName(cls));
				return handler;
			} catch (BeansException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@PostConstruct
	private void registerHandler(){
		/*HANDLER.put(Params.UploadFileType.VISITORS, ImportVisitorHandler.class.getName());*/
	}
}
