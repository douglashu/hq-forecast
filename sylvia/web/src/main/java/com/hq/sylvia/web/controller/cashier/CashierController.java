package com.hq.sylvia.web.controller.cashier;

import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.sid.service.entity.response.QrCodeRsp;
import com.hq.sylvia.model.WebContainer;
import com.hq.sylvia.model.resp.SimpleMchInfo;
import com.hq.sylvia.service.baseinfo.QrCodeService;
import org.apache.log4j.Logger;
import com.hq.sylvia.service.baseinfo.OpenIdService;
import com.hq.sylvia.service.constant.SylviaThirdConfig;
import com.hq.sylvia.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by zhaoyang on 11/01/2017.
 */
@RestController
public class CashierController extends BaseController {

    private static final Logger logger = Logger.getLogger(CashierController.class);

    private static final String DOMAIN = "http://t.hqast.com";

    private static final String WX_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    private static final String ALI_AUTH_URL = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";
    // 微信支付宝授权回调页面
    private static final String AUTH_CALLBACK_PAGE_URL = DOMAIN + "/sylvia/dist/cashier/index.html";
    // 提示页面
    private static final String ERROR_PAGE_URL = DOMAIN + "/sylvia/dist/error/index.html";

    @Autowired
    private OpenIdService openIdService;

    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 如果以后支持商家自定义公众号, 此处需要通过二维码id
     * 去查询该商户下是否有配置公众号用以替换appid
     *
     * @param qrCode
     * @param request
     * @param response
     */
    @RequestMapping(value = "/rs", method = RequestMethod.GET)
    public void roadSigns(@RequestParam(required = false, value = "qr") String qrCode,
            HttpServletRequest request, HttpServletResponse response) {
        WebContainer container = getWebContainer(request);
        if(!container.isWechat() && !container.isAlipay()) {
            // 请使用微信或支付宝中扫一扫
            redirect((ERROR_PAGE_URL + getErrorUrlString("", "请使用微信或支付宝“扫一扫”来完成付款!")), response);
            return;
        }
        if(StringUtils.isEmpty(qrCode)) {
            // URL请求参数有误
            redirect((ERROR_PAGE_URL + getErrorUrlString("", "请扫描正确的商户收款码!")), response);
            return;
        }
        StringBuilder redirectUrl = new StringBuilder();
        boolean urlIsOk = false;
        try {
            if (container.isWechat()) {
                redirectUrl.append(WX_AUTH_URL)
                        .append("?appid=").append(SylviaThirdConfig.WX_APP_ID)
                        .append("&redirect_uri=").append(
                            URLEncoder.encode((AUTH_CALLBACK_PAGE_URL + "?showwxpaytitle=1"), "UTF-8"))
                        .append("&response_type=code&scope=snsapi_base")
                        .append("&state=").append(qrCode)
                        .append("#wechat_redirect");
            } else {
                redirectUrl.append(ALI_AUTH_URL)
                        .append("?app_id=").append(SylviaThirdConfig.ALI_APP_ID)
                        .append("&redirect_uri=").append(URLEncoder.encode(AUTH_CALLBACK_PAGE_URL, "UTF-8"))
                        .append("&scope=auth_base").append("&state=").append(qrCode);
            }
            urlIsOk = true;
        } catch (UnsupportedEncodingException ex) {
            // ignore
            logger.warn("<<<<<< Encode Auth Call Back Url Fail", ex);
        }
        if(!urlIsOk) {
            redirectUrl.append(ERROR_PAGE_URL + getErrorUrlString("", ""));
            return;
        }
        redirect(redirectUrl.toString(), response);
    }

    @RequestMapping(value = "/qrcode/{id}", method = RequestMethod.GET)
    public ResponseEntity<QrCodeRsp> getStoreCode(@PathVariable String id) {
        QrCodeRsp storeCode = this.qrCodeService.getQrStoreCode(id);
        SimpleMchInfo mchInfo = new SimpleMchInfo();
        mchInfo.setMchId(storeCode.getMchId());
        mchInfo.setMchName(storeCode.getMchName());
        mchInfo.setMchShortName(storeCode.getMchShortName());
        mchInfo.setStoreName(storeCode.getStoreName());
        mchInfo.setLogo(storeCode.getLogo());
        return getJsonResp(storeCode, HttpStatus.OK);
    }

    /**
     * 如果支持多公众号, 此处需要由客户端上传appid
     *
     * @param request
     * @param authCode
     * @return
     */
    @RequestMapping(value = "/user_info", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUserInfo(
            HttpServletRequest request, @RequestParam String authCode) {
        WebContainer webContainer = getWebContainer(request);
        String openId;
        if(webContainer.isWechat()) {
            openId = this.openIdService.getWxUserOpenId(authCode);
        } else if(webContainer.isAlipay()) {
            openId = this.openIdService.getAliUserOpenId(authCode);
        } else {
            throw new CommonException(CommonErrCode.BUSINESS, "请在微信或支付宝内发起请求");
        }
        return getJsonResp(MapBuilder.create("openId", openId).get(), HttpStatus.OK);
    }

    private String getErrorUrlString(String title, String content) {
        try {
            StringBuilder builder = new StringBuilder("?");
            if (!StringUtils.isEmpty(title)) {
                builder.append("t=").append(URLEncoder.encode(title, "UTF-8"));
            }
            if (!StringUtils.isEmpty(content)) {
                builder.append("c=").append(URLEncoder.encode(content, "UTF-8"));
            }
            return builder.toString();
        } catch (Throwable th) {
            logger.warn("<<<<<< Encode Url String Fail", th);
            return "";
        }
    }

}
