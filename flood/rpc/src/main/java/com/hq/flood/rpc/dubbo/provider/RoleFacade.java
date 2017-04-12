package com.hq.flood.rpc.dubbo.provider;

import com.github.pagehelper.PageInfo;
import com.hq.flood.service.entity.response.BatchOperRoleRsp;
import com.hq.flood.service.entity.response.RoleRsp;
import com.hq.scrati.model.HqRequest;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：16/12/16 下午7:39
 */
public interface RoleFacade {

    /**
     * 新增角色
     *
     * @param hqRequest 公共参数 + 角色对象【ProdReq】
     * @return
     */
    public boolean insert(HqRequest hqRequest);

    /**
     * 删除角色
     *
     * @param hqRequest 公共参数 + 角色ID【ProdIdReq】
     * @return
     */
    public boolean delete(HqRequest hqRequest);

    /**
     * 修改角色
     *
     * @param hqRequest 公共参数 + 角色对象【ProdReq】
     * @return
     */
    public boolean update(HqRequest hqRequest);

    /**
     * 查询角色信息
     *
     * @param hqRequest 角色ID【ProdIdReq】
     * @return
     */
    public RoleRsp queryById(HqRequest hqRequest);

    /**
     * 根据条件查询角色分页信息
     *
     * @param hqRequest 公共参数 + 角色分页对象【ProdPageReq】->{角色名称,开始记录数,每页记录数}
     * @return
     */
    public PageInfo<RoleRsp> findRoleByCondition(HqRequest hqRequest);

    /**
     * 初始化商户角色
     *
     * @param hqRequest
     * @return
     */
    public Boolean initRoles(HqRequest hqRequest);

    /**
     * 批量操作员角色
     *
     * @param hqRequest
     * @return
     */
    public List<BatchOperRoleRsp> batchRoles(HqRequest hqRequest);
}
