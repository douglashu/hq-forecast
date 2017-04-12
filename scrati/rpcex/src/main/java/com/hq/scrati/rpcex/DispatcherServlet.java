package com.hq.scrati.rpcex;

import com.alibaba.fastjson.JSON;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.model.HqRequest;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Zale on 2017/3/15.
 */
public class DispatcherServlet extends HttpServlet {
    private static final String PARAM_SERVICE="service";
    private static final String PARAM_METHOD="method";
    private static final String PARAM_REQUEST="request";
    private ApplicationContext context;

    public DispatcherServlet(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if("GET".equals(method)){
            String service = req.getParameter(PARAM_SERVICE);
            if(StringUtils.isBlank(service)){
                return;
            }
            String srvMethod = req.getParameter(PARAM_METHOD);
            if(StringUtils.isBlank(srvMethod)){
                return;
            }
            String request = req.getParameter(PARAM_REQUEST);
            if(StringUtils.isBlank(request)){
                return;
            }
            HqRequest hqRequest = JSON.parseObject(request,HqRequest.class);

            Object obj = null;
            try {
                obj = context.getBean(Class.forName("com.hq.sid.dubbo."+service));
            Class clazz = obj.getClass();
                clazz.getMethod(srvMethod, HqRequest.class).invoke(obj, hqRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
