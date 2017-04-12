package com.hq.app.olaf.ui.activity.shoukuan;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hq.component.annotation.Layout;
import com.hq.component.network.KLog;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.order.ScannerOrderResp;
import com.hq.app.olaf.util.Const;

import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.activity_pay_success)
public class ScannerPayResultActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;


    @Bind(R.id.img_result)
    ImageView imgResult;
    @Bind(R.id.pay_channel)
    TextView payChannel;
    @Bind(R.id.text_amout)
    TextView textAmout;
    @Bind(R.id.text_result)
    TextView textResult;

    @Bind(R.id.txt_failed_info)
    TextView txtFailedInfo;

    @Bind(R.id.btn_back_home)
    Button btn_back_home;

    public final static String RETURN_MESSAGE = "MESSAGE";//返回提示信息
    public final static String RETURN_DATA = "DATA";//返回数据
    public final static String CASH_NUMBER = "CASH_NUMBER";//交易金额
    public final static String SANNER_TYPE = "SANNER_TYPE";//扫码类型： 微信 支付宝

    public final static String RETURN_CODE = "CODE";//0 :消费成功  -1：消费失败    10：消费撤销成功
    private int code;
    private String message;
    private String cash;
    private String scannerType;//交易类型
    private ScannerOrderResp scannerOrderResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbarWithoutListener(toolbar);
       // title.setText("收款");
        title.setVisibility(View.GONE);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            code = bundle.getInt(RETURN_CODE);
            message = bundle.getString(RETURN_MESSAGE);
            scannerType = bundle.getString(SANNER_TYPE);
            cash = bundle.getString(CASH_NUMBER);
            scannerOrderResp = bundle.getParcelable(RETURN_DATA);
        }

        initView();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward(ShoukuanActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                finish();
            }
        });
    }

    private void initView() {


        if (code > 0) {
            if (Const.ALI_PAY_TYPE.equals(scannerType)) {
                payChannel.setText("：支付宝");
                KLog.i("支付宝支付成功");

            } else if (Const.WEI_CHART_TYPE.equals(scannerType)) {
                payChannel.setText("：微信支付");
                KLog.i("微信支付成功");
            } else {
                payChannel.setText("：扫码支付");
                KLog.i("扫码支付成功");
            }
            textResult.setText("交易成功");
            textAmout.setText("："+cash + "元");
            txtFailedInfo.setVisibility(View.INVISIBLE);
        } else {
            if (Const.ALI_PAY_TYPE.equals(scannerType)) {
                payChannel.setText("：支付宝");
                KLog.i("支付宝支付失败");
            } else if (Const.WEI_CHART_TYPE.equals(scannerType)) {
                payChannel.setText("：微信支付");
                KLog.i("微信支付失败");
            } else {
                payChannel.setText("：扫码支付");
                KLog.i("扫码支付失败");
            }
            textResult.setText("交易失败");
            textAmout.setText("："+cash + "元");
            KLog.i("结果码code:" + code + "   结果信息msg:" + message + "    支付金额cash:" + cash);
            Drawable failDrawable = getResources().getDrawable(R.drawable.icon_pay_failed);
            imgResult.setImageDrawable(failDrawable);
            txtFailedInfo.setText(message);
            txtFailedInfo.setVisibility(View.VISIBLE);

        }
    }

    //返回首面
    @OnClick(R.id.btn_back_home)
    protected void backHome(){
        forward(NewIndexActivity.class);
        finish();
    }

    /**
     * 设置返回键事件
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            forward(ShoukuanActivity.class);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
            finish();
        }
        return true;
    }
}
