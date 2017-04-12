package com.hq.buck.service.app;

import com.hq.buck.model.app.App;
import com.hq.buck.model.app.AppVersion;
import com.hq.buck.repository.mongo.AppRepository;
import com.hq.buck.repository.mongo.AppVersionRepository;
import com.hq.scrati.common.constants.OSPlatform;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import com.hq.scrati.common.util.MapBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyang on 09/02/2017.
 */
@Service
public class AppVersionService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AppVersionRepository versionRepository;

    public void createVersion(AppVersion appVersion) {
        if (appVersion == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID);
        }
        if (StringUtils.isEmpty(appVersion.getAppId())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用id不能为空[appId]");
        }
        if (OSPlatform.fromString(appVersion.getPlatform()) == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "平台类型错误[platform]");
        }
        if (StringUtils.isEmpty(appVersion.getVersion())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用版本号不能为空[version]");
        }
        if (StringUtils.isEmpty(appVersion.getWhatsNew())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "更新内容不能为空[whatsNew]");
        }
        if (StringUtils.isEmpty(appVersion.getUrl())) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "更新地址不能为空[url]");
        }
        if (appVersion.getForce() == null) {
            appVersion.setForce(Boolean.FALSE);
        }
        App app = this.appRepository.findOne(appVersion.getAppId());
        if (app == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID
                    , "应用信息不存在[appId=" + appVersion.getAppId() + "]");
        }
        List<AppVersion> versions = this.versionRepository.findByAppIdAndPlatformAndVersion(
                appVersion.getAppId(), appVersion.getPlatform(), appVersion.getVersion());
        if (versions != null && versions.size() > 0) {
            throw new CommonException(CommonErrCode.BUSINESS, "版本信息已存在");
        }
        appVersion.setId(null);
        appVersion.setCreateTime(DateTime.now().getMillis());
        appVersion.setUpdateTime(appVersion.getCreateTime());
        this.versionRepository.save(appVersion);
    }

    public Map<String, Object> checkVersion(
            String appId, String platform, String version) {
        if (StringUtils.isEmpty(appId)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用id不能为空[appId]");
        }
        if (OSPlatform.fromString(platform) == null) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "平台类型错误[platform]");
        }
        if (StringUtils.isEmpty(version)) {
            throw new CommonException(CommonErrCode.ARGS_INVALID, "应用版本号不能为空[version]");
        }
        Pageable pageable = new PageRequest(0, 1, new Sort(
                new Sort.Order(Sort.Direction.DESC, "version")));
        List<AppVersion> versions = this
                .versionRepository.findByAppIdAndPlatform(appId, platform, pageable);
        AppVersion appVersion = null;
        boolean newVersionFound = false;
        if (versions != null && versions.size() > 0) {
            appVersion = versions.get(0);
            int compare = appVersion.getVersion().compareTo(version);
            if(compare > 0) {
                newVersionFound = true;
            }
        }
        MapBuilder builder = MapBuilder.create("newVersionFound", newVersionFound);
        if (newVersionFound) {
            builder.add("appVersion", appVersion);
        }
        return builder.get();
    }
}
