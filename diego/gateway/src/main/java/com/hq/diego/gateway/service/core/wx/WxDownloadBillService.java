package com.hq.diego.gateway.service.core.wx;

import com.hq.diego.gateway.service.core.common.DownloadBillService;
import com.hq.diego.model.req.DownloadBillReq;
import com.hq.diego.model.resp.DownloadBillResp;
import com.hq.diego.model.route.ChannelRoute;
import org.springframework.stereotype.Service;

/**
 * Created by zhaoyang on 04/02/2017.
 */
@Service("WxDownloadBillService")
public class WxDownloadBillService implements DownloadBillService {

    @Override
    public DownloadBillResp download(DownloadBillReq downloadBillReq, ChannelRoute route) {
        return null;
    }
}
