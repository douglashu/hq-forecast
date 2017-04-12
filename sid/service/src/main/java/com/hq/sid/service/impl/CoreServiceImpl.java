package com.hq.sid.service.impl;

import com.hq.sid.service.CoreService;
import com.hq.sid.service.constant.SidConstants;
import org.springframework.stereotype.Service;

/**
 * Created by Zale on 2016/12/13.
 */
@Service
public class CoreServiceImpl implements CoreService {
    @Override
    public boolean uptCoreStatus(SidConstants.CoreStatus coreStatus) {
        return false;
    }
}
