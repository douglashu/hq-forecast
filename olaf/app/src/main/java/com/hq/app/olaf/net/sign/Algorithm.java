package com.hq.app.olaf.net.sign;

/**
 * Created by huwentao on 16/8/14.
 */
public enum Algorithm {
    DES("DES", ""), HMAC("HMAC", "HmacSHA256"), RSA("RSA", ""), MD5("MD5", "MD5");

    private String algorithm;
    private String code;

    /**
     * @param code      算法类型
     * @param algorithm 具体算法
     */
    Algorithm(String code, String algorithm) {
        this.code = code;
        this.algorithm = algorithm;
    }

    public String getCode() {
        return code;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
