package com.hq.sid.dubbo;

import com.github.pagehelper.PageInfo;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.AliAuthResp;
import com.hq.sid.service.entity.response.MercResp;

/**
 * Created by Zale on 2016/12/25.
 */
public interface MerchantRPCService {
    /**
     * 业务员进件接口
     *
     */
    MercResp mercRegister(HqRequest hqRequest);
    /**
     * 换取第三方应用授权token
     *
     */
    AliAuthResp exchangeAliAuthToken(HqRequest hqRequest);

    /**
     * 刷 新token
     *
     */
    AliAuthResp refreshAliAuthToken(HqRequest hqRequest);
    /**
     * 获得商户信息
     *
     */
    MercResp getMercInfo(HqRequest hqRequest);

    /**
     * 获得商户列表
     *
     */
    PageInfo<MercResp> getMercList(HqRequest hqRequest);
}
