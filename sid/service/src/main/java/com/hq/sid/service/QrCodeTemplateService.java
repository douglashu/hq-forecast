package com.hq.sid.service;

import com.hq.sid.service.entity.request.QrCodeTemplateReq;
import com.hq.sid.service.entity.response.QrCodeTemplateRsp;

import java.util.List;

/**
 * @包名称：com.hq.sid.service
 * @创建人：yyx
 * @创建时间：17/2/13 下午10:08
 */
public interface QrCodeTemplateService {

    /**
     * 新增模版
     *
     * @param req
     * @return
     */
    public boolean insert(QrCodeTemplateReq req);

    /**
     * 获取模版列表
     *
     * @return
     */
    public List<QrCodeTemplateRsp> queryAll();
}
