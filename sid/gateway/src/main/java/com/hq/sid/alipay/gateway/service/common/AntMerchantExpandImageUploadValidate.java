package com.hq.sid.alipay.gateway.service.common;

import com.alipay.api.FileItem;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @包名称：com.hq.sid.alipay.gateway.service.common
 * @创建人：yyx
 * @创建时间：16/12/12 下午10:58
 */
@Component
public class AntMerchantExpandImageUploadValidate implements IAntMerchantExpandImageUploadValidate {

    private Logger logger = Logger.getLogger();

    @Override
    public void validate(FileItem fileItem) {
        if (null == fileItem) {
            throw new ParamValueException("【FileItem】图片内容不能为空");
        }
        try {
            if (null == fileItem.getContent()) {
                throw new ParamValueException("【image_content】图片二进制字节流不能为空");
            }
            if (fileItem.getContent().length > 5242880) {
                throw new ParamValueException("【image_content】图片二进制字节流大小不能超过5242880");
            }
        } catch (IOException e) {
            logger.error(e.fillInStackTrace());
            throw new ParamValueException("【image_content】获取图片二进制字节流异常");
        }

    }
}
