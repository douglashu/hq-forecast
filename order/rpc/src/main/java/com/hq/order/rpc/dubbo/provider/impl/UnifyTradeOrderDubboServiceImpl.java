package com.hq.order.rpc.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.order.rpc.dubbo.provider.UnifyTradeOrderDubboService;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.framework.IDGenerator;
import com.hq.scrati.model.HqRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @包名称：com.hq.tony.rpc.dubbo.provider.impl
 * @创建人：yyx
 * @创建时间：17/1/24 下午5:36
 */
@Service(interfaceName = "com.hq.order.rpc.dubbo.provider.UnifyTradeOrderDubboService", version = "1.0")
public class UnifyTradeOrderDubboServiceImpl implements UnifyTradeOrderDubboService {

    @Autowired
    private IDGenerator idGenerator;

    private static Logger logger = Logger.getLogger();

    @Override
    public String unifyOrder(HqRequest hqRequest) {
        if (StringUtils.isBlank(hqRequest.getMchId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "商户号不能为空[mchId]");
        }
        try {
            return this.idGenerator.generateOrderId(hqRequest.getMchId());
        } catch (Throwable th) {
            logger.warn("<<<<<< Unify Trade Order Fail", th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "交易订单创建失败");
        }
    }

}
