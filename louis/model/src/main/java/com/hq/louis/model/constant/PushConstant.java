package com.hq.louis.model.constant;

/**
 * Created by zhaoyang on 21/12/2016.
 */
public interface PushConstant {

    interface Type {
        // 推送单个消息
        String SINGLE = "single";
        // 推送多条消息
        String MULTI = "multi";
        // 群发
        String ALL = "all";
    }

    interface UMengType {
        String UNICAST = "unicast";
        String LISTCAST = "listcast";
        String FILECAST = "filecast";
        String BROADCAST = "broadcast";
        String GROUPCAST = "groupcast";
        String CUSTOMIZEDCAST = "customizedcast";
    }

}
