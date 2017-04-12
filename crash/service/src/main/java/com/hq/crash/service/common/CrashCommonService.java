package com.hq.crash.service.common;

import com.hq.crash.model.auth.UserSession;
import com.hq.crash.model.auth.resp.LoginMchResp;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.framework.invoker.ServiceInvoker;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 14/01/2017.
 */
public class CrashCommonService {

    @Value("${esc.url}")
    private String escUrl;

    private ServiceInvoker serviceInvoker = ServiceInvoker.newInstance();

    public <T> T invoke(String service, String version, HqRequest hqRequest, Class<T> clazz) {
        if(StringUtils.isEmpty(service)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "服务名不能为空[service]");
        }
        if(StringUtils.isEmpty(version)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "服务版本号为空[version]");
        }
        if(hqRequest == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "请求数据不能为空[hqRequest]");
        }
        RespEntity<T> respEntity = this.serviceInvoker
                .invoke(this.escUrl, service, version, hqRequest, clazz);
        if((!CommonErrCode.NONE.getCode().equals(respEntity.getKey()) && !"0000".equals(respEntity.getKey()))) {
            throw new CommonException(respEntity.getKey(), respEntity.getMsg());
        }
        return respEntity.getExt();
    }

    public HqRequest getHqRequest(UserSession userSession) {
        HqRequest hqRequest = new HqRequest();
        hqRequest.setAppId(userSession.getAppId());
        hqRequest.setUserId(userSession.getUserId());
        LoginMchResp loginMchResp = userSession.activeMchInfo();
        if (loginMchResp != null) {
            hqRequest.setCoreId(loginMchResp.getCoreId());
            hqRequest.setMchId(loginMchResp.getMchId());
        }
        return hqRequest;
    }

    public String getEscUrl() {
        return escUrl;
    }

    public ServiceInvoker getServiceInvoker() {
        return serviceInvoker;
    }

}
