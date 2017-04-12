package com.hq.scrati.cache.constant;

/**
 * Created by zhaoyang on 17/12/2016.
 */
public class RedisKeyConfigure {

    public static final String SID_PREFIX = "SID";
    public static final String LOUIS_PREFIX = "LOUIS";
    public static final String DIEGO_PREFIX = "DIEGO";
    public static final String RUDY_PREFIX = "RUDY";
    public static final String CRASH_PREFIX = "CRASH";
    public static final String BROOKE_PREFIX = "BROOKE";
    public static final String SYLVIA_PREFIX = "SYLVIA";

    public static final String USER_CENTER_KEY = "HQ_QRCODE_TRADE_TOPIC_WITH_USER_CENTER";
    public static final String TRADE_CENTER_KEY = "HQ_QRCODE_TRADE_TOPIC_WITH_TRADE_CENTER";

    public static String SidMchInfoCacheKey(Integer coreId) {
        return Key.with(SID_PREFIX).append("MCH").append(coreId).string();
    }

    public static String SidMchOperatorsPushList(String mchId) {
        return Key.with(SID_PREFIX).append("MCH").append(mchId).append("PUSH_LIST").string();
    }

    public static String RudyMchChannelsCacheKey(String mchId) {
        return Key.with(RUDY_PREFIX).append("MCH").append(mchId).append("CHANNELS").string();
    }

    public static String RudyChannelRoutesCacheKey() {
        return Key.with(RUDY_PREFIX).append("CHANNEL_ROUTES").string();
    }

    public static String DiegoTradePollingCacheKey(String tradeId) {
        return Key.with(DIEGO_PREFIX).append("TRADE").append(tradeId).append("POLLING").string();
    }

    public static String CrashUserSessionCacheKey(String appId, String userId) {
        return Key.with(CRASH_PREFIX).append("APP").append(appId).append("USER").append(userId).string();
    }

    public static String LouisAppPushInfoCacheKey(String appId) {
        return Key.with(LOUIS_PREFIX).append("APP").append(appId).append("PUSH").string();
    }

    public static String BrookeAccessTokenCacheKey(String appId) {
        return Key.with(BROOKE_PREFIX).append("TOKEN").append(appId).string();
    }

    public static String BrookeJsapiTicketCacheKey(String appId) {
        return Key.with(BROOKE_PREFIX).append("JSAPI").append(appId).string();
    }

    public static String SylviaStoreCodeCacheKey(String id) {
        return Key.with(SYLVIA_PREFIX).append("CODE").append(id).string();
    }

    public static class Key {
        private StringBuilder builder;
        private Key() {
            this.builder = new StringBuilder();
        }
        private Key(String key) {
            this();
            this.builder.append(key);
        }
        public static Key with(String key) {
            return new Key(key);
        }
        public Key append(Object key) {
            builder.append(":").append(key);
            return this;
        }
        public String string() {
            return this.toString();
        }
        @Override
        public String toString() {
            return this.builder.toString();
        }
    }

}
