package com.hq.scrati.framework.web.cfg;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.constants.FrameworkConstants;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Zale on 2016/11/4.
 */
public class DubboPropertySourceLoader implements PropertySourceLoader {
    @Override
    public String[] getFileExtensions() {
        return new String[] { "cfg" };
    }
    private Properties prop;
    private GenericService getService(String application, String registry, String clazz, String version) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(application);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress(registry);
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
        reference.setGeneric(true);
        reference.setInterface(clazz);
        reference.setVersion(version);
        GenericService genericService = reference.get();
        return genericService;
    }

    @Override
    public PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
        if(prop == null) {
            Properties localProps = new Properties();
            localProps.load(resource.getInputStream());
            String application = localProps.getProperty("appName");
            String zookeeperAddr = localProps.getProperty("zookeeper.address");
            GenericService service = getService(application, zookeeperAddr, "com.hq.elva.rpc.facade.TBasConfigFacade", "1.0");
            String ip = InetAddress.getLocalHost().getHostAddress();
            Object serviceResult = service
                    .$invoke("getTBasConfigByServiceName", new String[] { "java.lang.String", "java.lang.String" },
                            new Object[] { application, ip });
            @SuppressWarnings("unchecked")
			Map<Object, Object> serviceMapResult = (HashMap<Object, Object>) serviceResult;
			if (FrameworkConstants.RESPONSE_ENTITY_CANONICAL_NAME.equals(serviceMapResult.get("class"))) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				RespEntity<Map<String, String>> rst = new RespEntity((String) serviceMapResult.get("key"),
						(String) serviceMapResult.get("msg"), " ", serviceMapResult.get("ext"));
				Map<String, String> properties = rst.getExt();
				StringBuilder sb = new StringBuilder();
				sb.append("sdkconfig.application").append("=").append(application).append("\n\r");
				sb.append("sdkconfig.zookeeperAddress").append("=").append(zookeeperAddr).append("\n\r");
				for (Map.Entry<String, String> prop : properties.entrySet()) {
					sb.append(prop.getKey()).append("=").append(prop.getValue()).append("\n\r");
				}
				System.out.println(sb.toString());
				InputStream input = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
				prop = new Properties();
				prop.load(input);
			}
        }
        return  new PropertiesPropertySource(name, prop);
    }
}
