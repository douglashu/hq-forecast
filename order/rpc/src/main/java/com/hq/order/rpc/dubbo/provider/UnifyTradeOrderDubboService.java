package com.hq.order.rpc.dubbo.provider;

import com.hq.scrati.model.HqRequest;

/**
 * @包名称：com.hq.tony.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：17/1/24 下午5:34
 */
public interface UnifyTradeOrderDubboService {

    String unifyOrder(HqRequest hqRequest);

}
