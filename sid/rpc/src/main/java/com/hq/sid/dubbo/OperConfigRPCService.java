package com.hq.sid.dubbo;

import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.OperConfigRsp;

import java.util.List;

/**
 * @包名称：com.hq.sid.dubbo
 * @创建人：yyx
 * @创建时间：17/2/15 下午11:04
 */
public interface OperConfigRPCService {

    /**
     * 更新操作员配置信息
     * @param hqRequest
     * @return
     */
    boolean config(HqRequest hqRequest);

    /**
     * 获取操作员配置信息
     * @param hqRequest
     * @return
     */
    OperConfigRsp obtainConfig(HqRequest hqRequest);

    List<OperConfigRsp> getOperConfigsByMchId(HqRequest hqRequest);
}
