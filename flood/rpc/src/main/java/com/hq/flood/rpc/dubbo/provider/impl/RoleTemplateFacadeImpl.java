package com.hq.flood.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.flood.rpc.dubbo.provider.RoleTemplateFacade;
import com.hq.flood.service.RoleTemplateService;
import com.hq.flood.service.entity.request.OperResourcesReq;
import com.hq.flood.service.entity.request.RoleTemplateReq;
import com.hq.flood.service.entity.response.ResourceRsp;
import com.hq.flood.service.entity.response.RoleTemplateRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.rpc.helper.RpcReturnHelper;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：17/2/10 下午8:51
 */
@Service(version = "1.0",interfaceName = "com.hq.flood.rpc.dubbo.provider.RoleTemplateFacade")
public class RoleTemplateFacadeImpl implements RoleTemplateFacade {

    private static Logger logger = Logger.getLogger();

    @Autowired
    private RoleTemplateService roleTemplateService;

    @Override
    public List<RoleTemplateRsp> queryByRoleId(HqRequest hqRequest) {

        RoleTemplateReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RoleTemplateReq>() {
        });

        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getRoleId())) {
            throw new ParamValueException("参数【roleId】不能为空");
        }
        try {
            List<RoleTemplateRsp> rsp = roleTemplateService.queryByRoleId(req.getRoleId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
