package com.hq.louis.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.hq.louis.model.req.DeviceBindReq;
import com.hq.louis.model.req.DeviceunBindReq;
import com.hq.louis.rpc.dubbo.PushDeviceRPCService;
import com.hq.louis.service.PushDeviceService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @包名称：com.hq.sid.dubbo.impl
 * @创建人：yyx
 * @创建时间：17/2/5 下午10:37e
 */
@Service(version = "1.0", interfaceClass =PushDeviceRPCService.class )
public class PushDeviceRPCServiceImpl extends DubboBaseService implements PushDeviceRPCService {

    private static Logger logger = Logger.getLogger();

    @Autowired
    private PushDeviceService pushDeviceService;

    @Override
    public void bindingDevice(HqRequest hqRequest) {
        try {
            DeviceBindReq deviceBindReq = JSON.parseObject(
                    hqRequest.getBizContent(), DeviceBindReq.class);
            this.pushDeviceService.bindDeviceToken(deviceBindReq);
        } catch (CommonException ce) {
            throw ce;
        } catch (Throwable th) {
            logger.error(th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "绑定设备失败");
        }
    }

    @Override
    public void unbundlingdevice(HqRequest hqRequest) {
        try {
            DeviceunBindReq deviceBindReq = JSON.parseObject(hqRequest.getBizContent(), DeviceunBindReq.class);
            String key = hqRequest.getAppId()+":"+hqRequest.getUserId();
            if(deviceBindReq!=null&& StringUtils.isNotBlank(deviceBindReq.getKey())){
                key = deviceBindReq.getKey();
            }
            this.pushDeviceService.unBindDeviceToken(key);
        } catch (CommonException ce) {
            throw ce;
        } catch (Throwable th) {
            logger.error(th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "解绑设备失败");
        }
    }

}
