package com.hq.crash.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.scrati.model.HqRequest;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 13/02/2017.
 */
@Service
public class ProvCityService extends CrashCommonService {

    public String[] getNames(String... ids) {
        if (ids == null || ids.length == 0) return new String[] { };
        HqRequest request = new HqRequest();
        request.setBizContent(JSON.toJSONString(ids));
        JSONObject jsonObject = invoke("elva.area.getName", "1.0", request, JSONObject.class);
        String[] results = new String[ids.length];
        for(int i=0; i<ids.length; i++) {
            results[i] = jsonObject.getString(ids[i]);
        }
        return results;
    }

}
