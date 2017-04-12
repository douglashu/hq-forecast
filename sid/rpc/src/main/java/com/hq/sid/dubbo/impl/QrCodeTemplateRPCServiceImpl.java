package com.hq.sid.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.dubbo.QrCodeTemplateRPCService;
import com.hq.sid.service.QrCodeTemplateService;
import com.hq.sid.service.entity.request.QrCodeTemplateReq;
import com.hq.sid.service.entity.response.QrCodeTemplateRsp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @包名称：com.hq.sid.dubbo.impl
 * @创建人：yyx
 * @创建时间：17/2/13 下午10:19
 */
@Service(version = "1.0",interfaceName = "com.hq.sid.dubbo.QrCodeTemplateRPCService")
public class QrCodeTemplateRPCServiceImpl implements QrCodeTemplateRPCService{
    private static Logger logger = Logger.getLogger();

    @Autowired
    private QrCodeTemplateService codeTemplateService;

    @Override
    public boolean insert(HqRequest hqRequest) {
        QrCodeTemplateReq req = JSONObject.parseObject(
                hqRequest.getBizContent(), new TypeReference<QrCodeTemplateReq>() {
                });
        if (req == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        try {
            Boolean flag = codeTemplateService.insert(req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<QrCodeTemplateRsp> queryAll() {
        try {
            return codeTemplateService.queryAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
