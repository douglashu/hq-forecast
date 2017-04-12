package com.hq.soto.service;

import com.hq.soto.service.entity.request.FeeMercCoreReq;
import com.hq.soto.service.entity.response.FeeMercRsp;

import java.util.List;

/**
 * @包名称：com.hq.flood.service
 * @创建人：yyx
 * @创建时间：17/2/16 下午10:54
 */
public interface FeeMercService {
    /**
     * 查询商户费率信息
     *
     * @param coreId
     * @return
     */
    public FeeMercRsp queryFeeByMchId(Integer coreId);

    /**
     * 更新商户费率
     * @param req
     * @return
     */
    Boolean update(FeeMercCoreReq req);


    /**
     * 更新商户费率
     * @param req
     * @return
     */
    Boolean insert(FeeMercCoreReq req);
}
