package com.hq.order.starter;

import java.util.logging.Logger;

/**
 * Created by Zale on 16/10/09.
 */
public class OrderAppStarter {

    private static final Logger LOGGER = Logger.getLogger(OrderAppStarter.class.getName());

    public static void main(String[] args) {
        com.alibaba.dubbo.container.Main.main(args);
    }
}
