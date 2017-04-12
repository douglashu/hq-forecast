
package com.hq.scrati.common.util.signature;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;

public class RSASign {

    public static void main(String[] args) {
        try {
            String s = (new RSASign()).encrypt("123456", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDd0oFLwQwMp2OnBBRWfFiLcclyzPnTMvkKMgFugKKe9znzYJz2K2NqB3vfZ5aMII4iKWut44oSfFBT7bkNORI5c46nxH6cX6zvavXhNqSIUWAhYfGQd33ksbHr/cP83rZ4S931J4O1GWzhjy5PSCiqU8AkjcQj9otSS2aoOKlUFQIDAQAB");
            System.out.println(s);
            // ZCB56XCoQn2i4oPHKnESHBOMIXI2mYZkEamencPCBgSdg832136DFs1Bip3qVcFLRNd6Hnw9eFMIvLkhnd8aAyYFGzD5DHdfc8ikD8OSYH2mBuMngh6Nw14vtIEIVP5Usff3psQprrWKVSr/cWFul6PSeeMP8Csca955GpKE8Zg=
            String ss = (new RSASign()).decrypt(s, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN3SgUvBDAynY6cE\n" +
                    "FFZ8WItxyXLM+dMy+QoyAW6Aop73OfNgnPYrY2oHe99nlowgjiIpa63jihJ8UFPt\n" +
                    "uQ05EjlzjqfEfpxfrO9q9eE2pIhRYCFh8ZB3feSxsev9w/zetnhL3fUng7UZbOGP\n" +
                    "Lk9IKKpTwCSNxCP2i1JLZqg4qVQVAgMBAAECgYBB2xpZ5EFwjE2wqyxilN4w4CJf\n" +
                    "u+07v7liRoBYT0Z4jYud7wQKrc0BEKG9LOLsTUoFaGi4ooI+hLvQfxZej6AR8vYr\n" +
                    "PXNQlsyOIHr5PRXdei+T4vZBNwrmFbThjuOy1ya7nAXhULWyyWHoJCQUYytkPNbz\n" +
                    "nxI1AaCghCMOsaO9bQJBAO9XiP06zNAuCwL8OtZVDFR9I647otpfwDv6fN2cpSKW\n" +
                    "ej7qETcazZ/6RvdB3FKNVicSpNMDJ2UX5ZLiuTgCi9sCQQDtQtB4vD0AMtFyNc7z\n" +
                    "ydNuFzYIuxIAsKjW/QGHa9sclg4lJHMAwDjdKDgfy5oIdsXPdNMiADS9DwiBXay1\n" +
                    "EhrPAkEAjFm5hD2Z4y5M2MhOPGeNHnJvnPltqz7g/IsL8+yqzZiOQRyDUdwO3pQm\n" +
                    "T1I4v9QPxEPjkbXUwSx9PGBlmtmrZwJAc6yvly2vT/l2xAgX7iPoQTRv07HhBRUc\n" +
                    "PDT+Gdgr67P9EX7D0wnkr258boQFhTrjFlIO3fTbwZEixG+3VFCPYQJBAIErOeFs\n" +
                    "FdrFG5+8PNthtTdnNsjbXn/SwGmD29L6HUGi/j08SDidfkhPNSey8qjPg7m1VzD0\n" +
                    "iTZ4nGT8x9iuOF4=", "UTF-8");
            System.out.println(ss);
        } catch (Throwable th) {
        }
    }
	
	public String  SIGN_ALGORITHMS = "SHA1WithRSA";

    String input_charset = "UTF-8";

    public String getSIGN_ALGORITHMS() {
        return SIGN_ALGORITHMS;
    }

    public void setSIGN_ALGORITHMS(String SIGN_ALGORITHMS) {
        this.SIGN_ALGORITHMS = SIGN_ALGORITHMS;
    }

    public RSASign(){}

    public RSASign(String SIGN_ALGORITHMS){
        this.SIGN_ALGORITHMS = SIGN_ALGORITHMS;
    }


    /**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 私钥
	* @return 签名值
	*/
	public String sign(String content, String privateKey)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decodeBase64(privateKey) );
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encodeBase64String(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param public_key 公钥
	* @return 布尔值
	*/
	public boolean verify(String content, String sign, String public_key)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decodeBase64(public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decodeBase64(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}


    /**
     * 公钥加密
     * @param content 明文
     * @param public_key 公钥
     * @return 解密后的字符串
     */
    public String encrypt(String content, String public_key) throws Exception {
        PublicKey pubKey = getPublicKey(public_key);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] enBytes = cipher.doFinal(content.getBytes(input_charset));
        return Base64.encodeBase64String(enBytes);
    }


	
	/**
	* 解密
	* @param content 密文
	* @param private_key 私钥
	* @param input_charset 编码格式
	* @return 解密后的字符串
	*/
	public String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

	
	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decodeBase64(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}

    /**
     * 得到公钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decodeBase64(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        return pubKey;
    }

    /*
    public static void main(String[] args) throws Exception {

        RSASign rsaSign = new RSASign();

        // demo
        final String pck8 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJZjwzwIqMuU5lrWj18NBpzg3CM3dvRlDVtCkU/UrQKvZwgkNxIV+8f050tlrwGq2XL2aFtkd3FqvEbpPHARCaPd1pjX1+6Kd8TUTi7B5dicLecMhQ0KhIIBXCNnSc93YGhe5gJLn1gg2hHZvsrk0CPxpw0aopiGYkzDuu3QS/ZVAgMBAAECgYAtw4f6scvjv5jllZslWopjnM2AbfVl44ZP+g7MEzTet0+MG7EicMwgifcEa5n40OvA3xjcjtMGEWvfzEPW+mhLHN7r7r18yfkkMhKvJ8DL/Or53fDmM/miDsDmJoufHsouPXRj650w7TPN2qFXlV1x7xwvQ9r/inj4KZGVFSrk9QJBAMZKN9Uu0dC4/6pVYB6GPiVm4S/Oey1ZSFoVY2sb11VgvZqsTG3xHVuSFRp+k16MofzKnzZjARPexxx8ZGn0s48CQQDCKLFLc5OtILLEMTIDbiNBGGWSouBpoPzUJ1PEV+XZJbBKeaUGN5L3O1ewTnnAjeRNoykS+6fBruoXuMIgunXbAkEAryKGRchgkNWVGsDlcJBo+2fupObebKNbABW8BbJFcqdLT/22U7SOlX5lHU2GpA/aUwgzrzmqjf5KJqLHnb3/iQJAR5pMMCAygPGDG9lssVknxTTjGJ3alvnY48CRIuKrkFZW2lBcLJvIJkcLQqjeO8XYQU1/s1F9f28rQ45OgVViawJAeidc0gHxv0Oaww2cKTvQP3Q411QG8egFIvluNwToCuWOeLISOROdk3Xf2vRgZdZVeHsCuxVvLWd8FJSlGW9rXw==";
        final String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWY8M8CKjLlOZa1o9fDQac4NwjN3b0ZQ1bQpFP1K0Cr2cIJDcSFfvH9OdLZa8Bqtly9mhbZHdxarxG6TxwEQmj3daY19fuinfE1E4uweXYnC3nDIUNCoSCAVwjZ0nPd2BoXuYCS59YINoR2b7K5NAj8acNGqKYhmJMw7rt0Ev2VQIDAQAB";

        // sign and validate
        // 使用私钥签名
        String sign = rsaSign.sign("hello, test test", pck8);
        // 使用公钥验证
        boolean verifyRes = rsaSign.verify("hello, test test", sign, pubkey);
        System.out.println("Verify Result " + verifyRes);

        // encrypt and decrypt
        // 使用公钥加密
        String secuirty = rsaSign.encrypt("hello, world 中国", pubkey);
        System.out.println("security is: " + secuirty);
        // 使用私钥解密
        String result  = rsaSign.decrypt(secuirty, pck8, "utf-8");
        System.out.println(result);
    }
    */
}
