package com.hq.diego.model.ali;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public class AliRoyaltyInfo {
    // 分账类型 卖家的分账类型，目前只支持传入ROYALTY（普通分账类型）
    private String royalty_type;

    public String getRoyalty_type() {
        return royalty_type;
    }

    public void setRoyalty_type(String royalty_type) {
        this.royalty_type = royalty_type;
    }
}
