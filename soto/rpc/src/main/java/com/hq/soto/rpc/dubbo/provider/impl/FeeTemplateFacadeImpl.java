package com.hq.soto.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.model.HqRequest;
import com.hq.soto.rpc.dubbo.provider.FeeTemplateFacade;
import com.hq.soto.service.FeeTemplateService;
import com.hq.soto.service.entity.response.FeeTemplateRsp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @包名称：com.hq.soto.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：17/2/16 下午11:34
 */
@Service(version = "1.0",interfaceName = "com.hq.soto.rpc.dubbo.provider.FeeTemplateFacade")
public class FeeTemplateFacadeImpl implements FeeTemplateFacade{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private FeeTemplateService feeTemplateService;
    @Override
    public List<FeeTemplateRsp> queryTemplates() {
        try {
            return feeTemplateService.queryTemplates();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }

    }

    @Override
    public Boolean insert(HqRequest hqRequest) {
        return null;
    }

    @Override
    public Boolean update(HqRequest hqRequest) {
        return null;
    }

    @Override
    public Boolean disable(HqRequest hqRequest) {
        return null;
    }

    @Override
    public Boolean enable(HqRequest hqRequest) {
        return null;
    }
}
