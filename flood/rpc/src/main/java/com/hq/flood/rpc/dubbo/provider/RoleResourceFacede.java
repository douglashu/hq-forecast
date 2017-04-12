package com.hq.flood.rpc.dubbo.provider;

import com.hq.flood.service.entity.response.ResourceRsp;
import com.hq.scrati.model.HqRequest;

import java.util.List;

/**
 * @包名称：com.hq.flood.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：17/2/9 下午10:25
 */
public interface RoleResourceFacede {

    /**
     * 操作员的资源列表
     *
     * @param hqRequest
     * @return
     */
    public List<ResourceRsp> queryByOperId(HqRequest hqRequest);
}
