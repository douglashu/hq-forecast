package com.hq.diego.gateway.service.core.ali;

import com.hq.diego.gateway.service.core.common.CommonPayService;
import com.hq.diego.model.Goods;
import com.hq.diego.model.ali.AliGoodsDetail;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyang on 26/11/2016.
 */
public class AliCommonService extends CommonPayService {

    public static List<AliGoodsDetail> getGoodsDetail(List<Goods> goodsList) {
        if(goodsList == null || goodsList.size() == 0) return null;
        List<AliGoodsDetail> aliGoodsDetails = new ArrayList<>();
        for(Goods goods: goodsList) {
            AliGoodsDetail goodsDetail = new AliGoodsDetail();
            goodsDetail.setGoods_id(goods.getId());
            goodsDetail.setGoods_name(goods.getTitle());
            goodsDetail.setQuantity(goods.getQuantity());
            goodsDetail.setPrice(getPrice(goods.getPrice()));
            if(StringUtils.isNotEmpty(goods.getCategory())) {
                goodsDetail.setGoods_category(goods.getCategory());
            }
            if(StringUtils.isNotEmpty(goods.getThirdId())) {
                goodsDetail.setAlipay_goods_id(goods.getThirdId());
            }
            if(StringUtils.isNotEmpty(goods.getBody())) {
                goodsDetail.setBody(goods.getBody());
            }
            if(StringUtils.isNotEmpty(goods.getBody())) {
                goodsDetail.setShow_url(goods.getUrl());
            }
            aliGoodsDetails.add(goodsDetail);
        }
        return aliGoodsDetails;
    }


}
