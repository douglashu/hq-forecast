package com.hq.sid.dubbo;

import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.QrCodeTemplateRsp;

import java.util.List;

/**
 * @包名称：com.hq.sid.dubbo
 * @创建人：yyx
 * @创建时间：17/2/13 下午10:18
 */
public interface QrCodeTemplateRPCService {

    /**
     * 新增二维码模版
     * @param hqRequest
     * @return
     */
    public boolean insert(HqRequest hqRequest);

    /**
     * 获取模版列表
     *
     * @return
     */
    public List<QrCodeTemplateRsp> queryAll();
}
