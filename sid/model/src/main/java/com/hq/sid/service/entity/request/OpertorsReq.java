package com.hq.sid.service.entity.request;

import java.io.Serializable;
import java.util.List;

/**
 * @包名称：com.hq.sid.service.entity.request
 * @创建人：yyx
 * @创建时间：17/2/5 下午10:45
 */
public class OpertorsReq implements Serializable{

    private static final long serialVersionUID = 4410044248051522769L;
    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
