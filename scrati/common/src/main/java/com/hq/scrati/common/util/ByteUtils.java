package com.hq.scrati.common.util;

/**
 * Created by Zale on 2016/11/28.
 */
public class ByteUtils{
    public final static String ENCODING_UTF8 = "UTF-8";
    public static String encoding = ENCODING_UTF8;

    /**
     * 在指定字节数组后追加字节数组。
     *
     * @param bySrc
     * @param byAdd
     * @return
     */
    public static byte[] addBytes(byte[] bySrc, byte[] byAdd) {
        byte[] byNew = new byte[bySrc.length + byAdd.length];
        for (int i = 0; i < bySrc.length; i++) {
            byNew[i] = bySrc[i];
        }
        int nSrclen = bySrc.length;
        for (int i = 0; i < byAdd.length; i++) {
            byNew[i + nSrclen] = byAdd[i];
        }
        return byNew;
    }
    /**
     * 左补齐
     * @param a
     * @param length
     * @param b
     * @return
     */
    public static byte[] appendRightBytes(byte[] a,int length,byte b){
        byte[] ret = getFillBytes(a, length, b);
        return byteAdd(a, ret);
    }

    /**
     * 右补齐
     * @param a
     * @param length
     * @param b
     * @return
     */
    public static byte[] appendLeftBytes(byte[] a,int length,byte b){
        byte[] ret = getFillBytes(a, length, b);
        return byteAdd(ret,a);
    }


    /**
     * 获取填充数组
     * @param a 数组
     * @param length 长度
     * @param b 补充字符
     * @return 字符数组
     */
    public static byte[] getFillBytes(byte[] a, int length, byte b) {
        int feed=length-a.length;
        if(feed<0){
            throw new RuntimeException("数组长度["+a.length+"]超过限制长度["+length+"]");
        }else if(feed == 0){
            return a;
        }
        byte [] ret = new byte[feed];
        for(int i=0; i<a.length; i++){
            ret[i] = b;
        }
        return ret;
    }



    /**
     * 合并byte数组
     * @return
     */
    public static byte[] byteAdd(byte[] a, byte[] b){
        byte[] ret = new byte[a.length+b.length];
        for(int i=0; i<a.length; i++){
            ret[i] = a[i];
        }
        for(int j=0; j<b.length; j++){
            ret[a.length+j] = b[j];
        }
        return ret;

    }

    /**
     * 截取byte数组
     * @return
     */
    public static byte[] subByte(byte[] a, int start, int end){
        byte[] ret = new byte[end-start];
        for(int i=0; i<end-start; i++){
            ret[i] = a[start+i];
        }
        return ret;
    }
    /**
     * 将字节转为MB
     * @param bytes 字节
     * @return MB
     */
    public static long bytes2Mb(long bytes){
        return bytes/1024/1024;
    }

}
