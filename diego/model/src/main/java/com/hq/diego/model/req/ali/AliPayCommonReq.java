package com.hq.diego.model.req.ali;

import com.hq.diego.model.ali.AliSubMerchant;
import com.hq.diego.model.ali.AliExtendParams;
import com.hq.diego.model.ali.AliGoodsDetail;
import com.hq.diego.model.ali.AliRoyaltyInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public class AliPayCommonReq extends AliCommonReq {

    // 商户订单号,64个字符以内
    private String out_trade_no;
    // 非必填, 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
    private String seller_id;
    // 某些情况下非必填, 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    private BigDecimal total_amount;
    /*
        非必填
        参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
        如果该值未传入，但传入了【订单总金额】和【不可打折金额】，则该值默认为【订单总金额】-【不可打折金额】
     */
    private BigDecimal discountable_amount;
    /*
        非必填
        不参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
        如果该值未传入，但传入了【订单总金额】和【可打折金额】，则该值默认为【订单总金额】-【可打折金额】
     */
    private BigDecimal undiscountable_amount;
    // 订单标题
    private String subject;
    // 非必填, 订单描述
    private String body;
    // 非必填, 订单包含的商品列表信息
    private List<AliGoodsDetail> goods_detail;
    // 非必填, 商户操作员编号
    private String operator_id;
    // 非必填, 商户门店编号
    private String store_id;
    // 非必填, 商户机具终端编号
    private String terminal_id;
    // 非必填, 支付宝的店铺编号
    private String alipay_store_id;
    // 非必填, 业务扩展参数
    private AliExtendParams extend_params;
    // 非必填, 描述分账信息，Json格式
    private AliRoyaltyInfo royalty_info;

    /*
            非必填
            该笔订单允许的最晚付款时间，逾期将关闭交易。
            取值范围：1m～15d。m-分钟，h-小时，d-天，
                     1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
                     该参数数值不接受小数点， 如 1.5h，可转换为 90m
     */
    private String timeout_express;
    // 非必填, 二级商户信息,当前只对特殊银行机构特定场景下使用此字段
    private AliSubMerchant sub_merchant;


    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getDiscountable_amount() {
        return discountable_amount;
    }

    public void setDiscountable_amount(BigDecimal discountable_amount) {
        this.discountable_amount = discountable_amount;
    }

    public BigDecimal getUndiscountable_amount() {
        return undiscountable_amount;
    }

    public void setUndiscountable_amount(BigDecimal undiscountable_amount) {
        this.undiscountable_amount = undiscountable_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<AliGoodsDetail> getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(List<AliGoodsDetail> goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getAlipay_store_id() {
        return alipay_store_id;
    }

    public void setAlipay_store_id(String alipay_store_id) {
        this.alipay_store_id = alipay_store_id;
    }

    public AliExtendParams getExtend_params() {
        return extend_params;
    }

    public void setExtend_params(AliExtendParams extend_params) {
        this.extend_params = extend_params;
    }

    public AliRoyaltyInfo getRoyalty_info() {
        return royalty_info;
    }

    public void setRoyalty_info(AliRoyaltyInfo royalty_info) {
        this.royalty_info = royalty_info;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public AliSubMerchant getSub_merchant() {
        return sub_merchant;
    }

    public void setSub_merchant(AliSubMerchant sub_merchant) {
        this.sub_merchant = sub_merchant;
    }
}
