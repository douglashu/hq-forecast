/*
 * Copyright (c) 2013. Kevin Lee (http://182.92.183.142/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hq.app.olaf.net.sign;

import android.text.TextUtils;

import com.hq.component.utils.MD5;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Random;


/**
 * Created with IntelliJ IDEA. User: kevin Date: 13-7-7 Time: 上午11:46
 * Email:lishu5566@gmail.com
 */
public class EncryptManager {
    //	public static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALfF3i/oPISjG19JOUCXY2U9w8tDjxw+fIahiWTcE2JwQmXA4ri2jLJbG4foMNLVVgJv/2pbknfGkqJSA3OsFnWB8f11Cr5u0Eosi4SzHgYegUWvS27FyS4M0JL9+ABCrGgbLBUnf9LD3qW0OayApsLk1dSbelciBJ6hil08SOtrAgMBAAECgYB/onhvk388q7/eDRArcTmCXkR3DwP9HNUF+HlhSIxaCRfEbhPJMFhFo4taeAQ42hxzcu/VIaZ3c73x6L4m/3VUl854WgXiqKSgullmCFtm7fPlnjmnlY2NMnoL0S0CnuACQTvjIMVmwJiULxYRBCVhTxqxsJWPhnwBCZKe23t48QJBAOX5D7Y6c8JZJAHF3fmvSs7Ipp767oOf+7GRyZFQJ3o+eipnnK4rM7EfX9ebHCp3MQoT/1Do7NPpZDPE9/XYf9kCQQDMkkSZJYSg/BOhLd3sHbTxV1uLLNasmXm3j4nYrpjaO3kvU1BXT92tUTIlHWbeIi0AwqZcXKAOV0xT3hsygT7jAkA4EjjVenz89tUDpaXQmf/IWT3e51m+OASbL+uQhZWKha8tpaObB6eL2RV6MTR12ifXyDZpGNGdfXtT8ANxKr9JAkB9Sg/tY8cI+ZnkGz1RwRfyv7f3UyzfZNfhDm40YSqIbehYjcQk1WtFHPeDN7Cq12+Miapt4uS8I8dBjkRF+FZVAkEAmNa8YeJvT/JvdMpkZmfJJ0UlsNFLPZl5q7e88+utDbkRzDb4/jZzvY49U3qk4RNOVbfs27ywU11cIuzdxM5ldg==";
    // public static final String publicKey =
    // "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDW0bfUQVUouffhqN6GX9GtGcSBVpPe/MJAtZOln/+jhCejzAcgNPVtcJY3agag6LW/CPcGwsD01U9dY/zkf6cAFU0az7AvMV90M7gGWioIUwEjvdGu7qOfCLFKBBcQ3Umt4fyuHLspxB1cxwcUf1HvJ1ngzpvkeybp+8XC9qbrzQIDAQAB";
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx8X1ZwAyJfC2T5WTP/RHE6YHB+1gY+EFKfxJcwygBBvGM6TEHVmERRzfjPN6TZY+m4X5f7T/OvntYmhuh4cW/KyyupepBsX2TZlipqSFiGFOIhOH/iErlnKIL03aq9rt35MpixG5MiU0LB0grGxD/wNbi1EKJj3IBjMgnHOg3cwIDAQAB";
    private String pingKey = null;
    private String workKey = null;
    private String mobKey = null;

    private EncryptManager() {

    }

    private final static class SingleHolde {
        private final static EncryptManager instance = new EncryptManager();
    }

    public static EncryptManager getInstance() {
        return SingleHolde.instance;
    }

    /**
     * 初始化加密管理者， 1.生成加密秘钥 2.生成验签秘钥
     *
     * @throws Exception
     */
    public void initEncrypt() {
        pingKey = getRandomString(24);
        workKey = getRandomString(24);
        setMobKey(pingKey, workKey);
    }

    /**
     * 随机获取字符串长度
     *
     * @param length 字符串长度
     * @return
     */
    private static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        Random random = new Random();
        Random random = new SecureRandom();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(base.length());
            sb.append(base.charAt(num));
        }
        return sb.toString();
    }

    /**
     * 返回加密秘钥
     *
     * @return
     */
    public String getPingKey() {
        return pingKey;
    }

    /**
     * 返回验签秘钥
     *
     * @return
     */
    public String getWorkKey() {
        return workKey;
    }

    /**
     * 默默的设置RAS加密
     *
     * @param pingKey
     * @param workKey
     * @throws Exception
     */
    private void setMobKey(String pingKey, String workKey) {
        mobKey = encryptKey(pingKey + workKey).replace("\n","");
    }

    /**
     * 返回加密的秘钥保护字段
     *
     * @return
     */
    public String generateMobKey() {
        return mobKey;
    }

    public static byte[] MD5Bytes(String s) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param desKey
     * @return
     * @throws Exception
     */
    public String encryptKey(String desKey) {
        String enStr = "";
        try {

            // 构造X509EncodedKeySpec对象
            byte[] keyBytes = Base64Util.decryptBASE64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactor = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactor
                    .generatePublic(keySpec);
            byte[] wl9ebankSignBin = desKey.getBytes();
            byte[] plainSignBin = RSAUtil
                    .encrypt(rsaPublicKey, wl9ebankSignBin);
            enStr = Base64Util.encryptBASE64(plainSignBin);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return enStr;
    }

    /**
     * 移动解密验签
     *
     * @param paramNames
     * @param map
     * @return
     * @throws Exception
     */
    public boolean verifySignature(String[] paramNames, Map<String, Object> map) {
        try {

            String signHex = (String) map.get("sign");
            if (TextUtils.isEmpty(signHex)) {
                return false;
            }
            // 拼接签名原串
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < paramNames.length; i++) {
                String v = (String) map.get(paramNames[i]);
                if (null != v) {
                    sb.append(v);
                }
            }
            // 计算摘要
            String calSignHex = MD5.encryptMD5(sb.toString());
            sb = null;
            // 解密sign
            String decSign = DES3Encrypt.decryptMode(getWorkKey(), signHex);
            // 比较
            if (!calSignHex.equalsIgnoreCase(decSign)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 加密签名
     *
     * @param reqSign 签名字符串
     * @return
     */
    public String generateSignature(String reqSign) {
        return DES3Encrypt.encryptMode(getWorkKey(), MD5.encryptMD5(reqSign).toUpperCase()).replace("\n","");
    }

    /**
     * 字段加密
     *
     * @param str
     * @return
     */
    public String encrypt3DES(String str) {
        return DES3Encrypt.encryptMode(getPingKey(), str).replace("\n","");
    }

    /**
     * 字段解密
     *
     * @param str
     * @return
     */
    public String decrypt3DES(String str) {
        return DES3Encrypt.decryptMode(getPingKey(), str);
    }
}
