package com.hq.scrati.common.util.signature;

import java.util.HashMap;
import java.util.Map;

import com.hq.scrati.common.util.signature.sign.AssemblyUtil;
import org.apache.log4j.Logger;

/**
 * @包名称：com.hq.esc.common.util.signature
 * @创建人：YYX
 * @创建时间：2016/3/18 15:50
 */
public class SignatureUtil {

    private static Logger logger = Logger.getLogger(SignatureUtil.class);

    /**
     * 加密
     *
     * @param signType
     * @param params
     * @param privatekey
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static String sign(String signType, Map<String, Object> params, String privatekey, String algorithm) throws Exception {
        String result = "";
        Map<String, Object> tempResult = AssemblyUtil.paraFilter(params);
        String input = AssemblyUtil.createLinkString(tempResult);
        logger.info("return ===" + input);
        switch (signType) {
            case "AES":
                AESSign aesSign = new AESSign(algorithm);
                result = aesSign.encrypt(input, privatekey);
                break;
            case "DES":
                DESSign desSign = new DESSign(algorithm);
                result = desSign.encrypt(input.getBytes("UTF-8"), privatekey);
                break;
            case "HMAC":
                HMACSign sign = new HMACSign(algorithm);
                result = sign.encryptHMAC(input, privatekey);
                break;
            case "RSA":
                RSASign rsaSign = new RSASign(algorithm);
                result = rsaSign.sign(input, privatekey);
                break;
            case "MD5":
                result = MD5Util.MD5Encode(input + privatekey, "UTF-8");
                break;
            default:
                break;

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
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static boolean verify(String signType, Map<String, Object> params, String sign, String signKey, String algorithm)
            throws Exception {
        boolean flag = false;
        String result = "";

        Map<String, Object> tempResult = AssemblyUtil.paraFilter(params);
        String input = AssemblyUtil.createLinkString(tempResult);
        logger.info("====" + input);
        switch (signType) {
            case "AES":
                AESSign aesSign = new AESSign(algorithm);
                result = aesSign.decrypt(sign, signKey);
                if (input.equals(result)) {
                    flag = true;
                }
                break;
            case "DES":
                DESSign desSign = new DESSign(algorithm);
                result = desSign.decrypt(sign, signKey);
                if (input.equals(result)) {
                    flag = true;
                }
                break;
            case "HMAC":
                HMACSign hmacSign = new HMACSign(algorithm);
                flag = hmacSign.checkSignature(input, signKey, sign);
                break;
            case "RSA":
                RSASign rsaSign = new RSASign(algorithm);
                flag = rsaSign.verify(input, sign, signKey);
                break;
            case "MD5":
                result = MD5Util.MD5Encode(input + signKey, "UTF-8");
                if (sign.equals(result)) {
                    flag = true;
                }
                break;
            default:
                break;

        }
        return flag;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "卡友智能");

        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("name", "卡友智能1");

        /*************************************************      HMAC     *******************************************/
        //        System.out.println(SignatureUtil.sign("HMAC",map,"1234567890","HmacSHA256"));
        //        System.out.println(SignatureUtil.verify("HMAC",map,"09c9424b34c8bf96cd2b1f482d5381010ef17a1a9e1a16a65b8717843029cb1d","1234567890","HmacSHA256"));
        //        System.out.println(SignatureUtil.verify("HMAC",map1,"09c9424b34c8bf96cd2b1f482d5381010ef17a1a9e1a16a65b8717843029cb1d","1234567890","HmacSHA256"));

        /*************************************************      DES     *******************************************/

        //        System.out.println(SignatureUtil.sign("DES",map,"1234567890","DES"));
        //        System.out.println(SignatureUtil.verify("DES", map, "71by+pPricmedrPIdFZLwYTfpVV7k0Mi", "1234567890", "DES"));
        //        System.out.println(SignatureUtil.verify("DES", map1, "71by+pPricmedrPIdFZLwYTfpVV7k0Mi", "1234567890", "DES"));

        /*************************************************      RSA     *******************************************/
        //        final String pck8 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJZjwzwIqMuU5lrWj18NBpzg3CM3dvRlDVtCkU/UrQKvZwgkNxIV+8f050tlrwGq2XL2aFtkd3FqvEbpPHARCaPd1pjX1+6Kd8TUTi7B5dicLecMhQ0KhIIBXCNnSc93YGhe5gJLn1gg2hHZvsrk0CPxpw0aopiGYkzDuu3QS/ZVAgMBAAECgYAtw4f6scvjv5jllZslWopjnM2AbfVl44ZP+g7MEzTet0+MG7EicMwgifcEa5n40OvA3xjcjtMGEWvfzEPW+mhLHN7r7r18yfkkMhKvJ8DL/Or53fDmM/miDsDmJoufHsouPXRj650w7TPN2qFXlV1x7xwvQ9r/inj4KZGVFSrk9QJBAMZKN9Uu0dC4/6pVYB6GPiVm4S/Oey1ZSFoVY2sb11VgvZqsTG3xHVuSFRp+k16MofzKnzZjARPexxx8ZGn0s48CQQDCKLFLc5OtILLEMTIDbiNBGGWSouBpoPzUJ1PEV+XZJbBKeaUGN5L3O1ewTnnAjeRNoykS+6fBruoXuMIgunXbAkEAryKGRchgkNWVGsDlcJBo+2fupObebKNbABW8BbJFcqdLT/22U7SOlX5lHU2GpA/aUwgzrzmqjf5KJqLHnb3/iQJAR5pMMCAygPGDG9lssVknxTTjGJ3alvnY48CRIuKrkFZW2lBcLJvIJkcLQqjeO8XYQU1/s1F9f28rQ45OgVViawJAeidc0gHxv0Oaww2cKTvQP3Q411QG8egFIvluNwToCuWOeLISOROdk3Xf2vRgZdZVeHsCuxVvLWd8FJSlGW9rXw==";
        //        final String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWY8M8CKjLlOZa1o9fDQac4NwjN3b0ZQ1bQpFP1K0Cr2cIJDcSFfvH9OdLZa8Bqtly9mhbZHdxarxG6TxwEQmj3daY19fuinfE1E4uweXYnC3nDIUNCoSCAVwjZ0nPd2BoXuYCS59YINoR2b7K5NAj8acNGqKYhmJMw7rt0Ev2VQIDAQAB";
        //        System.out.println(SignatureUtil.sign("RSA", map, pck8, "SHA1WithRSA"));
        //        System.out.println(SignatureUtil.verify("RSA",map,"Y5dg+BcVpxdosNeqRBqh53KfQIxiyFEaho7m0r8quxNxB0a8HdFUVVxo4765cRnSVprRu69TebQHKvGuzwv1SxlkmLguyD3ZCa03RxYCdQqImIRJ9wMVZZn/W/46nucWkSik701H1rtf1+m07DxklFYrxT6sBlaEYsmzE4lZIB0=",pubkey,"SHA1WithRSA"));
        //        System.out.println(SignatureUtil.verify("RSA",map1,"Y5dg+BcVpxdosNeqRBqh53KfQIxiyFEaho7m0r8quxNxB0a8HdFUVVxo4765cRnSVprRu69TebQHKvGuzwv1SxlkmLguyD3ZCa03RxYCdQqImIRJ9wMVZZn/W/46nucWkSik701H1rtf1+m07DxklFYrxT6sBlaEYsmzE4lZIB0=",pubkey,"SHA1WithRSA"));

        /*************************************************      MD5     *******************************************/
        System.out.println(SignatureUtil.sign("MD5", map, "1234567890", ""));
        System.out.println(SignatureUtil.verify("MD5", map, "6946376683e85c2e910199fb614e7ca8", "1234567890", ""));
        System.out.println(SignatureUtil.verify("MD5", map1, "6946376683e85c2e910199fb614e7ca8", "1234567890", ""));

        /*************************************************      AES     *******************************************/

        System.out.println("AES:"+SignatureUtil.sign("AES", map, "1234567890000000", "SHA1PRNG"));
        System.out.println("AES:"+SignatureUtil.verify("AES", map, "MpaxLLQt2Sh5C5vw/jMI9n6S1+th0sleUGWbRfN9i1M=", "1234567890000000", "SHA1PRNG"));
        System.out.println("AES:"+SignatureUtil.verify("AES", map1, "MpaxLLQt2Sh5C5vw/jMI9n6S1+th0sleUGWbRfN9i1M=", "1234567890000000", "SHA1PRNG"));

    }

}
