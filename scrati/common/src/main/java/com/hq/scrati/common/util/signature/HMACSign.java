package com.hq.scrati.common.util.signature;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class HMACSign {
	
	private static Logger logger=Logger.getLogger(HMACSign.class);
	
    /**
     * 定义加密方式
     * MAC算法可选以下多种算法
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    private String KEY_MAC = "HmacSHA256";


    public String getKEY_MAC() {
        return KEY_MAC;
    }

    public void setKEY_MAC(String KEY_MAC) {
        this.KEY_MAC = KEY_MAC;
    }
    /**
     * 全局数组
     */
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 构造函数
     */
    public HMACSign(){

    }

    public HMACSign(String KEY_MAC){
        this.KEY_MAC = KEY_MAC;
    }


    /**
     * 初始化HMAC密钥
     * @return
     */
    public String init() {
        SecretKey key;
        String str = "";
        try {
            KeyGenerator generator = KeyGenerator.getInstance(KEY_MAC);
            key = generator.generateKey();
            str = Base64Util.encryptBASE64(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * HMAC加密
     * @param data 需要加密的字节数组
     * @param key 密钥
     * @return 字节数组
     */
    public byte[] encryptHMAC(byte[] data, String key) {
        SecretKey secretKey;
        byte[] bytes = null;
        try {
            secretKey = new SecretKeySpec(Base64Util.decryptBASE64(key), KEY_MAC);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * HMAC加密
     * @param data 需要加密的字符串
     * @param key 密钥
     * @return 字符串
     */
    public String encryptHMAC(String data, String key) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        byte[] bytes = null;
		try {
			bytes = encryptHMAC(data.getBytes("utf-8"), key);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return byteArrayToHexString(bytes);
    }


    /**
     * HMAC加密
     * @param data 需要加密的字符串
     * @return 字符串
     */
    public String encryptHMAC(String data) {
        String key = init();
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        byte[] bytes = encryptHMAC(data.getBytes(), key);
        return byteArrayToHexString(bytes);
    }


    /**
     * 将一个字节转化成十六进制形式的字符串
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteToHexString(byte b) {
        int ret = b;
        //System.out.println("ret = " + ret);
        if (ret < 0) {
            ret += 256;
        }
        int m = ret / 16;
        int n = ret % 16;
        return hexDigits[m] + hexDigits[n];
    }

    /**
     * 转换字节数组为十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }



    public Boolean checkSignature(String data,String key,String signature) {
        String destSignature = encryptHMAC(data,key);
        logger.debug("signature is :"+destSignature);
        if (signature.equals(destSignature)) {
            return true;
        }
        return false;
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) throws Exception {
        HMACSign sign = new HMACSign();
        String key = sign.init();
        System.out.println("Mac密钥:\n" + key);
        String word = "accCode=test&accessKeyId=22152115fd694d2599c4e27003fd37e0&algorithm=HmacSHA256&appCode=test&name=kayou&requestId=123456789&serviceCode=appTest&signType=HMAC&timestamp=1458704023689&version=1.0";
        System.out.println(sign.encryptHMAC(word, "666127323a2241a7882344ab1d7cfa18"));
    }
}