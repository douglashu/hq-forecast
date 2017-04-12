package com.hq.flood.rpc.dubbo.provider;

import com.hq.flood.service.entity.response.ResourceRsp;
import com.hq.scrati.model.HqRequest;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：16/12/16 下午7:39
 */
public interface ResourceFacade {
    /**
     * 新增资源
     *
     * @param hqRequest 公共参数 + 资源对象【ResoucesReq】
     * @return
     */
    public boolean insert(HqRequest hqRequest);

    /**
     * 删除资源
     *
     * @param hqRequest 公共参数 + 资源ID【ResoucesIdReq】
     * @return
     */
    public boolean delete(HqRequest hqRequest);

    /**
     * 修改资源
     *
     * @param hqRequest 公共参数 + 资源对象【ResoucesReq】
     * @return
     */
    public boolean update(HqRequest hqRequest);

    /**
     * 查询资源信息
     *
     * @param hqRequest 资源ID【ResoucesIdReq】
     * @return
     */
    public ResourceRsp queryById(HqRequest hqRequest);


    /**
     * 根据条件查询资源信息
     *
     * @param hqRequest 公共参数 + 资源分页对象【PrdResourcesReq】
     * @return
     */
    public List<ResourceRsp> findResoucesByPrdCode(HqRequest hqRequest);


    /**
     * 根据条件查询资源信息
     *
     * @param hqRequest 公共参数 + 资源分页对象【RoleResourcesReq】
     * @return
     */
    public List<ResourceRsp> findResoucesByRoleId(HqRequest hqRequest);
}
