package com.hq.diego.repository.dao.custom;

import com.hq.diego.model.resp.statistics.MchTradeStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhaoyang on 03/02/2017.
 */
public interface TradeStatisticsMapper {

    List<MchTradeStatistics> getMchTradeStatistics(@Param("mchId") String mchId
            , @Param("startDate") String startDate, @Param("endDate") String endDate);

}
