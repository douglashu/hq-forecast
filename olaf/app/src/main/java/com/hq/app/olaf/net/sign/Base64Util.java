package com.hq.app.olaf.net.sign;


import android.util.Base64;

/**
 *
 * @Author Zale
 * @Date 16/7/18 上午11:19
 *
 */
public class Base64Util {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key,Base64.DEFAULT);
    }
    
    /**
     * BASE64 加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key,Base64.DEFAULT);
    }
}
