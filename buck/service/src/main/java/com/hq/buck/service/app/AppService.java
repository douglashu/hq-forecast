package com.hq.buck.service.app;

import com.hq.buck.model.app.App;
import com.hq.buck.repository.mongo.AppRepository;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.ArrayList;

/**
 * Created by zhaoyang on 09/02/2017.
 */
@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    public void saveApp(App app) {
        if (app == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if (StringUtils.isEmpty(app.getId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用id不能为空[id]");
        }
        if (app.getId().length() != 3) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用id长度错误[id]");
        }
        if (this.appRepository.exists(app.getId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用id已存在[id]");
        }
        if (StringUtils.isEmpty(app.getName())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用名不能为空[name]");
        }
        if (StringUtils.isEmpty(app.getCategory())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用类别不能为空[category]");
        }
        if (StringUtils.isEmpty(app.getSecondCategory())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用二级类别不能为空[secondCategory]");
        }
        if (StringUtils.isEmpty(app.getKip())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用宣传语不能为空[kip]");
        }
        if (StringUtils.isEmpty(app.getIntroduce())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用介绍不能为空[introduce]");
        }
        if (StringUtils.isEmpty(app.getIconS())) {
            app.setIconS("");
        }
        if (StringUtils.isEmpty(app.getIconM())) {
            app.setIconM("");
        }
        if (StringUtils.isEmpty(app.getIconL())) {
            app.setIconL("");
        }
        if (CollectionUtils.isEmpty(app.getThumbs())) {
            app.setThumbs(new ArrayList<>());
        }
        app.setCreateTime(DateTime.now().getMillis());
        app.setUpdateTime(app.getCreateTime());
        this.appRepository.save(app);
    }

    public App getApp(String appId) {
        if (StringUtils.isEmpty(appId)) {
            throw new CommonException(
                    CommonErrCode.ARGS_INVALID, "应用id不能为空[appId]");
        }
        return this.appRepository.findOne(appId);
    }

}
