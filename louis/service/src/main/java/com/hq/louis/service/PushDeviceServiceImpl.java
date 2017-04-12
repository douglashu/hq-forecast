package com.hq.louis.service;

import com.alibaba.fastjson.JSON;
import com.hq.louis.model.req.DeviceBindReq;
import com.hq.louis.repository.model.PushDevice;
import com.hq.louis.repository.mongo.PushDeviceRepository;
import com.hq.scrati.common.constants.OSPlatform;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.log.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

/**
 * @包名称：com.hq.sid.service.impl
 * @创建人：yyx
 * @创建时间：17/2/5 下午10:05
 */
@Service
public class PushDeviceServiceImpl implements PushDeviceService {

    private static Logger logger = Logger.getLogger();

    @Autowired
    private PushDeviceRepository pushDeviceRepository;

    @Override
    public void bindDeviceToken(DeviceBindReq req) {
        if (req == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if (StringUtils.isEmpty(req.getKey())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "key不能为空");
        }
        if (StringUtils.isEmpty(req.getGrpId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "grpId不能为空");
        }
        OSPlatform osPlatform = OSPlatform.fromString(req.getOsplatform());
        if (osPlatform == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "设备操作系统平台错误");
        }
        if (StringUtils.isEmpty(req.getDeviceToken())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "设备DeviceToken不能为空");
        }
        if (req.getExpiryTime() == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "设备DeviceToken过期时间不能为空");
        }
        if (req.getExpiryTime() <= DateTime.now().getMillis()) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "设备DeviceToken过期时间不能小于等于当前时间[expiryTime="
                    + (new DateTime(req.getExpiryTime())).toString("YYYY-MM-dd HH:mm:ss") + "]");
        }
        try {
            PushDevice pushDevice = this.pushDeviceRepository.findOne(req.getKey());
            if (pushDevice == null) {
                pushDevice = new PushDevice();
                pushDevice.setCreateTime(new Date());
                pushDevice.setUpdateTime(pushDevice.getCreateTime());
            } else {
                pushDevice.setUpdateTime(new Date());
            }
            pushDevice.setKeyId(req.getKey());
            pushDevice.setGrpId(req.getGrpId());
            pushDevice.setOsplatform(req.getOsplatform());
            pushDevice.setDeviceToken(req.getDeviceToken());
            pushDevice.setExpiryTime((new DateTime(req.getExpiryTime())).toDate());
            pushDevice.setState(Boolean.TRUE);
            this.pushDeviceRepository.save(pushDevice);
        } catch (Throwable th) {
            logger.error("<<< Bind Device Fail(bindReq=" + JSON.toJSONString(req) + ")", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }

    @Override
    public void unBindDeviceToken(String key) {
        if (StringUtils.isEmpty(key))
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用keyId不能为空[key]");
        try {
            PushDevice pushDevice = this.pushDeviceRepository.findOne(key);
            if (pushDevice != null) {
                pushDevice.setKeyId(key);
                pushDevice.setState(Boolean.FALSE);
                pushDevice.setUpdateTime(new Date());
                this.pushDeviceRepository.save(pushDevice);
            }
        } catch (Throwable th) {
            logger.error("<<< UnBind Device Fail(key=" + key + ")", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }

    @Override
    public List<PushDevice> getDeviceTokenByKeys(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "用户key列表不能为空[keys]");
        }
        try {
            return this.pushDeviceRepository.findByStateAndKeyIdInAndExpiryTimeAfter(Boolean.TRUE, keys, new Date());
        } catch (Throwable th) {
            logger.error("<<< Get Users Device Token Fail(keys=" + JSON.toJSONString(keys) + ")", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }

    @Override
    public List<PushDevice> getDeviceTokenByGrpId(String grpId) {
        if (StringUtils.isEmpty(grpId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "grpId不能为空");
        }
        try {
            return this.pushDeviceRepository.findByStateAndGrpIdAndExpiryTimeAfter(Boolean.TRUE, grpId, new Date());
        } catch (Throwable th) {
            logger.error("<<< Get Users Device Token Fail(grpId=" + grpId + ")", th);
            throw new CommonException(CommonErrCode.DB_ERROR);
        }
    }

}
