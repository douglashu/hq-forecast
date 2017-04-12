package com.hq.sid.alipay.gateway.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayOfflineMaterialImageUploadRequest;
import com.alipay.api.request.AntMerchantExpandImageUploadRequest;
import com.alipay.api.response.AlipayOfflineMaterialImageUploadResponse;
import com.alipay.api.response.AntMerchantExpandImageUploadResponse;
import com.hq.scrati.common.log.Logger;

/**
 * @author Yan
 * @date 2017年1月14日
 * @version V1.0
 */
public class AlipayImageUploadTester {
	
	// 支付宝商户应用ID
    public static final String ALIPAY_APP_ID = "2016073000123613";
    // 支付宝应用私钥
    public static final String ALIPAY_APP_PKCS8_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC5gEyitPWt/SoUoa8ERH44vDDsf4s6tmN7TedNIf8aecDFsdxRw1OUSQxa+ksKP+Ve3ukRrOzwDt1ZPJPktzgW6M1mTA+usZcP38xSEK1Zn0qF4UeiC6Wvr5+lfGXNYJp42kM53m8qQwCUt7s9j4WI28v2zpJfTqYwxeDYehpXOBv2uAD/+2FtdiYlJbpLRE+DcamWT2ME/3VHOQWNptr2RVo9mwDhefX+XIKZt69dbfn2Vz1KEhb+bZKpqngNGdUJ87NrHTYEGO6VRsR5Mfbzw5PJxGxXq7ouvKvBupGRc3MLYsdZ1fan7qeU+K/dczOFxoUp3SysisYCKxuMgWG9AgMBAAECggEAVFkqf7GZJQxA3jDGzEH4wwOPm3pDbOcSRTcfPct/nCJQrZQ6glM25e5xV80H4hk7yu/BOvBOrLmVnMYW2aEWOBSTkTh0yRKnhehVl9tNd7sev9G4KM7y//fzt+PgxopusxVMDDxAgA8HbJg+47cnGRwYmplDpWK13uebXdPntlBda6NfUkxjjx3xAIGiVnrSIg9dbBlq6V0Q9oFZ1tRn/TKAW83vd6FLXYEynITUV4wGn8R2I6seYHVBegZ/da+DHrVgtWqjOw0QddE0EwQsucnFX5qNxFwBaHeWSJMzcnswFyHsrnyihlwD92HaYBoig9Gn1qvNO9LW0uIW4RT7YQKBgQD28SaX1rLa0ltUcO21KNqZ8pqHMSKN/+c9fTxfW55jKagBxL0nU1kGmsibwEZUH7LlBL2AF6ikrOrSgIdJuya75QImG9a342y+PbsLgtxM2NYkcU8p0cfR4pmNocF9ZBnSX3gATaNrpVpfxsRhkR5RE8mQXVLikoSXt5KNFM7ZewKBgQDATjQOmhj8k4g6Yp6rp1nlszCeWFVjZNQXdSkceCXBQJnK+3VcJz20gIpZFvTR0pXq2gBc+yV1yuqTxUfMa4Yplbd8CSW0k9pXfYlNByuJ0m+JyvRJMtr+C3VkXDtdHTYkvaF77Yjr4ujcXACcE/A/uPHtyUdi+7dDENv13wbAJwKBgCl0jmALIPdvPPnOA3nYI+qYOazcaMqh3TYLYik3/jzzb0rstjx114ouTeMnwia5M5US2KU6U4thpAI2pX+RNY1xgozXO5KfUVAWmOHz0/vqovh3NSz2FK99dP272/mq5lLUyBZ08ixOE//kJG+kpwxBYCMmJ5X1tuCpZqbciWUlAoGBAIyYIEMUsnPmwNA+zdadkYH4EfGSj0sNqmpEA/yGcsPpIQdf9JudLOXMnFkqCW9l0KlVHLLeArMt2XaT4zw22G2Kwgzy+xgciKMyarFgLpqiuV06foMNpUHaysbHD7ex5aXS5j8emN4slSaWNPPnN9y+sFGyuHhLo95qcwY9Okg9AoGBAJJAa9RyInNBLkfVvcJLWV1sZBHHukPSspaW2SB6xSgrxaeDTDu25SWaundno/+dep1Oqu6rGf8DkqfDzOHizZgWE6R3BeKrjkY7A952MHmp68vQOLYNF+fzFndqki1NyXqvzCyEv8J6DdgwKlfVzFx4W28UdpvtNNKa3tSZt3J3";
    
    // 支付宝应用公钥
    public static final String ALIPAY_APP_PKCS8_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuYBMorT1rf0qFKGvBER+OLww7H+LOrZje03nTSH/GnnAxbHcUcNTlEkMWvpLCj/lXt7pEazs8A7dWTyT5Lc4FujNZkwPrrGXD9/MUhCtWZ9KheFHogulr6+fpXxlzWCaeNpDOd5vKkMAlLe7PY+FiNvL9s6SX06mMMXg2HoaVzgb9rgA//thbXYmJSW6S0RPg3Gplk9jBP91RzkFjaba9kVaPZsA4Xn1/lyCmbevXW359lc9ShIW/m2Sqap4DRnVCfOzax02BBjulUbEeTH288OTycRsV6u6LryrwbqRkXNzC2LHWdX2p+6nlPiv3XMzhcaFKd0srIrGAisbjIFhvQIDAQAB";
    
    // 支付宝公钥
    public static final String ALIPAY_PKCS8_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzxE0rGOF5yQSclv8JIkvX2vKA8Ts/juqnz2ZONBr4qq8+zGIjmoA9H7ji5j8jEwI5oxEWqfybPfDGm7ONEWTQoMHe8RIvrneA5wpdnymZpUQ+fp3469vL0Z4raqM4oN0wwfly8OqsH4/yqkVhZCwYQpBFXy2L6UOoFDdUD2rToMZNuUOUH+x4BCuky5TGS9jQbazEev7XwddmGQ6CtddHtOvnyJMmRWGUbskAn8CcChoXf4LQWai2jmKFe6pG7ylNzQuz6hvJAKAvW5D8CjvfgII++1ltNEa7se6ULAeMwevCGou4rmZpc/ERDbgsysr8ajRotYANCEI1HURofdgiQIDAQAB";
    		
    // 支付宝异步通知URL
    public static final String ALIPAY_NOTIFY_URL = "http://www.hqast.com";
    
    private static final Boolean SANDBOX_MODE = Boolean.TRUE;
    public static final String GATEWAY = "https://openapi.alipay.com/gateway.do";
    public static final String SANDBOX_GATEWAY = "https://openapi.alipaydev.com/gateway.do";
    
    public static String getGatewayUrl() {
        return SANDBOX_MODE? SANDBOX_GATEWAY: GATEWAY;
    }
    
	public static void main(String[] args) {
		alipayOfflineMaterialImageUpload();
		
		antMerchantExpandImageUpload();
	}
	
	public static void alipayOfflineMaterialImageUpload(){
		AlipayClient alipayClient = new DefaultAlipayClient(getGatewayUrl(), ALIPAY_APP_ID, ALIPAY_APP_PKCS8_PRIVATE_KEY,"json","GBK",ALIPAY_PKCS8_PUBLIC_KEY,"RSA2");
		AlipayOfflineMaterialImageUploadRequest alipayRequest = new AlipayOfflineMaterialImageUploadRequest();
		alipayRequest.setImageType("jpg"); //图片类型，当前支持bmp,png,jpeg,jpg,gif 5个类型
		alipayRequest.setImageName("店内布景");
		FileItem ImageContent = new FileItem("C:\\Users\\Yan\\Desktop\\pics\\350403797666165857.jpg"); //图片文件路径
		alipayRequest.setImageContent(ImageContent);
		alipayRequest.setImagePid("2088521348003143");
		try {
			AlipayOfflineMaterialImageUploadResponse alipayResponse = alipayClient.execute(alipayRequest);
			Logger.getLogger().info(alipayResponse);
		} catch (AlipayApiException e) {
			Logger.getLogger().info(e);
		}
	}
	
	
	public static void antMerchantExpandImageUpload(){
		AlipayClient alipayClient = new DefaultAlipayClient(getGatewayUrl(), ALIPAY_APP_ID, ALIPAY_APP_PKCS8_PRIVATE_KEY,"json","GBK",ALIPAY_PKCS8_PUBLIC_KEY,"RSA2");
		AntMerchantExpandImageUploadRequest alipayRequest = new AntMerchantExpandImageUploadRequest();
		alipayRequest.setImageType("jpg"); //图片类型，当前支持bmp,png,jpeg,jpg,gif 5个类型
		FileItem ImageContent = new FileItem("C:\\Users\\Yan\\Desktop\\pics\\350403797666165857.jpg"); //图片文件路径
		alipayRequest.setImageContent(ImageContent);
		try {
			AntMerchantExpandImageUploadResponse alipayResponse = alipayClient.execute(alipayRequest);
			Logger.getLogger().info(alipayResponse);
		} catch (AlipayApiException e) {
			Logger.getLogger().info(e);
		}
	}
}
