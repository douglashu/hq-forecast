package com.hq.flood.service;

import com.hq.flood.service.entity.response.OperRoleRsp;

import java.util.List;

/**
 * @包名称：com.hq.flood.service
 * @创建人：yyx
 * @创建时间：17/2/9 下午6:47
 */
public interface OperRoleService {

    /**
     * 操作员角色绑定
     * @param userId
     * @param operId
     * @param roles
     * @return
     */
    public Boolean bindRole(Integer userId,Integer operId,List<String> roles);

    /**
     * 操作员绑定角色模版
     *
     * @param userId
     * @param operId
     * @param coreId
     *@param templateId  @return
     */
    Boolean bindRoleTemplate(Integer userId, Integer operId, String coreId, Integer templateId);


    /**
     * 查询下级操作员信息
     * @param roleId
     * @param pCodeId
     * @param coreId
     * @return
     */
    List<OperRoleRsp> queryOpersWithMchId(String roleId, Integer pCodeId,Integer coreId);
}
