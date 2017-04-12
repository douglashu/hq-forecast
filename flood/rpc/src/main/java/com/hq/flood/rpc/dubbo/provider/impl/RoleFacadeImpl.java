package com.hq.flood.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.hq.flood.dao.generate.TFloodRoleTemplateMapper;
import com.hq.flood.entity.generate.TFloodRoleExample;
import com.hq.flood.rpc.dubbo.provider.RoleFacade;
import com.hq.flood.service.RoleResourceService;
import com.hq.flood.service.RoleService;
import com.hq.flood.service.entity.request.*;
import com.hq.flood.service.entity.response.BatchOperRoleRsp;
import com.hq.flood.service.entity.response.RoleRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.rpc.helper.RpcReturnHelper;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：16/12/16 下午11:43
 */
@Service(version = "1.0",interfaceName = "com.hq.flood.rpc.dubbo.provider.RoleFacade")
public class RoleFacadeImpl implements RoleFacade{

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceService roleResourceService;

    private Logger logger = Logger.getLogger();

    @Override
    public boolean insert(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        RoleReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RoleReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            String roleId = roleService.saveRoleTransactional(hqRequest.getUserId(), req);
            boolean flag = Boolean.TRUE;
            if(CollectionUtils.isNotEmpty(req.getResourceIds())){
                flag = roleResourceService.bind(hqRequest.getUserId(),roleId,req.getResourceIds());

            }
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

        RoleIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RoleIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getRoleId())) {
            throw new ParamValueException("参数【roleId】不能为空");
        }
        try {
            Boolean flag = roleService.deleteRoleTransactional(hqRequest.getUserId(), req.getRoleId());
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
        RoleReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RoleReq>() {
        });
        if (req == null || StringUtils.isBlank(req.getRoleId())) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = roleService.updateRoleTransactional(hqRequest.getUserId(), req);
            if(CollectionUtils.isNotEmpty(req.getResourceIds())) {
                flag = roleResourceService.bind(hqRequest.getUserId(), req.getRoleId(), req.getResourceIds());
            }
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public RoleRsp queryById(HqRequest hqRequest) {
        RoleIdReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RoleIdReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }

        if (StringUtils.isBlank(req.getRoleId())) {
            throw new ParamValueException("参数【roleId】不能为空");
        }
        try {
            RoleRsp rsp = roleService.queryRoleById(req.getRoleId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public PageInfo<RoleRsp> findRoleByCondition(HqRequest hqRequest) {
        RolePageReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RolePageReq>() {
        });
        if (null == req || null == req.getPage()) {
            req.setPage(0);
        }
        if (null == req || null == req.getPageSize()) {
            req.setPageSize(20);
        }
        try {
            PageInfo<RoleRsp> page = roleService
                    .queryRoles(req.getRoleName(),req.getRoleStatus(),req.getUrmId(),req.getpRoleId(), req.getPage(),req.getPageSize());
            return page;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Boolean initRoles(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getUserId())) {
            throw new ParamValueException("参数【userId】不能为空");
        }
        RoleInitReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<RoleInitReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = roleService.initRoles(hqRequest.getUserId(), req.getSystemId(),req.getCoreId(),req.getIsAdmin());
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<BatchOperRoleRsp> batchRoles(HqRequest hqRequest) {
        BatchOperRoleReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<BatchOperRoleReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            List<BatchOperRoleRsp> rsps = roleService.batchRoles(req.getSystemId(),req.getOperIds());
            return rsps;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
