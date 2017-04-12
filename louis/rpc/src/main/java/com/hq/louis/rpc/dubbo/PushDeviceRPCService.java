package com.hq.louis.rpc.dubbo;

import com.hq.scrati.model.HqRequest;

/**
 * @包名称：com.hq.sid.dubbo
 * @创建人：yyx
 * @创建时间：17/2/5 下午10:36
 */
public interface PushDeviceRPCService {

    /**
     * 绑定设备
     *
     * @param hqRequest
     * @return
     */
    void bindingDevice(HqRequest hqRequest);

    /**
     * 解绑设备
     *
     * @param hqRequest
     * @return
     */
    void unbundlingdevice(HqRequest hqRequest);

}
