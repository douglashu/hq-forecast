package com.hq.flood.rpc.dubbo.provider;

import com.hq.flood.service.entity.request.OperRoleReq;
import com.hq.flood.service.entity.response.BatchOperRoleRsp;
import com.hq.flood.service.entity.response.OperRoleRsp;
import com.hq.scrati.model.HqRequest;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：17/2/9 下午10:22
 */
public interface OperRoleFacade {

    /**
     * 操作员角色绑定
     * @param hqRequest
     * @return
     */
    public Boolean bindRole(HqRequest hqRequest);

    /**
     * 操作员绑定角色模版
     *
     * @param hqRequest
     * @return
     */
    public Boolean bindRoleTemplate(HqRequest hqRequest);

    /**
     * 查询下级操作员信息
     * @param hqRequest
     * @return
     */
    public List<OperRoleRsp> queryOpersWithMchId(HqRequest hqRequest);
}
