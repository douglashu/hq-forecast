package com.hq.diego.gateway.service.core;

import com.hq.diego.gateway.service.core.common.DownloadBillService;
import com.hq.diego.model.req.DownloadBillReq;
import com.hq.diego.model.resp.DownloadBillResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 04/02/2017.
 */
@Service
public class DownloadBillDispatcher {

    @Autowired
    private ApplicationContext applicationContext;

    public DownloadBillResp downloadBill(DownloadBillReq downloadBillReq, ChannelRoute route) {
        if (downloadBillReq == null) throw new CommonException(CommonErrCode.ARGS_INVALID);
        PayChannels.PayChannel payChannel = PayChannels.fromString(downloadBillReq.getPayChannel());
        if (payChannel == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "支付渠道不正确[payChannel]");
        }
        DownloadBillService downloadBillService = (DownloadBillService)
                this.applicationContext.getBean(payChannel.getBeanPrefix() + "DownloadBillService");
        if (downloadBillService == null) {
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR
                    , "找不到对应的对账单下载服务[" + downloadBillReq.getPayChannel() + "]");
        }
        return downloadBillService.download(downloadBillReq, route);
    }

}
