package com.hq.soto.dao.generate.custom;

import com.hq.soto.service.entity.response.FeeTemplateRsp;

import java.util.List;

/**
 * @包名称：com.hq.soto.dao.generate.custom
 * @创建人：yyx
 * @创建时间：17/2/16 下午11:37
 */
public interface FeeTemplateDao {

    public List<FeeTemplateRsp> queryTemplates();
}
