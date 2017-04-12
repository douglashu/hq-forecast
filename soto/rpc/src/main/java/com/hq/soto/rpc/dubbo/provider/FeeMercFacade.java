package com.hq.soto.rpc.dubbo.provider;

import com.hq.scrati.model.HqRequest;
import com.hq.soto.service.entity.response.FeeMercRsp;

import java.util.List;

/**
 * @包名称：com.hq.soto.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：17/2/16 下午10:49
 */
public interface FeeMercFacade {

    /**
     * 查询商户费率信息
     *
     * @param hqRequest
     * @return
     */
    public FeeMercRsp queryFeeByMchId(HqRequest hqRequest);

    /**
     * 更新商户费率
     *
     * @param hqRequest
     * @return
     */
    public Boolean update(HqRequest hqRequest);


    /**
     * 新增商户费率
     *
     * @param hqRequest
     * @return
     */
    public Boolean insert(HqRequest hqRequest);
}
