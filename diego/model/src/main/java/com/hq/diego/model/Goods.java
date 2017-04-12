package com.hq.diego.model;

/**
 * Created by zhaoyang on 10/5/16.
 */
public class Goods {

    // 商品ID
    private String id;
    // 第三方 如 微信/支付宝 商品ID
    private String thirdId;
    // 商品标题
    private String title;
    // 商品描述
    private String body;
    // 商品数量
    private Integer quantity;
    // 商品价格, 单位: 分
    private Integer price;
    // 商品类别
    private String category;
    // 商品访问URL
    private String url;
    private String attach;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }
}
