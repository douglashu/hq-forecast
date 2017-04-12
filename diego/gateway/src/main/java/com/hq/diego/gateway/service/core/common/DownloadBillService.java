package com.hq.diego.gateway.service.core.common;

import com.hq.diego.model.req.DownloadBillReq;
import com.hq.diego.model.resp.DownloadBillResp;
import com.hq.diego.model.route.ChannelRoute;

/**
 * Created by zhaoyang on 04/02/2017.
 */
public interface DownloadBillService {

    DownloadBillResp download(DownloadBillReq downloadBillReq, ChannelRoute route);

}
