package com.hq.sylvia.service.baseinfo;

import com.alibaba.fastjson.JSON;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import com.hq.sid.service.entity.response.MercResp;
import com.hq.sylvia.service.common.SylviaCommonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 16/01/2017.
 */
@Service
public class MerchantService extends SylviaCommonService {

    private static final Logger logger = Logger.getLogger(MerchantService.class);

    public MercResp getMerchantInfo(Integer coreId) {
        if(coreId == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "内部商户号不能为空[coreId]");
        }
        HqRequest request = new HqRequest();
        MapBuilder builder = MapBuilder.create("coreId", coreId);
        request.setBizContent(JSON.toJSONString(builder.get()));
        return invoke("sid.merc.getinfo", "1.0", request, MercResp.class);
    }

}
