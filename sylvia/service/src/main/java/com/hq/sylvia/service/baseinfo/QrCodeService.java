package com.hq.sylvia.service.baseinfo;

import com.hq.scrati.cache.constant.RedisKeyConfigure;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.QrCodeRsp;
import com.hq.sylvia.service.common.SylviaCommonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * Created by zhaoyang on 22/01/2017.
 */
@Service
public class QrCodeService extends SylviaCommonService {

    private static final Logger logger = Logger.getLogger(QrCodeService.class);

    @Resource(name = "JSONRedisCache")
    private RedisCacheDao redisCacheDao;

    private static final Integer STORE_CODE_EXPIRE_IN_DAYS = 3;

    public QrCodeRsp getQrStoreCode(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "二维码id不能为空[id]");
        }
        // Read store code from redis
        QrCodeRsp storeCode = null;
        String storeCodeCacheKey = RedisKeyConfigure.SylviaStoreCodeCacheKey(id);
        try {
            storeCode = this.redisCacheDao.read(storeCodeCacheKey, QrCodeRsp.class);
        } catch (Throwable th) {
            logger.warn("<<<<<< Read Store Code From Redis Fail, CodeId=" + id, th);
        }
        if (storeCode != null) return storeCode;
        storeCode = getQrCodeInfoFromServer(id);
        if (storeCode == null || StringUtils.isEmpty(storeCode.getMchId())) {
            throw new CommonException(CommonErrCode.BUSINESS, "获取收款码信息失败,请稍后再试");
        }
        if (!"S".equals(storeCode.getState())) {
            throw new CommonException(CommonErrCode.BUSINESS, "收款码已被冻结,请联系商家处理");
        }
        try {
            this.redisCacheDao.save(storeCodeCacheKey, storeCode, STORE_CODE_EXPIRE_IN_DAYS * 24 * 60 * 60L);
        } catch (Throwable th) {
            logger.warn("<<<<<< Save Store Code To Redis Fail, CodeId=" + id, th);
        }
        return storeCode;
    }

    public QrCodeRsp getQrCodeInfoFromServer(String id) {
        HqRequest request = new HqRequest();
        request.setBizContent("{ codeId: " + id + " }");
        return invoke("sid.qrcode.queryone", "1.0", request, QrCodeRsp.class);
    }

}
