package com.hq.scrati.framework.invoker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.hq.esc.inf.entity.Constants;
import com.hq.scrati.common.exception.InfException;
import com.hq.scrati.common.log.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;

/**
 * @author zale
 *
 */
@Component
public class DubboInvoker {
	
	public static final Logger LOGGER = Logger.getLogger(DubboInvoker.class);
	
	/**
	 * ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接 缓存所有的reference
	 */
	public static Map<String, ReferenceConfig<GenericService>> referenceMap = new ConcurrentHashMap<String, ReferenceConfig<GenericService>>();

	// 当前应用配置
	@Resource
	private ApplicationConfig application = null;

	// 连接注册中心配置
	@Resource
	private RegistryConfig registry = null;


	private GenericService getService(String clazz, String version) {
		// 引用远程服务
		ReferenceConfig<GenericService> reference = null;
		if (referenceMap.containsKey(clazz+"_"+version)) {
			reference = referenceMap.get(clazz+"_"+version);
		} else {
			reference = new ReferenceConfig<GenericService>();
			referenceMap.put(clazz+"_"+version, reference);
		}
		reference.setApplication(application);
		reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
		reference.setGeneric(true);
		reference.setInterface(clazz);
		reference.setVersion(version);
		GenericService genericService = reference.get();
		if(genericService==null){
			referenceMap.remove(clazz+"_"+version);
		}
		return genericService;
	}


	public Object invokeMethod(String clazz, String methodName,
			String[] parameterTypes, Object[] parameters, String version) throws InfException {
		
		GenericService service = getService(clazz, version);
		RpcContext.getContext().setAttachment("logId",Logger.getRootLogger().getLogId());
		if (null != service) {
			return service.$invoke(methodName, parameterTypes, parameters);
		}else{
			LOGGER.info("find service is failure , clazz:"+clazz+" version:"+version);
			String msg = "find service is failure , clazz:"+clazz+" version:"+version;
			throw new InfException(Constants.SERVICE_UNAVAILABLE,msg," ");
		}
	}

}
