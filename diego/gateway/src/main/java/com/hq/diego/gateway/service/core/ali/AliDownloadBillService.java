package com.hq.diego.gateway.service.core.ali;

import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.hq.diego.gateway.httpclient.executor.AliServiceExecutor;
import com.hq.diego.gateway.service.core.common.DownloadBillService;
import com.hq.diego.model.req.DownloadBillReq;
import com.hq.diego.model.resp.DownloadBillResp;
import com.hq.diego.model.route.ChannelRoute;
import com.hq.scrati.common.exception.CommonErrCode;
import com.hq.scrati.common.exception.CommonException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 04/02/2017.
 */
@Service("AliDownloadBillService")
public class AliDownloadBillService implements DownloadBillService {

    private static final Logger logger = Logger.getLogger(AliDownloadBillService.class);

    @Autowired
    private AliServiceExecutor executor;

    @Override
    public DownloadBillResp download(DownloadBillReq downloadBillReq, ChannelRoute route) {
        try {
            StringBuilder dateBuilder = new StringBuilder(
                    downloadBillReq.getBillDate());
            dateBuilder.insert(4, "-");
            dateBuilder.insert(7, "-");
            AlipayDataDataserviceBillDownloadurlQueryRequest request =
                    new AlipayDataDataserviceBillDownloadurlQueryRequest();
            request.setBizContent("{" +
                    "    \"bill_type\":\"trade\"," +
                    "    \"bill_date\":\"" + dateBuilder.toString() + "\"" +
                    "  }");
            AlipayDataDataserviceBillDownloadurlQueryResponse response = this
                    .executor.execute(request, null, route.getKey());
            if (response.isSuccess()) {


                response.getBillDownloadUrl();


                System.out.println("调用成功");
                System.out.println(response.getBody());
            } else {
                if ("INVAILID_ARGUMENTS".equals(response.getSubCode())) {

                } else if ("BILL_NOT_EXIST".equals(response.getSubCode())) {

                } else {

                }
            }
            return null;
        } catch (CommonException ce) {
            logger.warn("<<<<<< ", ce);
            throw ce;
        } catch (Throwable th) {
            logger.warn("<<<<<< ", th);
            throw new CommonException(CommonErrCode.INTERNAL_SERVER_ERROR, "", th);
        }
    }

    public static void main(String[] args) throws Exception {


        AliServiceExecutor executor = new AliServiceExecutor();
        AlipayDataDataserviceBillDownloadurlQueryRequest request =
                new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "    \"bill_type\":\"trade\"," +
                "    \"bill_date\":\"2017-02-05\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = executor
                .execute(request, null, "201701BB000bfcf04c5c44289f24e9ffd2b3fA33");
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.getBody());
        } else {
            if ("INVAILID_ARGUMENTS".equals(response.getSubCode())) {

            } else if ("BILL_NOT_EXIST".equals(response.getSubCode())) {

            } else {

            }
            System.out.println("调用失败");
        }

    }




}
