package com.hq.sid.service;

import com.hq.sid.service.entity.request.OperConfigReq;
import com.hq.sid.service.entity.response.OperConfigRsp;

/**
 * @包名称：com.hq.sid.service
 * @创建人：yyx
 * @创建时间：17/2/16 上午12:00
 */
public interface OperConfigService {

    /**
     * 更新操作员配置信息
     * @param req
     * @return
     */
    Boolean config(OperConfigReq req, String mchId);

    /**
     * 获取操作员配置信息
     * @param id
     * @return
     */
    OperConfigRsp obtainConfig(Integer id);
}
