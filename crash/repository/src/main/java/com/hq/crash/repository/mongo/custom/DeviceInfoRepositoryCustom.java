package com.hq.crash.repository.mongo.custom;

/**
 * Created by zhaoyang on 19/12/2016.
 */
public interface DeviceInfoRepositoryCustom {

    void updateDeviceToken(String appId, String userId, String osPlatform, String deviceToken);

    void markDeviceTokenInvalid(String appId, String userId);

    boolean exists(String appId, String userId);

}
