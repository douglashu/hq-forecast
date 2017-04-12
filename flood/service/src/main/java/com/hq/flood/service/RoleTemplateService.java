package com.hq.flood.service;

import com.hq.flood.service.entity.response.RoleTemplateRsp;

import java.util.List;

/**
 * @包名称：com.hq.flood.service
 * @创建人：yyx
 * @创建时间：17/2/10 下午1:48
 */
public interface RoleTemplateService {

    /**
     * 角色对应的下级角色模版列表
     *
     * @param roleId
     * @return
     */
    public List<RoleTemplateRsp> queryByRoleId(String roleId);
}
