package com.hq.crash.web.controller.statistics;

import com.hq.crash.model.auth.UserSession;
import com.hq.crash.model.statistics.BillSummary;
import com.hq.crash.model.statistics.StatisticSummary;
import com.hq.crash.service.statistics.StatisticsService;
import com.hq.crash.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhaoyang on 05/02/2017.
 */
@RestController
public class StatisticsController extends BaseController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistic_summary", method = RequestMethod.GET)
    public ResponseEntity<StatisticSummary> getStatisticSummary(
            HttpServletRequest request, @RequestParam String statisticDate) {
        UserSession userSession = getUserSession(request);
        StatisticSummary summary = this
                .statisticsService.getStatisticSummary(statisticDate, userSession);
        return getJsonResp(summary, HttpStatus.OK);
    }

    @RequestMapping(value = "/bill_summary", method = RequestMethod.GET)
    public ResponseEntity<BillSummary> getBillSummary(
            HttpServletRequest request, @RequestParam String statisticDate) {
        UserSession userSession = getUserSession(request);
        BillSummary summary = this.statisticsService.getBillSummary(statisticDate, userSession);
        return getJsonResp(summary, HttpStatus.OK);
    }

}
