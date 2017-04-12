package com.hq.soto.service.impl;

import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.log.Logger;
import com.hq.soto.dao.generate.custom.FeeTemplateDao;
import com.hq.soto.service.FeeTemplateService;
import com.hq.soto.service.entity.response.FeeMercRsp;
import com.hq.soto.service.entity.response.FeeTemplateRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @包名称：com.hq.soto.service.impl
 * @创建人：yyx
 * @创建时间：17/2/16 下午11:36
 */
@Service
public class FeeTemplateServiceImpl implements FeeTemplateService {

    private static Logger logger = Logger.getLogger();

    @Autowired
    private FeeTemplateDao feeTemplateDao;

    @Override
    public List<FeeTemplateRsp> queryTemplates() {
        try {
            List<FeeTemplateRsp> rsp = feeTemplateDao.queryTemplates();
            return  rsp;

        } catch (Throwable th) {
            logger.error("<<<<<< Get Users Device Token Fail", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }
}
