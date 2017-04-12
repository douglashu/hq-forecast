package com.hq.crash.service.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hq.crash.model.auth.UserSession;
import com.hq.crash.service.common.CrashCommonService;
import com.hq.scrati.common.util.MapBuilder;
import com.hq.scrati.model.HqRequest;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Created by zhaoyang on 10/02/2017.
 */
@Service
public class AppService extends CrashCommonService {

    public JSONObject checkVersion(String appId, String version, UserSession userSession) {
        HqRequest request = getHqRequest(userSession);
        Map<String, Object> results = MapBuilder.create("appId", appId)
                .add("version", version).add("platform", userSession.getOsPlatform()).get();
        request.setBizContent(JSON.toJSONString(results));
        return invoke("buck.app.version.check", "1.0", request, JSONObject.class);
    }

}
