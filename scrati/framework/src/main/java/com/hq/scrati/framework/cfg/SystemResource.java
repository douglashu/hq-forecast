package com.hq.scrati.framework.cfg;


import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.util.HashMap;

import com.hq.elva.rpc.facade.TBasConfigFacade;
import com.hq.esc.inf.entity.RespEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Zale on 2016/11/4.
 */
//@Component("systemResource")
public class SystemResource extends AbstractResource {
//    @Resource(name="configFacade")
    private TBasConfigFacade configFacade;
//    @Value("#{sdkconfig.application}")
    private String application;
    private Logger logger = LoggerFactory.getLogger(SystemResource.class);

    public TBasConfigFacade getConfigFacade() {
        return configFacade;
    }

    public void setConfigFacade(TBasConfigFacade configFacade) {
        this.configFacade = configFacade;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    private Map<String,String> cacheProps;

    @Override
    public String getDescription() {
        return "dubbo resource";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        RespEntity<Map<String,String>> rst = configFacade.getTBasConfigByServiceName(application,ip);
        Map<String,String> properties = rst.getExt();
        StringBuilder sb = new StringBuilder();
        cacheProps = new HashMap<>();
        for(Map.Entry<String,String> prop:properties.entrySet()){
            sb.append(prop.getKey()).append("=").append(prop.getValue()).append("\n\r");
            cacheProps.put(prop.getKey(),prop.getValue());
        }
        logger.debug(sb.toString());
        InputStream input = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
        return input;
    }

    public String get(String key){
        return cacheProps.get(key);
    }
}
