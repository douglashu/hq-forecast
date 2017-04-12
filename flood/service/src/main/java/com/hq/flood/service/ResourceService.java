package com.hq.flood.service;

import com.hq.flood.service.entity.request.ResourceListReq;
import com.hq.flood.service.entity.request.ResourceReq;
import com.hq.flood.service.entity.response.ResourceRsp;

import java.util.List;

/**
 * @包名称：com.hq.flood.service
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:09
 */
public interface ResourceService {

    /**
     * 资源新增
     *
     * @param req
     * @return
     */
    public boolean saveResourceTransactional(String userId,ResourceReq req);

    /**
     * 资源修改
     *
     * @param req
     * @return
     */
    public boolean updateResourceTransactional(String userId,ResourceReq req);

    /**
     * 资源删除
     *
     * @param userId    经办人id
     * @param resourceId 资源id
     * @return
     */
    public boolean deleteResourceTransactional(String userId, String resourceId);

    /**
     * 资源列表查询
     *
     * @param req
     * @return
     */
    public ResourceRsp queryResourceList(ResourceListReq req);

    /**
     * 资源树查询
     *
     * @param prdId   产品id
     * @return
     */
    public List<ResourceRsp> queryResourceTreeByPrdId(String prdId);


    /**
     * 资源树查询
     *
     * @param roleId   角色id
     * @return
     */
    public List<ResourceRsp> queryResourceTreeByRoleId(String roleId);

    /**
     * 查询资源信息
     * @param resourceId
     * @return
     */
    public ResourceRsp queryById(String resourceId);
}
