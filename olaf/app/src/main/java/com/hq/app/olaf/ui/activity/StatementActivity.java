package com.hq.app.olaf.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.ui.fragment.IQuery;
import com.hq.component.annotation.Layout;
import com.hq.component.date.DateTime;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.TextHelper;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.about.MerchantInfo;
import com.hq.app.olaf.ui.bean.home.BillSummary;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.activity_statement)
public class StatementActivity extends HqPayActivity implements IQuery, DatePickerDialog.OnDateSetListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.statementDate) TextView statementDate;
    @Bind(R.id.receiptAmount) TextView receiptAmount;
    @Bind(R.id.incomeAmount) TextView incomeAmount;
    @Bind(R.id.totalAmount) TextView totalAmount;
    @Bind(R.id.tradeCount) TextView tradeCount;
    @Bind(R.id.wxTotalAmount) TextView wxTotalAmount;
    @Bind(R.id.wxTradeCount) TextView wxTradeCount;
    @Bind(R.id.aliTotalAmount) TextView aliTotalAmount;
    @Bind(R.id.aliTradeCount) TextView aliTradeCount;
    @Bind(R.id.refundAmount) TextView refundAmount;
    @Bind(R.id.refundCount) TextView refundCount;
    @Bind(R.id.rateFee) TextView rateFee;
    @Bind(R.id.refundRateFee) TextView refundRateFee;

    private DateTime startDate;
    private DateTime nowDate;
    private String dateStart;
    HttpTask getBillSummary ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);

        setActTitle("对账单");

        nowDate = DateTime.now(TimeZone.getDefault());
        startDate = nowDate;
        dateStart=startDate.format("YYYYMMDD");
        statementDate.setText(startDate.format("YYYY-MM-DD"));
        loadData();
    }

    private void loadData( ){

        getBillSummary = HttpService.getInstance().getBillSummary(dateStart);
        NetTools.excute(getBillSummary, new LoadingDialog(this), new HqNetCallBack(getRootView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    BillSummary billSummary= JSON.parseObject((String) taskResult.getResult(), BillSummary.class);
                    if(billSummary!=null) {
                        TextHelper.setText(receiptAmount,   String .format("%.2f",Double.valueOf(billSummary.getReceiptAmount())/100));
                        TextHelper.setText(incomeAmount,  String .format("%.2f",Double.valueOf(billSummary.getIncomeAmount())/100));
                        TextHelper.setText(totalAmount,  String .format("%.2f",Double.valueOf(billSummary.getTotalAmount())/100));
                        TextHelper.setText(tradeCount, billSummary.getTradeCount()+"");
                        TextHelper.setText(wxTotalAmount,  String .format("%.2f",Double.valueOf(billSummary.getWxTotalAmount())/100));
                        TextHelper.setText(wxTradeCount, billSummary.getWxTradeCount()+"");
                        TextHelper.setText(aliTotalAmount, String .format("%.2f",Double.valueOf(billSummary.getAliTotalAmount())/100));
                        TextHelper.setText(aliTradeCount, billSummary.getAliTradeCount()+"");
                        TextHelper.setText(refundAmount,  String .format("%.2f",Double.valueOf(billSummary.getRefundAmount())/100));
                        TextHelper.setText(refundCount, billSummary.getRefundCount()+"");
                        TextHelper.setText(rateFee,  String .format("%.2f",Double.valueOf(billSummary.getRateFee())/100));
                        TextHelper.setText(refundRateFee,  String .format("%.2f",Double.valueOf(billSummary.getRefundRateFee())/100));
                    }
                }
            }
        });
    }

    @Override
    public void refreshList() {

    }

    @Override
    public void loadMoreList() {

    }

    @Override
    public void getList() {

    }

    @Override
    public void cancel() {
        if (getBillSummary != null) {
            ThreadPoolTool.getInstance().cancel(getBillSummary.getTAG());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_statement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.filtering:
                showDateStart();
                break;
        }
        return true;
    }


    /**
     * 检查日期选择
     */
    private boolean checkDate() {

        DateTime dateTime = nowDate.minus(0, 3, 0,
                nowDate.getHour() == null ? 0 : nowDate.getHour(),
                nowDate.getMinute() == null ? 0 : nowDate.getMinute(),
                nowDate.getSecond() == null ? 0 : nowDate.getSecond(),
                nowDate.getNanoseconds() == null ? 0 : nowDate.getNanoseconds(),
                DateTime.DayOverflow.LastDay);
        dateTime = DateTime.forDateOnly(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        if (dateTime.gt(startDate)) {
            showSnackBar(StatementActivity.this.getRootView(), "所选时间区域不能超过三个月");
            return false;
        }
        return true;
    }

    /**
     * 选择开始时间
     */
    public void showDateStart() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                startDate.getYear(),
                startDate.getMonth()-1,
                startDate.getDay()
        );
        dpd.setMaxDate(Calendar.getInstance());
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(StatementActivity.this.getFragmentManager(), "startDate");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        DateTime dateTime = DateTime.forDateOnly(year, monthOfYear + 1, dayOfMonth);
        if ("startDate".equals(view.getTag())) {
            startDate = dateTime;
            dateStart=dateTime.format("YYYYMMDD");
            statementDate.setText(dateTime.format("YYYY-MM-DD"));
            if(checkDate()){ loadData();}
        }
    }

}
