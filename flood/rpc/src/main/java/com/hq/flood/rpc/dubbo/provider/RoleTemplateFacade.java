package com.hq.flood.rpc.dubbo.provider;

import com.hq.flood.service.entity.response.RoleTemplateRsp;
import com.hq.scrati.model.HqRequest;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：17/2/10 下午8:50
 */
public interface RoleTemplateFacade {

    /**
     * 角色对应的下级角色模版列表
     *
     * @param hqRequest
     * @return
     */
    public List<RoleTemplateRsp> queryByRoleId(HqRequest hqRequest);
}
