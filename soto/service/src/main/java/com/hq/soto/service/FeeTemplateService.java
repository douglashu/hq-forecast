package com.hq.soto.service;

import com.hq.soto.service.entity.response.FeeTemplateRsp;

import java.util.List;

/**
 * @包名称：com.hq.soto.service
 * @创建人：yyx
 * @创建时间：17/2/16 下午11:36
 */
public interface FeeTemplateService {

    /**
     * 查询费率模版
     * @return
     */
    List<FeeTemplateRsp> queryTemplates();
}
