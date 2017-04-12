package com.hq.soto.rpc.dubbo.provider;

import com.hq.scrati.model.HqRequest;
import com.hq.soto.service.entity.response.FeeTemplateRsp;

import java.util.List;

/**
 * @包名称：com.hq.soto.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：17/2/16 下午11:29
 */
public interface FeeTemplateFacade {

    /**
     * 查询费率模版
     * @return
     */
    List<FeeTemplateRsp> queryTemplates();

    /**
     * 新增模版
     * @param hqRequest
     * @return
     */
    Boolean insert(HqRequest hqRequest);

    /**
     * 更新模版
     * @param hqRequest
     * @return
     */
    Boolean update(HqRequest hqRequest);


    /**
     * 禁用模版
     * @param hqRequest
     * @return
     */
    Boolean disable(HqRequest hqRequest);

    /**
     * 启用模版
     * @param hqRequest
     * @return
     */
    Boolean enable(HqRequest hqRequest);

}
