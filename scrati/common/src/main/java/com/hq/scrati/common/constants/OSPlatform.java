package com.hq.scrati.common.constants;

import org.springframework.util.StringUtils;

/**
 * Created by zhaoyang on 19/12/2016.
 */
public enum OSPlatform {

    Android(1), IOS(2);

    private int value;

    OSPlatform(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OSPlatform fromString(String osPlatform) {
        if(StringUtils.isEmpty(osPlatform)) return null;
        switch (osPlatform) {
            case "Android":
                return Android;
            case "IOS":
                return IOS;
            default:
                return null;
        }
    }

    public static boolean isValid(String osPlatform) {
        OSPlatform platform = fromString(osPlatform);
        return platform != null;
    }

}
