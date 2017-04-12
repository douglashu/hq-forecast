package com.hq.flood.service;

import com.github.pagehelper.PageInfo;
import com.hq.flood.service.entity.request.RoleReq;
import com.hq.flood.service.entity.response.BatchOperRoleRsp;
import com.hq.flood.service.entity.response.RoleRsp;

import java.util.List;

/**
 * 角色管理
 *
 * @包名称：com.hq.flood.service
 * @创建人：yyx
 * @创建时间：16/12/15 下午7:09
 */
public interface RoleService {

    /**
     * 角色新增
     *
     * @param userId //经办员ID
     * @param req
     * @return
     */
    String saveRoleTransactional(String userId, RoleReq req);

    /**
     * 角色修改
     *
     * @param userId //经办员ID
     * @param req
     * @return
     */
    boolean updateRoleTransactional(String userId, RoleReq req);

    /**
     * 角色删除
     *
     * @param userId //经办员ID
     * @param roleId  //主键id
     * @return
     */
    boolean deleteRoleTransactional(String userId, String roleId);

    /**
     * 角色查询
     *
     * @param roleId
     * @return
     */
    RoleRsp queryRoleById(String roleId);

    /**
     * 角色列表查询
     * <p>
     * @param roleName  //名称,同组不能重复
     * @param roleStatus  //状态：0正常 ,1失效
     * @param urmId  //状态：0正常 ,1失效
     * @param pRoleId  //状态：0正常 ,1失效
     * @param page //页码
     * @param pageSize  //每页行数
     * @return
     */
    PageInfo<RoleRsp> queryRoles(String roleName,String roleStatus,String urmId,String pRoleId, Integer page, Integer pageSize);

    /**
     * 初始化商户角色
     * @param userId
     * @param systemId
     * @param coreId
     * @param isAdmin
     * @return
     */
    Boolean initRoles(String userId, String systemId, Integer coreId, Boolean isAdmin);

    /**
     * 批量操作员角色
     *
     * @param systemId
     * @param operId
     * @return
     */
    List<BatchOperRoleRsp> batchRoles(String systemId, List<Integer> operId);
}
