package com.hq.app.olaf.net.sign;


import com.orhanobut.logger.Logger;

import java.util.Map;

/**
 * @Author Zale
 * @Date 16/7/14 下午2:14
 */
public class SignatureUtil {

    /**
     * 加密
     *
     * @param signType
     * @param params
     * @param privatekey
     * @return
     * @throws Exception
     */
    public static String sign(Algorithm signType, Map<String, Object> params, String privatekey) throws Exception {
        String result = "";
        Map<String, Object> tempResult = AssemblyUtil.paraFilter(params);
        String input = AssemblyUtil.createLinkString(tempResult);//.replace("+"," ");
        Logger.d("input==>%s",input);
        switch (signType) {
            case DES:
                DESSign desSign = new DESSign(signType.getAlgorithm());
                result = desSign.encrypt(input.getBytes("UTF-8"), privatekey);
                break;
            case HMAC:
                HMACSign sign = new HMACSign(signType.getAlgorithm());
                result = sign.encryptHMAC(input, privatekey);
                break;
            case RSA:
                RSASign rsaSign = new RSASign(signType.getAlgorithm());
                result = rsaSign.sign(input, privatekey);
                break;
            case MD5:
                result = MD5Util.MD5Encode(input + privatekey, "UTF-8");
                break;
            default:
                Logger.d("不支持的加密算法%s", signType.getAlgorithm());
        }
        return result;
    }


    /**
     * 验签
     *
     * @param signType
     * @param params
     * @param sign
     * @param signKey
     * @return
     * @throws Exception
     */
    public static boolean verify(Algorithm signType, Map<String, Object> params, String sign, String signKey) throws Exception {
        boolean flag = false;
        String result = "";

        Map<String, Object> tempResult = AssemblyUtil.paraFilter(params);
        String input = AssemblyUtil.createLinkString(tempResult);

        switch (signType) {
            case DES:
                DESSign desSign = new DESSign(signType.getAlgorithm());
                result = desSign.decrypt(sign, signKey);
                if (input.equals(result)) {
                    flag = true;
                }
                break;
            case HMAC:
                HMACSign hmacSign = new HMACSign(signType.getAlgorithm());
                flag = hmacSign.checkSignature(input, signKey, sign);
                break;
            case RSA:
                RSASign rsaSign = new RSASign(signType.getAlgorithm());
                flag = rsaSign.verify(input, sign, signKey);
                break;
            case MD5:
                result = MD5Util.MD5Encode(input + signKey, "UTF-8");
                if (sign.equals(result)) {
                    flag = true;
                }
                break;
            default:
                Logger.d("不支持的加密算法%s", signType.getAlgorithm());
        }
        return flag;
    }


}
