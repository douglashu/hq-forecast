package com.hq.flood.service.impl;

import com.hq.flood.dao.custom.ResourceDao;
import com.hq.flood.dao.generate.TFloodOperRoleMapper;
import com.hq.flood.dao.generate.TFloodRoleResourceMapper;
import com.hq.flood.entity.generate.*;
import com.hq.flood.service.ResourceService;
import com.hq.flood.service.RoleResourceService;
import com.hq.flood.service.entity.response.ResourceRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.impl
 * @创建人：yyx
 * @创建时间：16/12/15 下午8:29
 */
@Service
public class RoleResourceServiceImpl implements RoleResourceService {

    private Logger logger = Logger.getLogger();

    @Autowired
    private TFloodRoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private TFloodOperRoleMapper operRoleMapper;

    @Autowired
    private ResourceDao resourceDao;


    @Override
    @Transactional
    public boolean bind(String userId, String roleId, List<String> resourceIds) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("缺少【userId】参数，不能绑定角色资源");
        }
        if (StringUtils.isBlank(roleId)) {
            throw new BusinessException("缺少【roleId】参数，不能绑定角色资源");
        }
        if (CollectionUtils.isEmpty(resourceIds)) {
            throw new BusinessException("缺少【resourceIds】参数，不会绑定角色资源");
        }

        //在绑定角色资源之前先解除绑定当前的角色的所有资源关系
        TFloodRoleResourceExample roleResourceExample = new TFloodRoleResourceExample();
        TFloodRoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(roleResourceExample);

        try {
            for (String resourceId : resourceIds) {
                TFloodRoleResource record = new TFloodRoleResource();
                record.setResourceId(resourceId);
                record.setRoleId(roleId);
                record.setCreateUserId(userId);
                record.setCreateTime(Calendar.getInstance().getTime());
                roleResourceMapper.insert(record);
            }
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean unbind(String roleId, String resourceId) {
        if (StringUtils.isBlank(roleId)) {
            throw new BusinessException("缺少【roleId】参数，不能解除绑定角色资源");
        }
        if (StringUtils.isBlank(resourceId)) {
            throw new BusinessException("缺少【resourceId】参数，不能解除绑定角色资源");
        }

        TFloodRoleResourceKey key = new TFloodRoleResourceKey();
        key.setResourceId(resourceId);
        key.setRoleId(roleId);
        try {
            roleResourceMapper.deleteByPrimaryKey(key);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;

    }

    @Override
    @Transactional
    public boolean unbindAll( String roleId) {
        if (StringUtils.isBlank(roleId)) {
            throw new BusinessException("缺少【roleId】参数，不能解除绑定角色资源");
        }
        TFloodRoleResourceExample example = new TFloodRoleResourceExample();
        TFloodRoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        try {
            roleResourceMapper.deleteByExample(example);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public List<ResourceRsp> query(String roleId) {
        if (org.apache.commons.lang.StringUtils.isBlank(roleId)) {
            throw new BusinessException("缺少【roleId】参数，不能查询角色资源");
        }
        List<ResourceRsp> result= null;

        try{
            result = resourceService.queryResourceTreeByRoleId( roleId );
        }catch (Exception e){
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }

        return result;
    }

    @Override
    public List<ResourceRsp> queryByOperId(String systemId, Integer operId) {
        if( StringUtils.isBlank(systemId) ){
            throw new ParamValueException("应用标识不能为空");
        }
        if( null == operId ){
            throw new ParamValueException("操作员不能为空");
        }
        List<ResourceRsp> result = resourceDao.findResourcesByOperId(systemId, operId);
        List<ResourceRsp> root = makeTree(result, "-1", 0);
        return root;
    }


    //递归组成功能点表的树形LIST
    public List<ResourceRsp> makeTree(List<ResourceRsp> resources, String resourceId, Integer statusFilter) {
        List<ResourceRsp> result = new ArrayList<ResourceRsp>();
        for (ResourceRsp resource : resources) {
            if( StringUtils.isNotBlank(resource.getResourceId()) ) {
                if (statusFilter != null && statusFilter.equals(1))
                    resource.setResourceStatus("1");
                if (resourceId.equals(resource.getResourceParentId())) {
                    //resourceParentId为"-1"的是根节点
                    result.add(resource);
                }
            }else{
                result.add( resource );
            }
        }
        for (ResourceRsp tmp : result) {
            if( StringUtils.isNotBlank(tmp.getResourceId())) {
                tmp.setChilds(makeTree(resources, tmp.getResourceId(), 0));
            }
        }
        return result;
    }
}
