package com.hq.flood.service;

import com.hq.flood.service.entity.response.ResourceRsp;

import java.util.List;

/**
 * 角色资源内部接口
 *
 * @包名称：com.hq.flood.service
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:09
 */
public interface RoleResourceService {
    /**
     * 角色资源绑定
     *
     * @param userId     //经办员ID
     * @param roleId      //角色主键id
     * @param resourceIds //资源主键ID拼接字符串，以逗号“,”连接
     * @return
     */
    public boolean bind(String userId, String roleId, List<String> resourceIds);

    /**
     * 角色资源解除绑定
     *
     * @param roleId     //角色主键id
     * @param resourceId //资源主键ID拼接字符串，以逗号“,”连接
     * @return
     */
    public boolean unbind(String roleId, String resourceId);

    /**
     * 角色下所有资源解除绑定
     *
     * @param roleId //角色主键id
     * @return
     */
    public boolean unbindAll(String roleId);

    /**
     * 角色权限查询
     *
     * @param roleId //角色主键id
     * @return
     */
    public List<ResourceRsp> query(String roleId);

    /**
     * 操作员的资源列表
     *
     * @param systemId
     * @param operId
     * @return
     */
    public List<ResourceRsp> queryByOperId(String systemId,Integer operId);


}
