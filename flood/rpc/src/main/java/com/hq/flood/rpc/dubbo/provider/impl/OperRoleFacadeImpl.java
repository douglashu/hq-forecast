package com.hq.flood.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.flood.rpc.dubbo.provider.OperRoleFacade;
import com.hq.flood.service.OperRoleService;
import com.hq.flood.service.entity.request.OperRoleReq;
import com.hq.flood.service.entity.request.OperRoleSubReq;
import com.hq.flood.service.entity.request.OperRoleTemplateReq;
import com.hq.flood.service.entity.response.OperRoleRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.rpc.helper.RpcReturnHelper;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：17/2/9 下午10:22
 */
@Service(version = "1.0",interfaceName = "com.hq.flood.rpc.dubbo.provider.OperRoleFacade")
public class OperRoleFacadeImpl implements OperRoleFacade {

    private static Logger logger = Logger.getLogger();

    @Autowired
    private OperRoleService operRoleService;

    @Override
    public Boolean bindRole(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        OperRoleReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<OperRoleReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = operRoleService.bindRole(Integer.parseInt(hqRequest.getUserId()), req.getOperId(), req.getRoles());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Boolean bindRoleTemplate(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        OperRoleTemplateReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<OperRoleTemplateReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = operRoleService.bindRoleTemplate(Integer.parseInt(hqRequest.getUserId()), req.getOperId(),req.getCoreId(), req.getTemplateId());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<OperRoleRsp> queryOpersWithMchId(HqRequest hqRequest) {

        OperRoleSubReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<OperRoleSubReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            List<OperRoleRsp> rsp = operRoleService.queryOpersWithMchId(req.getRoleId(),req.getPcoreId(),req.getCoreId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
