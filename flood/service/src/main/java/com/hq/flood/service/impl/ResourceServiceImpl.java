package com.hq.flood.service.impl;

import com.hq.flood.dao.custom.ResourceDao;
import com.hq.flood.dao.generate.TFloodPrdMapper;
import com.hq.flood.dao.generate.TFloodPrdResourceMapper;
import com.hq.flood.dao.generate.TFloodResourceMapper;
import com.hq.flood.entity.generate.*;
import com.hq.flood.service.ResourceService;
import com.hq.flood.service.entity.request.ResourceListReq;
import com.hq.flood.service.entity.request.ResourceReq;
import com.hq.flood.service.entity.response.ResourceRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.common.util.UUIDGenerator;
import com.hq.scrati.common.util.ValidateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @包名称：com.hq.flood.service.impl
 * @创建人：yyx
 * @创建时间：16/12/15 下午8:28
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private Logger logger = Logger.getLogger();

    @Autowired
    private TFloodResourceMapper resourceMapper;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private TFloodPrdMapper prdMapper;

    @Autowired
    private TFloodPrdResourceMapper prdResourceMapper;

    private void validateResourceNameUnique(ResourceReq req) {
        TFloodResourceExample example = new TFloodResourceExample();
        TFloodResourceExample.Criteria criteria = example.createCriteria();
        criteria.andResourceNameEqualTo(req.getResourceName());
        criteria.andResourceTypeEqualTo(req.getResourceType());
        if (!ValidateUtils.isStrEmpty(req.getResourceId())) {
            criteria.andResourceIdNotEqualTo(req.getResourceId());
        }
        long count;
        try {
            count = resourceMapper.countByExample(example);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (count > 0) {
            throw new BusinessException("资源信息已经存在");
        }
    }

    private void validateResourceReq(ResourceReq req) {
        if (StringUtils.isBlank(req.getResourceName())) {
            throw new BusinessException("缺少【resourceName】参数，不能新增资源");
        }
        if (StringUtils.isBlank(req.getSystemId())) {
            throw new BusinessException("缺少【systemId】参数，不能新增资源");
        }
        if (StringUtils.isBlank(req.getResourceCode())) {
            throw new BusinessException("缺少【resourceCode】参数，不能新增资源");
        }
        if (StringUtils.isBlank(req.getResourceType())) {
            throw new BusinessException("缺少【resourceType】参数，不能新增资源");
        }
    }

    private TFloodResource validateResource(String resourceId) {
        TFloodResource resource = null;
        try {
            resource = resourceMapper.selectByPrimaryKey(resourceId);
        } catch (Exception e) {
            Logger.getLogger().error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (null == resource) {
            throw new ParamValueException("没有对应的资源");
        }
        return resource;
    }

    /**
     * 检查所属产品id是否存在
     *
     * @param prdId
     * @return
     */
    private void validatePrdId(List<String> prdId) {
        TFloodPrdExample example = new TFloodPrdExample();
        if (prdId.size() > 0) {
            for (String prdCode : prdId) {
                example.clear();
                example.createCriteria().andPrdCodeEqualTo(prdCode);
                if (prdMapper.countByExample(example) != 1) {
                    throw new BusinessException("产品【" + prdCode + "】无效");
                }
            }
        }
    }

    @Override
    @Transactional
    public boolean saveResourceTransactional(String userId, ResourceReq req) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("缺少【userId】参数，不能新增资源");
        }
        validateResourceNameUnique(req);
        validateResourceReq(req);
        List<String> prdIds = req.getPrdId();
        validatePrdId(prdIds);

        String resourceId = UUIDGenerator.generate();
        TFloodResource resource = new TFloodResource();
        BeanUtils.copyProperties(req, resource);
        resource.setResourceId(resourceId);
        resource.setResourceStatus("0");
        if( StringUtils.isBlank( req.getResourceParentId() ) ){
            resource.setResourceParentId("-1");
        }
        try {
            resourceMapper.insert(resource);

            logger.info("插入待更新的资源【" + resource.getResourceId() + "】");
            if(CollectionUtils.isNotEmpty(prdIds)) {
                prdIds.stream().filter(c -> c != null).forEach(p -> {
                    TFloodPrdResource floodPrdResource = new TFloodPrdResource();
                    floodPrdResource.setCreateTime(Calendar.getInstance().getTime());
                    floodPrdResource.setCreateUserId(userId);
                    floodPrdResource.setPrdId(p);
                    floodPrdResource.setResourceId(resourceId);
                    prdResourceMapper.insert(floodPrdResource);
                });
            }
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean updateResourceTransactional(String userId, ResourceReq req) {
        if (ValidateUtils.isStrEmpty(req.getResourceId())) {
            throw new ParamValueException("缺少【resourceId】参数");
        }
        validateResourceReq(req);
        validateResourceNameUnique(req);
        TFloodResource resource = validateResource(req.getResourceId());

        List<String> prdIds = req.getPrdId();
        validatePrdId(req.getPrdId());

        BeanUtils.copyProperties(req, resource, new String[] { "resourcesId", "resourceStatus" });
        resource.setLastupdatetime(Calendar.getInstance().getTime());
        resource.setModifiedUser(userId);
        try {
            resourceMapper.updateByPrimaryKeySelective(resource);

            logger.info("删除待更新的资源【" + resource.getResourceId() + "】");
            TFloodPrdResourceExample example = new TFloodPrdResourceExample();
            TFloodPrdResourceExample.Criteria criteria = example.createCriteria();
            criteria.andResourceIdEqualTo(resource.getResourceId());
            prdResourceMapper.deleteByExample(example);

            logger.info("重新插入待更新的资源【" + resource.getResourceId() + "】");
            if(CollectionUtils.isNotEmpty(prdIds)) {
                prdIds.stream().filter(c -> c != null).forEach(p -> {
                    TFloodPrdResource floodPrdResource = new TFloodPrdResource();
                    floodPrdResource.setCreateTime(Calendar.getInstance().getTime());
                    floodPrdResource.setCreateUserId(userId);
                    floodPrdResource.setPrdId(p);
                    floodPrdResource.setResourceId(resource.getResourceId());
                    prdResourceMapper.insert(floodPrdResource);
                });
            }
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean deleteResourceTransactional(String userId, String resourceId) {
        TFloodResource resource = validateResource(resourceId);
        resource.setResourceStatus("1");
        resource.setLastupdatetime(Calendar.getInstance().getTime());
        resource.setModifiedUser(userId);
        try {
            resourceMapper.updateByPrimaryKeySelective(resource);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public ResourceRsp queryResourceList(ResourceListReq req) {
        return null;
    }

    @Override
    public List<ResourceRsp> queryResourceTreeByPrdId(String prdId) {
        List<TFloodResource> resources = new ArrayList<TFloodResource>();
        if (StringUtils.isEmpty(prdId)) {
            TFloodResourceExample example = new TFloodResourceExample();
            TFloodResourceExample.Criteria criteria = example.createCriteria();
            criteria.andResourceStatusEqualTo("0");
            resources = resourceMapper.selectByExample(example);
        } else {
            resources = resourceDao.findResourcesByPrdId(prdId);
        }
        List<ResourceRsp> result = new ArrayList<ResourceRsp>();
        for (TFloodResource resource : resources) {
            ResourceRsp rsp = new ResourceRsp();
            BeanUtils.copyProperties(resource, rsp);
            result.add(rsp);
        }
        List<ResourceRsp> root = makeTree(result, "-1", 1);
        return root;
    }

    public List<ResourceRsp> queryResourceTreeByRoleId(String roleId) {
        List<TFloodResource> resources = new ArrayList<TFloodResource>();
        if (StringUtils.isEmpty(roleId)) {
            TFloodResourceExample example = new TFloodResourceExample();
            TFloodResourceExample.Criteria criteria = example.createCriteria();
            criteria.andResourceStatusEqualTo("0");
            resources = resourceMapper.selectByExample(example);
        } else {
            resources = resourceDao.findResourcesByRoleId(roleId);
        }
        List<ResourceRsp> result = new ArrayList<ResourceRsp>();
        for (TFloodResource resource : resources) {
            ResourceRsp rsp = new ResourceRsp();
            BeanUtils.copyProperties(resource, rsp);
            result.add(rsp);
        }
        List<ResourceRsp> root = makeTree(result, "-1", 1);
        return root;
    }

    @Override
    public ResourceRsp queryById(String resourceId) {
        TFloodResource role = validateResource(resourceId);
        ResourceRsp rsp = new ResourceRsp();
        BeanUtils.copyProperties(role, rsp);
        return rsp;
    }

    //递归组成功能点表的树形LIST
    public List<ResourceRsp> makeTree(List<ResourceRsp> resources, String resourceId, Integer statusFilter) {
        List<ResourceRsp> result = new ArrayList<ResourceRsp>();
        for (ResourceRsp resource : resources) {
            if (statusFilter != null && statusFilter.equals(0))
                resource.setResourceStatus("0");
            if (resourceId.equals(resource.getResourceParentId())) {
                //resourceParentId为"-1"的是根节点
                result.add(resource);
            }
        }
        for (ResourceRsp tmp : result) {
            tmp.setChilds(makeTree(resources, tmp.getResourceId(), 1));
        }
        return result;
    }

}
