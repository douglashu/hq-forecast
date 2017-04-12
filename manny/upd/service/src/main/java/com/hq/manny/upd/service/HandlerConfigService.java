package com.hq.manny.upd.service;

import com.hq.manny.upd.entity.mongodb.HandlerConfig;
import com.hq.manny.upd.service.model.RegisterRequest;
import com.hq.manny.upd.service.model.Result;

/**
 * Created by Zale on 2016/11/23.
 */
public interface HandlerConfigService {

    Result register(RegisterRequest registerRequest,String ip);

    HandlerConfig getHandlerByType(String type);
}
