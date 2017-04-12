package com.hq.app.olaf.ui.activity.shoukuan;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hq.component.annotation.Layout;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.order.ScannerOrderResp;

import butterknife.Bind;

@Layout(layoutId = R.layout.activity_scanner_pay_info)
public class ScannerPayInfoActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.totalAmount)
    TextView totalAmount;

    @Bind(R.id.endTime)
    TextView endTime;

    @Bind(R.id.tOrderId)
    TextView tOrderId;

    public final static String RETURN_DATA = "DATA";//返回数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        title.setText("支付详情");

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {

           ScannerOrderResp scannerOrderResp = bundle.getParcelable(RETURN_DATA);

            if(scannerOrderResp !=null){
                //交易金额
                totalAmount.setText(scannerOrderResp.getTotalAmount());
                //交易时间
                endTime.setText(scannerOrderResp.getEndDate()+ scannerOrderResp.getEndTime());
                //交易订单号
                tOrderId.setText(scannerOrderResp.gettOrderId());
            }
        }


    }
}
