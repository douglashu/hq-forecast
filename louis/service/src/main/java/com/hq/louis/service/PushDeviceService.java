package com.hq.louis.service;

import com.hq.louis.model.req.DeviceBindReq;
import com.hq.louis.repository.model.PushDevice;
import java.util.List;

/**
 * @包名称：com.hq.sid.service
 * @创建人：yyx
 * @创建时间：17/2/5 下午9:59
 */
public interface PushDeviceService {

    /**
     * 绑定设备
     *
     * @param req
     * @return
     */
    void bindDeviceToken(DeviceBindReq req);

    /**
     * 解绑设备
     *
     * @param key
     * @return
     */
    void unBindDeviceToken(String key);

    List<PushDevice> getDeviceTokenByKeys(List<String> keys);

    List<PushDevice> getDeviceTokenByGrpId(String grpId);

}
