package com.hq.diego.gateway.service.core.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.hq.diego.gateway.httpclient.executor.AliServiceExecutor;
import com.hq.diego.gateway.service.core.common.OrderCancelService;
import com.hq.diego.model.req.OrderCancelReq;
import com.hq.diego.model.resp.OrderCancelResp;
import com.hq.diego.model.route.ChannelRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyang on 06/12/2016.
 */
@Service("AliOrderCancelService")
public class AliOrderCancelService implements OrderCancelService {

    @Autowired
    private AliServiceExecutor aliExecutor;

    @Override
    public OrderCancelResp cancelOrder(OrderCancelReq cancelReq, ChannelRoute route) {
        Map<String, String> params = new HashMap<>();
        params.put("out_trade_no", cancelReq.getOrderId());
        params.put("trade_no", cancelReq.gettOrderId());
        OrderCancelResp cancelResp = new OrderCancelResp();
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizContent(JSON.toJSONString(params));
        AlipayTradeCancelResponse response;
        try {
            response = this.aliExecutor.execute(request, null, route.getKey());
        } catch (AlipayApiException ae) {
            cancelResp.setErrCode(ae.getErrCode());
            cancelResp.setErrCodeDes(ae.getErrMsg());
            cancelResp.setResult(CANCEL_RESULT_UNKNOW);
            return cancelResp;
        }
        String errCode = response.getCode();
        if(!StringUtils.isEmpty(response.getSubCode())) {
            errCode += ("(" + response.getSubCode() + ")");
        }
        if("Y".equals(response.getRetryFlag())) {
            cancelResp.setErrCode(errCode);
            cancelResp.setErrCodeDes(response.getSubMsg());
            cancelResp.setResult(CANCEL_RESULT_CONTINUE);
        } else {
            if ("10000".equals(response.getCode())) {
                cancelResp.setAction(response.getAction());
                cancelResp.setResult(CANCEL_RESULT_SUCCESS);
            } else {
                cancelResp.setErrCode(errCode);
                cancelResp.setErrCodeDes(response.getSubMsg());
                if ("20000".equals(response.getCode())) {
                    cancelResp.setResult(CANCEL_RESULT_UNKNOW);
                } else {
                    cancelResp.setResult(CANCEL_RESULT_FAIL);
                }
            }
        }
        return cancelResp;
    }

}
