package com.hq.sylvia.web.controller.cashier;

import com.hq.diego.model.resp.OrderPrepayResp;
import com.hq.scrati.common.constants.trade.PayChannels;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.sylvia.model.WebContainer;
import com.hq.sylvia.model.req.PrepayOrderReq;
import com.hq.sylvia.service.trade.H5PayService;
import com.hq.sylvia.service.trade.QrCodePayService;
import com.hq.sylvia.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoyang on 19/01/2017.
 */
@RestController
public class TradePayController extends BaseController {

    @Autowired
    private H5PayService h5PayService;

    @Autowired
    private QrCodePayService qrCodePayService;

    @RequestMapping(value = "/prepay_orders", method = RequestMethod.POST)
    public ResponseEntity<OrderPrepayResp> createPrepayOrder(
            HttpServletRequest request, @RequestBody PrepayOrderReq prepayOrderReq) {
        WebContainer webContainer = getWebContainer(request);
        if(StringUtils.isEmpty(prepayOrderReq.getAttach())) prepayOrderReq.setAttach("");
        OrderPrepayResp prepayResp;
        if(webContainer.isWechat()) {
            prepayResp = this.h5PayService.createPrepayOrder(
                    prepayOrderReq, PayChannels.WEIXIN_PAY.getCode());
        } else if(webContainer.isAlipay()) {
            prepayResp = this.qrCodePayService.createPrepayOrder(
                    prepayOrderReq, PayChannels.ALI_PAY.getCode());
        } else {
            throw new CommonException(CommonErrCode.BUSINESS, "请在微信或支付宝内发起请求");
        }
        return getJsonResp(prepayResp, HttpStatus.CREATED);
    }

}
