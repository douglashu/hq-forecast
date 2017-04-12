package com.hq.diego.gateway.service.redpacket;

import com.hq.diego.gateway.constant.url.WxPayURL;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by young on 1/27/16.
 */
public class WxRedPacketService {
    private static final Logger logger = Logger.getLogger(WxRedPacketService.class);

    public SendRedPacketRsp sendRedPacket(SendRedPacketReq redPacket, String key) {
        /*
        try {
            redPacket.genSendSign(key);
            HttpPost postMethod = new HttpPost(WxPayURL.getSendRedPacketUrl());
            postMethod.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            postMethod.setEntity(new StringEntity(redPacket.toSendXmlString(), Charset.forName("UTF-8")));
            CloseableHttpClient httpClient = null;
            CloseableHttpResponse resp = httpClient.execute(postMethod);
            try { postMethod.releaseConnection(); } finally { }
            try { resp.close(); } finally { }
            if(HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
                String xmlStr = EntityUtils.toString(resp.getEntity());
                Document doc = DocumentHelper.parseText(xmlStr);
                Element root = doc.getRootElement();
                SendRedPacketRsp rsp = new SendRedPacketRsp();
                rsp.setReturn_code(root.elementText("return_code"));
                rsp.setReturn_msg(root.elementText("return_msg"));
                rsp.setResult_code(root.elementText("result_code"));
                rsp.setErr_code(root.elementText("err_code"));
                rsp.setErr_code_des(root.elementText("err_code_des"));
                rsp.setMch_id(root.elementText("mch_id"));
                rsp.setMch_billno(root.elementText("mch_billno"));
                rsp.setWxappid(root.elementText("wxappid"));
                rsp.setRe_openid(root.elementText("re_openid"));
                String totalAmount = root.elementText("total_amount");
                if(totalAmount != null) {
                    rsp.setTotal_amount(Integer.valueOf(totalAmount));
                }
                rsp.setSend_listid(root.elementText("send_listid"));
                String sendTime = root.elementText("send_time");
                if(sendTime != null) {
                    rsp.setSend_time(new Date(Long.valueOf(sendTime)));
                }
                return rsp;
            } else {
                logger.warn("Send red packet to '" + redPacket.getRe_openid()
                        + "' fail, the http status code is " + resp.getStatusLine().getStatusCode() + ".");
            }
        } catch (Throwable th) {
            logger.warn("Send red packet to '" + redPacket.getRe_openid() + "' fail.", th);
        }
        */
        return null;
    }

    public void getRedPackets(SendRedPacketReq redPacket, String key) {
        /*
        try {
            redPacket.genQuerySign(key);
            HttpPost postMethod = new HttpPost(WxPayURL.getGetRedPackets());
            postMethod.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            postMethod.setEntity(new StringEntity(redPacket.toQueryXmlString(), Charset.forName("UTF-8")));
            CloseableHttpClient httpClient = null;
            CloseableHttpResponse resp = httpClient.execute(postMethod);
            try { postMethod.releaseConnection(); } finally { }
            try { resp.close(); } finally { }
            if(HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
                String xmlStr = EntityUtils.toString(resp.getEntity());
                System.out.println(xmlStr);
            } else {
                logger.warn("Get red packets fail, the http status code is '" + resp.getStatusLine().getStatusCode() + ".");
            }
        } catch (Throwable th) {
            logger.warn("Get red packets fail.", th);
        }
        */
    }
}
