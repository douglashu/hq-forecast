package com.hq.diego.model.req.wx;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhaoyang on 10/5/16.
 */
@XStreamAlias(value = "xml")
public class WxOrderPrepayReq extends WxCommonReq {

    /*
        商品描述: [Y] [128]

        商品描述交易字段格式根据不同的应用场景按照以下格式：
        （1） PC网站——传入浏览器打开的网站主页title名-实际商品名称，例如：腾讯充值中心-QQ会员充值；
        （2） 公众号——传入公众号名称-实际商品名称，例如：腾讯形象店- image-QQ公仔；
        （3） H5_JSAPI——应用在浏览器网页上的场景，传入浏览器打开的移动网页的主页title名-实际商品名称，例如：腾讯充值中心-QQ会员充值；
        （4） 线下门店——门店品牌名-城市分店名-实际商品名称，例如： image形象店-深圳腾大- QQ公仔）
        （5） APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     */
    private String body;

    /*
        商品详情: [N] [8192] 服务商必填

        使用JSON格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来
        {
            "goods_detail":
            [
                {
                    // 必填 [32] 商品的编号
                    "goods_id":"iphone6s_16G",
                    // 可选 [32] 微信支付定义的统一商品编号
                    "wxpay_goods_id":"1001",
                    // 必填 [256] 商品名称
                    "goods_name":"iPhone6s 16G",
                    // 必填 商品数量
                    "quantity":1,
                    // 必填 商品单价，单位为分
                    "price":528800,
                    // 可选 [32] 商品类目ID
                    "goods_category":"123456",
                    // 可选 [1000] 商品描述信息
                    "body":"苹果手机"
                },
                ...
            ]
        }
     */
    private String detail;

    /*
        附加数据: [N] [127]

        在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    private String attach;

    /*
        商户订单号: [Y] [32]

        商户系统内部的订单号,32个字符内、可包含字母
      */
    private String out_trade_no;

    /*
        货币代码: [N] [16]

        符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String fee_type;

    /*
        总金额: [Y]

        订单总金额，单位为分
      */
    private Integer total_fee;

    /*
        终端IP: [Y] [16]

        用户端实际IP
      */
    private String spbill_create_ip;

    /*
        交易开始时间: [Y] [14]

        订单生成时间，格式为yyyyMMddHHmmss，
        如2009年12月25日9点10分10秒表示为20091225091010
      */
    private String time_start;

    /*
        交易结束时间: [Y] [14]

        订单失效时间，格式为yyyyMMddHHmmss，
        如2009年12月27日9点10分10秒表示为20091227091010
      */
    private String time_expire;

    /*
        商品标记: [N] [32]

        代金券或立减优惠功能的参数
        微信支付代金券业务是基于微信支付，为了协助商户方便地实现营销优惠措施。
        针对部分有开发能力的商户，微信支付提供通过API接口实现运营代金券的功能
     */
    private String goods_tag;

    /*
        通知地址: [Y] [256]

        接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
     */
    private String notify_url;

    /*
        交易类型: [Y] [16]

        JSAPI -- 公众号支付 (微信浏览器内H5)
        ORDER_CODE -- 原生扫码支付
        APP -- app支付 (商户APP内支付, 商户APP集成Android, IOS SDK)

        注: MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
     */
    private String trade_type;

    /*
        指定支付方式: [N] [32]

        no_credit -- 指定不能使用信用卡支付
     */
    private String limit_pay;

    /*
        商品ID: [N] [32]

        trade_type=ORDER_CODE，此参数必传。此id为二维码中包含的商品ID，商户自行定义
     */
    private String product_id;

    /*
        用户标识: [N] [128]

        trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
     */
    private String openid;

    /*
        用户子标识: [N] [128]

        trade_type=JSAPI，此参数必传，用户在子商户appid下的唯一标识
        openid和sub_openid可以选传其中之一，如果选择传sub_openid,则必须传sub_appid
     */
    private String sub_openid;

    /*
        授权码: [N] [128]

        刷卡支付时此参数必传, 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     */
    private String auth_code;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }
}
