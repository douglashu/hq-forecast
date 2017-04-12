package com.hq.crash.web.controller.trade;

import com.hq.crash.service.trade.TradeQueryService;
import com.hq.crash.web.controller.common.BaseController;
import com.hq.diego.model.req.OrderQueryReq;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by zhaoyang on 15/01/2017.
 */
@RestController
public class TradeQueryController extends BaseController {

    @Autowired
    private TradeQueryService queryService;

    @RequestMapping(value = "/trade_orders", method = RequestMethod.GET)
    public ResponseEntity<Object> getTradeOrders(
            HttpServletRequest request,
            @RequestParam(required = false) String operatorId,
            @RequestParam(required = false) String payChannel,
            @RequestParam(required = false) String tradeType,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String inTradeStates,
            @RequestParam(required = false) String notInTradeStates,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if(page != null && page <= 0) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "页数不能小于1");
        }
        if(size != null && size > 50) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "每页数据量不能超过50条");
        }
        OrderQueryReq queryReq = new OrderQueryReq();
        queryReq.setOperatorId(operatorId);
        queryReq.setPayChannel(payChannel);
        queryReq.setTradeType(tradeType);
        queryReq.setStartDate(startDate);
        queryReq.setEndDate(endDate);
        if(!StringUtils.isEmpty(inTradeStates)) {
            String[] states = inTradeStates.split(",");
            queryReq.setInTradeStates(Arrays.asList(states));
        }
        if(!StringUtils.isEmpty(notInTradeStates)) {
            String[] states = notInTradeStates.split(",");
            queryReq.setNotInTradeStates(Arrays.asList(states));
        }
        queryReq.setPage(page);
        queryReq.setSize(size);
        Object resp = this.queryService.getTradeOrders(queryReq, getUserSession(request));
        return getJsonResp(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "/trade_order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTradeOrder(
            HttpServletRequest request, @PathVariable String id,
            @RequestParam(required = false, defaultValue = "order") String type) {
        Object resp = this.queryService.getTradeOrder(id, type, getUserSession(request));
        return getJsonResp(resp, HttpStatus.OK);
    }

}
