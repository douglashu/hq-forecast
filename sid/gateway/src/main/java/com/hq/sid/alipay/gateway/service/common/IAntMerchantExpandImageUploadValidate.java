package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.FileItem;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：16/12/12 下午10:58
 */
public interface IAntMerchantExpandImageUploadValidate {

    /**
     * 验证isv图片上传接口参数
     * @param fileItem
     */
    public void validate(FileItem fileItem);
}
