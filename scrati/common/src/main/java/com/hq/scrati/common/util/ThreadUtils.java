package com.hq.scrati.common.util;

/**
 * 线程工具类
 * @Author Zale
 * @Date 2016/11/28 下午7:20
 *
 */

public class ThreadUtils {
    /**
     * 时间休眠
     * @param time
     */
    public static void sleep(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 等待
     * @param object 等待对象
     */
    public static void wait(Object object){
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
