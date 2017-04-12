/* Copyright(c) 2010- Siro-info Co.(http://www.siro-inro.com)
 * All rights reserved.
 * 
 */
package com.hq.scrati.common.util;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/**
 * Class Declaration:
 *
 * @version v1.0
 * @since StringUtils.java v1.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
    public final static int DIRECTION_LEFT = 0;
    public final static int DIRECTION_RIGHT = 1;
    public final static int DIRECTION_ALL = 2;
    public final static int MAX_OUTPUT_LENGTH = 49152;
    public final static int DFT_LINELEN = 36;
    public final static String ENCODING_UTF8 = "UTF-8";
    public final static String ENCODING_GBK = "GBK";
    public static String encoding = ENCODING_UTF8;

    /**
     * 以特定的分割符分割字符串，取分割后的所有分节。
     *
     * @param source
     * @param delim
     * @return
     */
    public static String[] splitString(String source, String delim) {
        StringTokenizer tokenizer = new StringTokenizer(source, delim);
        int count = tokenizer.countTokens();
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = tokenizer.nextToken();
        }
        return result;
    }


    /**
     * 判断字符串是否是一合法的数字。
     *
     * @param source
     * @param scope  scope是否在整数范围内，true：整数范围内；false：实数范围内
     * @return
     */
    public static boolean isNum(String source, boolean scope) {
        boolean bTmp = false;
        try {
            if (scope) {
                for (int i = 0; i < source.length(); i++) {
                    if (Character.isLetter(source.charAt(i))) {
                        Integer.parseInt(source, 16);
                        bTmp = true;
                        break;
                    }
                }
                if (!bTmp) {
                    Integer.parseInt(source);
                }
            } else {
                Float.parseFloat(source);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将字节数组转换成十六进制字符串。
     *
     * @param bSrc
     * @param offset
     * @param len
     * @return
     */
    public static String byteArry2Hex(byte[] bSrc, int offset, int len) {
        String sTmp;
        String result = "";
        byte[] byNew = new byte[len];
        for (int i = 0; i < len; i++) {
            byNew[i] = bSrc[offset + i];
        }
        for (int i = 0; i < byNew.length; i++) {
            sTmp = Integer.toHexString(byNew[i] & 0xff);
            sTmp = fillChar(sTmp, '0', 2, true) + " ";
            result = result + sTmp;
        }
        return result;
    }

    /**
     * 整型转换为中文字符。
     *
     * @param nSrc
     * @return
     */
    public static String int2Chinese(int nSrc) {
        String sInt = Integer.toHexString(nSrc);
        byte[] byCh = new byte[2];
        for (int i = 0; i < byCh.length; i++) {
            String sTmp = sInt.substring(i * 2, (i + 1) * 2);
            byCh[i] = (byte) Integer.parseInt(sTmp, 16);
        }
        return new String(byCh);
    }

    /**
     * 取得对象的字符串形式
     *
     * @param object
     * @return
     */
    public static String object2String(Object object) {
        if (object == null)
            return null;
        if (object instanceof byte[])
            // TODO: 是否指定编码方式？？？
            return new String((byte[]) object);
        if (object instanceof String)
            return (String) object;
        else
            return object.toString();
    }

    /**
     * 去掉字符串指定方向上从端点开始的指定字符。
     *
     * @param bySrc
     * @param ch
     * @param nPos  nPos:方向：LEFT:左边；RIGTH:右边；ALL：两边。
     * @return
     */
    private static byte[] trim(byte[] bySrc, char ch, int nPos) {
        int nLen = bySrc.length;
        byte[] byRst = new byte[nLen];
        if (nPos == DIRECTION_LEFT) {
            int i = 0;
            for (; i < nLen; i++) {
                if (bySrc[i] != (byte) ch)
                    break;
            }
            for (int j = i; j < nLen; j++) {
                byRst[j - i] = bySrc[j];
            }
        }
        if (nPos == DIRECTION_RIGHT) {
            int i = nLen - 1;
            for (; i >= 0; i--) {
                if (bySrc[i] != (byte) ch)
                    break;
            }
            int nRight = i + 1;
            for (int j = 0; j < nRight; j++) {
                byRst[j] = bySrc[j];
            }
        }
        if (nPos == DIRECTION_ALL) {
            int i = 0;
            for (; i < nLen; i++) {
                if (bySrc[i] != (byte) ch)
                    break;
            }
            int nLeft = i;
            i = nLen - 1;
            for (; i >= 0; i--) {
                if (bySrc[i] != (byte) ch)
                    break;
            }
            int nRight = i + 1;
            for (int j = nLeft; j < nRight; j++) {
                byRst[j - nLeft] = bySrc[j];
            }
        }
        return trimnull(byRst);
    }

    /**
     * 去掉字符串指定方向上从端点开始的指定字符。
     *
     * @param sSrc
     * @param ch
     * @param nPos nPos:方向：LEFT:左边；RIGTH:右边；ALL：两边。
     * @return
     */
    public static String trim(String sSrc, char ch, int nPos) {
        if (sSrc == null || sSrc.equals(""))
            return sSrc;
        return new String(trim(sSrc.getBytes(), ch, nPos)).trim();
    }

    private static byte[] trimnull(byte[] bySrc) {
        int nStartPos = 0;
        int nEndPos = bySrc.length - 1;
        for (int i = 0; i < bySrc.length; i++) {
            if (bySrc[i] != 0) {
                nStartPos = i;
                break;
            }
        }
        for (int i = bySrc.length - 1; i >= 0; i--) {
            if (bySrc[i] != 0) {
                nEndPos = i;
                break;
            }
        }
        if (nEndPos == nStartPos && bySrc[nEndPos] == 0)
            return null;
        byte[] byRst = new byte[nEndPos - nStartPos + 1];
        System.arraycopy(bySrc, nStartPos, byRst, 0, nEndPos - nStartPos + 1);
        return byRst;
    }

    /**
     * 用特定字符填充字符串。
     *
     * @param sSrc  被填充的字符串
     * @param ch    填充字符
     * @param nLen  填充后长度
     * @param bLeft 是否左填充： TRUE：左填充，FALSE：右填充
     * @return
     */
    public static String fill(String sSrc, char ch, int nLen, boolean bLeft) {
        /** 2012.3.6 改为不调用trim，由调用者自己决定是否删除 */
        //		sSrc = sSrc.trim();
        StringBuffer sbTmp;
        if (sSrc == null || sSrc.equals("")) {
            sbTmp = new StringBuffer();
            for (int i = 0; i < nLen; i++) {
                sbTmp.append(ch);
            }
            return sbTmp.toString();
        }
        int nSrcLen = sSrc.length();
        if (nSrcLen >= nLen)
            return sSrc;
        if (bLeft) {
            sbTmp = new StringBuffer();
            int nAppLen = nLen - nSrcLen;
            for (int i = 0; i < nAppLen; i++) {
                sbTmp.append(ch);
            }
            sbTmp.append(sSrc);
            return sbTmp.toString();
        } else {
            sbTmp = new StringBuffer();
            sbTmp.append(sSrc);
            int nAppLen = nLen - nSrcLen;
            for (int i = 0; i < nAppLen; i++) {
                sbTmp.append(ch);
            }
            return sbTmp.toString();
        }
    }

    /**
     * 用特定字符填充字符串。
     *
     * @param sSource
     * @param ch
     * @param nLen
     * @param bLeft
     * @return
     */
    private static String fillChar(String sSource, char ch, int nLen, boolean bLeft) {
        int nSrcLen = sSource.length();
        if (nSrcLen < nLen) {
            StringBuffer buffer = new StringBuffer();
            if (bLeft) {
                for (int i = 0; i < (nLen - nSrcLen); i++) {
                    buffer.append(ch);
                }
                buffer.append(sSource);
            } else {
                buffer.append(sSource);
                for (int i = 0; i < (nLen - nSrcLen); i++)
                    buffer.append(ch);
            }
            return (buffer.toString());
        }
        return sSource;
    }

    /**
     * @param data
     * @return
     */
    public static String asc2Hex(byte[] data) {
        StringBuffer sbRet = new StringBuffer();
        for (int i = 0, n = data.length; i < n; i++) {
            String sHex = fillChar(Integer.toHexString((int) data[i] & 0XFF), '0', 2, true);
            sbRet.append(sHex);
        }
        return sbRet.toString();
    }

    /**
     * @param data
     * @return
     */
    public static byte[] hex2Asc(String data) {
        String sHex = data;
        int nLen = sHex.length() / 2;
        byte[] byRet = new byte[nLen];
        String sTmp = "";
        for (int i = 0; i < nLen; i++) {
            sTmp = sHex.substring(i * 2, i * 2 + 2);
            byRet[i] = (byte) Integer.parseInt(sTmp, 16);
        }
        return byRet;
    }

    /**
     * 左补空格
     *
     * @param msg
     * @param length
     * @return
     */
    public static String appendLeftBlank(String msg, int length) {
        return appendLeftSymbol(msg, length, ' ', "gbk");
    }

    /**
     * 左补符号
     *
     * @return
     * @throws Exception
     */
    public static String appendLeftSymbol(String msg, int length, char symbol, String encoding) {
        int appendLen;
        try {
            appendLen = length - msg.getBytes(encoding).length;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        if (appendLen > 0) {
            for (int i = 0; i < appendLen; i++) {
                msg = symbol + msg;
            }
            return msg;
        } else if (appendLen == 0) {
            return msg;
        } else if (appendLen < 0) {
            return msg;
        }
        return null;
    }

    /**
     * 根据字符换模板和参数获取字符串
     *
     * @param result
     * @param params
     * @return
     */
    public static String getTempateString(String result, String[] params) {
        for (int i = 0; i < params.length; i++) {
            String tmp = "{" + i + "}";
            int index = result.indexOf(tmp);
            while (-1 < index) {
                result = result.substring(0, index) + params[i] + result.substring(index + tmp.length());
                index = result.indexOf(tmp, index + (null == params[i] ? 4 : params[i].length()));
            }
        }
        return result;
    }

    /**
     * 将字符串转为数组
     *
     * @param data   数据
     * @param encode 字符编码
     * @return 字节数组
     */
    public static byte[] getBytes(String data, String encode) {
        try {
            if (null == data) {
                return null;
            }
            return data.getBytes(encode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 在字符串数组中比对关键字
     * @param key 关键字
     * @param allowKeyList 关键字数组
     * @return 是否存在
     */
    public static boolean arrayContain(String key,String [] allowKeyList){
        if(ValidateUtils.isArrEmpty(allowKeyList)){
            return false;
        }

        for(String s:allowKeyList){
            if(s.equals(key)){
                return true;
            }
        }
        return false;
    }


}
