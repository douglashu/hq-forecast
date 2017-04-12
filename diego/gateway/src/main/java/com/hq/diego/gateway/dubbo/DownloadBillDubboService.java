package com.hq.diego.gateway.dubbo;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 04/02/2017.
 */
public interface DownloadBillDubboService {

    RespEntity downloadBill(HqRequest request);

}
