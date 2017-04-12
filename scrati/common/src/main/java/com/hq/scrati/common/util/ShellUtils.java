package com.hq.scrati.common.util;

import java.io.IOException;

/**
 * Created by Zale on 2016/11/28.
 */
public class ShellUtils {
    /**
     * 执行系统命令
     * @param cmd 命令
     */
    public static void execSystemCmd(String cmd){
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
