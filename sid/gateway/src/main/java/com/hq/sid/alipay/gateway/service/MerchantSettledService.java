package com.hq.sid.alipay.gateway.service;

import com.alipay.api.FileItem;
import com.alipay.api.domain.AntMerchantExpandEnterpriseApplyModel;
import com.alipay.api.domain.AntMerchantExpandMapplyorderQueryModel;
import com.alipay.api.domain.AntMerchantExpandPersonalApplyModel;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.*;

/**
 * @包名称：com.hq.sid.alipay.gateway
 * @创建人：yyx
 * @创建时间：16/12/12 下午9:50
 */
public interface MerchantSettledService {

    /**
     * 使用auth_code换取接口access_token及用户userId(接口名称：alipay.system.oauth.token)
     * @param request
     * @return
     */
    public AlipayOpenAuthTokenAppResponse  alipayOpenAuthToken(AlipayOpenAuthTokenAppRequest request);

    /**
     * 商户入驻单查询接口 (ant.merchant.expand.mapplyorder.query)
     *
     * @param model
     * @return
     */
    public AntMerchantExpandMapplyorderQueryResponse antMerchantExpandMapplyorderQuery(AntMerchantExpandMapplyorderQueryModel model);

    /**
     * 个体工商户入驻接口 (ant.merchant.expand.personal.apply)
     * @param model
     * @return
     */
    public AntMerchantExpandPersonalApplyResponse antMerchantExpandPersonalApply(AntMerchantExpandPersonalApplyModel model);

    /**
     * 企业级商户入驻接口 (ant.merchant.expand.enterprise.apply)
     * @param model
     * @return
     */
    public AntMerchantExpandEnterpriseApplyResponse antMerchantExpandEnterpriseApply(AntMerchantExpandEnterpriseApplyModel model);

    /**
     * isv图片上传接口 (ant.merchant.expand.image.upload)
     * @param fileItem
     * @return
     */
    public AntMerchantExpandImageUploadResponse antMerchantExpandImageUpload(FileItem fileItem);
}
