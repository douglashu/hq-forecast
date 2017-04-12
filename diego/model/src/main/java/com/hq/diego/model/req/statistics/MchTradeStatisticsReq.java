package com.hq.diego.model.req.statistics;

import com.hq.scrati.model.HqRequest;

/**
 * Created by zhaoyang on 03/02/2017.
 */
public class MchTradeStatisticsReq extends HqRequest {

    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
