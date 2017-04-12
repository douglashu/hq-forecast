package com.hq.sid.service.impl;

import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.BeanUtils;
import com.hq.scrati.common.util.signature.MD5Util;
import com.hq.sid.dao.generate.TSidOperConfigMapper;
import com.hq.sid.entity.generate.TSidOperConfig;
import com.hq.sid.service.OperConfigService;
import com.hq.sid.service.entity.request.OperConfigReq;
import com.hq.sid.service.entity.response.OperConfigRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @包名称：com.hq.sid.service.impl
 * @创建人：yyx
 * @创建时间：17/2/16 上午12:01
 */
@Service
public class OperConfigServiceImpl implements OperConfigService{

    private static Logger logger = Logger.getLogger();

    @Autowired
    private TSidOperConfigMapper tSidOperConfigMapper;

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    @Override
    public Boolean config(OperConfigReq req, String mchId) {
        validate(req);
        try {
            TSidOperConfig config = tSidOperConfigMapper.selectByPrimaryKey(req.getOperId());
            if (config == null) {
                throw new CommonException(CommonErrCode.BUSINESS, "配置信息不存在");
            }
            if (!StringUtils.isEmpty(req.getRefundPassword())){
                config.setRefundPassword(MD5Util.MD5Encode(req.getRefundPassword(), null));
            }
            if (req.getIsPush() != null) {
                config.setIsPush(req.getIsPush());
            }
            tSidOperConfigMapper.updateByPrimaryKeySelective(config);
            String cacheKey = RedisKeyConfigure.SidMchOperatorsPushList(mchId);
            try {
                this.redisCacheDao.delete(cacheKey, ArrayList.class);
            } catch (Throwable th) {
                logger.warn("<<< Delete Redis Key(" + cacheKey + ") Fail");
            }
            return Boolean.TRUE;
        } catch (Throwable throwable) {
            logger.warn("<<< Update Operator(" + req.getOperId() + ") Config Fail", throwable);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }

    private void validate(OperConfigReq req) {
        if (req == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        if (StringUtils.isEmpty(req.getOperId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "操作员Id不能为空");
        }
    }

    @Override
    public OperConfigRsp obtainConfig(Integer operId) {
        if(operId == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "操作员Id不能为空");
        }
        try {
            TSidOperConfig config = tSidOperConfigMapper.selectByPrimaryKey(operId);
            OperConfigRsp rsp = new OperConfigRsp();
            BeanUtils.copyProperties(config, rsp, new String[] { "refundPassword" });
            return rsp;
        } catch (Throwable th) {
            logger.warn("<<< Obtain Config With Operator(" + operId + ") Fail");
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }
}
