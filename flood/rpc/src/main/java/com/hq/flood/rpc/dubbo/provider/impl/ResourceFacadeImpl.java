package com.hq.flood.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.flood.rpc.dubbo.provider.ResourceFacade;
import com.hq.flood.service.ResourceService;
import com.hq.flood.service.entity.request.PrdResourcesReq;
import com.hq.flood.service.entity.request.ResourceIdReq;
import com.hq.flood.service.entity.request.ResourceReq;
import com.hq.flood.service.entity.request.RoleResourcesReq;
import com.hq.flood.service.entity.response.ResourceRsp;
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
 * @创建时间：16/12/16 下午11:59
 */
@Service(version = "1.0",interfaceName = "com.hq.flood.rpc.dubbo.provider.ResourceFacade")
public class ResourceFacadeImpl implements ResourceFacade {
    @Autowired
    private ResourceService resourceService;

    private Logger logger = Logger.getLogger();

    @Override
    public boolean insert(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        ResourceReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ResourceReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = resourceService.saveResourceTransactional(hqRequest.getUserId(), req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean delete(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }

        ResourceIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ResourceIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getResourceId())) {
            throw new ParamValueException("参数【resourceId】不能为空");
        }
        try {
            Boolean flag = resourceService.deleteResourceTransactional(hqRequest.getUserId(), req.getResourceId());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean update(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        ResourceReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ResourceReq>() {
        });
        if (req == null || StringUtils.isBlank(req.getResourceId())) {
            throw new ParamValueException("参数【resourceId】不能为空");
        }
        try {
            Boolean flag = resourceService.updateResourceTransactional(hqRequest.getUserId(), req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ResourceRsp queryById(HqRequest hqRequest) {
        ResourceIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<ResourceIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getResourceId())) {
            throw new ParamValueException("参数【resourceId】不能为空");
        }
        try {
            ResourceRsp rsp = resourceService.queryById(req.getResourceId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<ResourceRsp> findResoucesByPrdCode(HqRequest hqRequest) {
        PrdResourcesReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<PrdResourcesReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getPrdCode())) {
            throw new ParamValueException("参数【prdCode】不能为空");
        }
        try {
            List<ResourceRsp> rsp = resourceService.queryResourceTreeByPrdId(req.getPrdCode());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public List<ResourceRsp> findResoucesByRoleId(HqRequest hqRequest) {
        RoleResourcesReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RoleResourcesReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        if (StringUtils.isBlank(req.getRoleId())) {
            throw new ParamValueException("参数【roleId】不能为空");
        }
        try {
            List<ResourceRsp> rsp = resourceService.queryResourceTreeByRoleId(req.getRoleId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
