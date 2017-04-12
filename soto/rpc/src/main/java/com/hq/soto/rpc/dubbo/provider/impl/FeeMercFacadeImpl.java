package com.hq.soto.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.model.HqRequest;
import com.hq.soto.dao.generate.TSotoFeeTemplateCoreMapper;
import com.hq.soto.rpc.dubbo.provider.FeeMercFacade;
import com.hq.soto.service.FeeMercService;
import com.hq.soto.service.entity.request.FeeMercCoreReq;
import com.hq.soto.service.entity.request.FeeMercReq;
import com.hq.soto.service.entity.response.FeeMercRsp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @包名称：com.hq.soto.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：17/2/16 下午10:50
 */
@Service(version = "1.0",interfaceName = "com.hq.soto.rpc.dubbo.provider.FeeMercFacade")
public class FeeMercFacadeImpl implements FeeMercFacade{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private TSotoFeeTemplateCoreMapper coreMapper;

    @Autowired
    private FeeMercService feeMercService;

    @Override
    public FeeMercRsp queryFeeByMchId(HqRequest hqRequest) {
        FeeMercReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<FeeMercReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        if(null==req.getCoreId()){
            throw new ParamValueException("参数【coreId】不能为空");
        }
        try {
            FeeMercRsp rsp = feeMercService.queryFeeByMchId(req.getCoreId());
            return rsp;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Boolean update(HqRequest hqRequest) {
        FeeMercCoreReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<FeeMercCoreReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = feeMercService.update(req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Boolean insert(HqRequest hqRequest) {
        FeeMercCoreReq req = JSONObject.parseObject(hqRequest.getBizContent(), new TypeReference<FeeMercCoreReq>() {
        });
        if (req == null) {
            throw new ParamValueException("参数传人错误");
        }
        try {
            Boolean flag = feeMercService.insert(req);
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
