package com.hq.crash.web.controller.trade;

import com.hq.crash.service.trade.SwipeCardPayService;
import com.hq.crash.service.trade.TradePollingService;
import com.hq.crash.web.controller.common.BaseController;
import com.hq.diego.model.req.OrderTradeReq;
import com.hq.diego.model.resp.OrderTradeResp;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoyang on 13/01/2017.
 */
@RestController
public class SwipeCardPayController extends BaseController {

    @Autowired
    private SwipeCardPayService payService;

    @Autowired
    private TradePollingService pollingService;

    @RequestMapping(value = "/trade_orders", method = RequestMethod.POST)
    public ResponseEntity<OrderTradeResp> swipeCard(HttpServletRequest request, @RequestBody OrderTradeReq tradeReq) {
        OrderTradeResp resp = this.payService.swipeCard(tradeReq, getUserSession(request));
        return getJsonResp(resp, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/polling/trade_orders/{id}", method = RequestMethod.GET)
    public ResponseEntity pollingTradeOrder(
            HttpServletRequest request, @PathVariable String id) {
        OrderTradeResp resp = this.pollingService.pollingTradeOrder(id, getUserSession(request));
        return getJsonResp(resp, HttpStatus.OK);
    }

}
