package com.hq.soto.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.soto.dao.generate.TSotoFeeTemplateCoreMapper;
import com.hq.soto.dao.generate.custom.FeeMercDao;
import com.hq.soto.entity.generate.TSotoFeeTemplateCore;
import com.hq.soto.entity.generate.TSotoFeeTemplateCoreExample;
import com.hq.soto.service.FeeMercService;
import com.hq.soto.service.entity.request.FeeMercCoreReq;
import com.hq.soto.service.entity.response.FeeMercRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @包名称：com.hq.soto.service.impl
 * @创建人：yyx
 * @创建时间：17/2/16 下午10:56
 */
@Service
public class FeeMercServiceImpl implements FeeMercService {

    private static Logger logger = Logger.getLogger();

    @Autowired
    private FeeMercDao feeMercDao;

    @Autowired
    private TSotoFeeTemplateCoreMapper coreMapper;
    @Override
    public FeeMercRsp queryFeeByMchId(Integer coreId) {
        if(null==coreId){
            throw new ParamValueException("参数【coreId】不能为空");
        }
        try {
            FeeMercRsp rsp = feeMercDao.queryFeeByMchId(coreId);
            return  rsp;

        } catch (Throwable th) {
            logger.error("<<<<<< Get Users Device Token Fail", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }

    @Override
    @Transactional
    public Boolean update(FeeMercCoreReq req) {
        if(null==req.getCoreId()){
            throw new ParamValueException("参数【coreId】不能为空");
        }
        if(null==req.getTemplateId()){
            throw new ParamValueException("参数【templateId】不能为空");
        }
        if(null==req.getValidDate()){
            throw new ParamValueException("参数【validDate】不能为空");
        }
        try {
            TSotoFeeTemplateCoreExample example = new TSotoFeeTemplateCoreExample();
            TSotoFeeTemplateCoreExample.Criteria criteria = example.createCriteria();
            criteria.andCoreIdEqualTo(req.getCoreId());
            criteria.andTemplateIdEqualTo(req.getTemplateId());
//            coreMapper.deleteByExample(example);

            TSotoFeeTemplateCore core = new TSotoFeeTemplateCore();
            BeanUtils.copyProperties(req,core);
//            coreMapper.insert(core);
            coreMapper.updateByExample(core,example);
            return  Boolean.TRUE;

        } catch (Throwable th) {
            logger.error("<<<<<< Get Users Device Token Fail", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }

    @Override
    public Boolean insert(FeeMercCoreReq req) {
        if(null==req.getCoreId()){
            throw new ParamValueException("参数【coreId】不能为空");
        }
        if(null==req.getTemplateId()){
            throw new ParamValueException("参数【templateId】不能为空");
        }
        if(null==req.getValidDate()){
            throw new ParamValueException("参数【validDate】不能为空");
        }
        try {
            TSotoFeeTemplateCore core = new TSotoFeeTemplateCore();
            BeanUtils.copyProperties(req,core);
            coreMapper.insert(core);
            return  Boolean.TRUE;

        } catch (Throwable th) {
            logger.error("<<<<<< Get Users Device Token Fail", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }
}
