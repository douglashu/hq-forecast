package com.hq.app.olaf.util;

/**
 * Author:  liaobin
 */
public class Const {

    /**
     * @Fields DEBUG_LOG : 是否开启调试打印
     */
    public static final boolean DEBUG_LOG = true;

    public static final class SharedPreferences {
        public static final String MERCHANT = "Merchant";
        /**
         * App列表Hashcode
         */
        public static final String APPHASHCODE = "AppHashcode";
        /**
         * 整个登录信息
         */
        public static final String ACCOUNTSESSTION = "AccountSession";
        /**
         * 令牌
         */
        public static final String ACCESSTOKEN = "accessToken";
        /**
         * 账户ID
         */
        public static final String ACCOUNTID = "accountId";

        /**
         * 账户（手机号码）
         */
        public static final String ACCOUNT = "account";

        /**
         * 创建日期
         */
        public static final String CREATTIME = "createTime";

        /**
         * 令牌失效日期
         */
        public static final String EXPIRYTIME = "expiryTime";
        /**
         * 用户信息
         */
        public static final String USER = "user";

        /**
         * 用户头像
         */
        public static final String NEW_AVATAR_FILE = "new_avata_File";

        /**
         * 地理位置信息
         */
        public static final String LOCATION = "location";

        public static final String HOME = "home";

    }

    //网络访问返回数据的格式定义
    public static String response_status = "status";
    public static String response_info = "info";
    public static String response_data = "data";

    /**
     * 热线电话
     */
    public static final String HOT_LINE = "hot_line";

    /**
     * 首次打开软件
     */
    public final static String First_loading = "First_loading";

    /**
     * 软件版本
     */
    public final static String APP_Version = "APP_Version";

    public static final String PASS_GUARD_EDIT_LICENSE = "azEydWFoMXh6OEFSY2YxeFJOeTR6dS9FTWhNTndGQnI3dy84U0pPczdZMVpUVlp5WlBNVy8yVWRrckZYR2hTR0ZoTCtuQlhuMVFSL3pGSjZYbTIxcmlxd0V3NURqajFjMnE2ckx0VGdKR2NGdEJjQWVQYjRiRGl2TGROQkkxa1dEUkpJb3hOaEplbHZrdmVPYzBiSHduY2UySnc5U3lUa1lzem9UbmJaMHNrPXsiaWQiOjAsInR5cGUiOiJwcm9kdWN0IiwicGFja2FnZSI6WyJjb20uY2FyZGluZm8uYW55cGF5Lm1lcmNoYW50Il0sImFwcGx5bmFtZSI6WyLkuIfllYblrp0iXSwicGxhdGZvcm0iOjJ9";
    public static final String PASS_GUARD_EDIT_CipherKey = "abcdefghijklmnopqrstuvwxyz123456";
    public static final int PAY_PWD_LENGTH = 6;

    /**************************************/

    //扫码自定义返回编码
    public static final int saoma_requestCode=10001;

    /**
     * 支付类型
     */
    public final static String WEI_CHART_TYPE = "type_wc";//微信
    public final static String ALI_PAY_TYPE = "type_ali";//支付宝
}
