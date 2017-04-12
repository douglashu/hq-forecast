package com.hq.app.olaf.ui.activity.order;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.order.Order;
import com.hq.app.olaf.ui.enums.PayChannelEnum;
import com.hq.app.olaf.ui.enums.PayStatusEnum;
import com.hq.app.olaf.ui.enums.TradeTypeEnum;
import com.hq.app.olaf.ui.fragment.OrderListFragment;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.utils.TextHelper;


import butterknife.Bind;

/**
 * 订单详情
 */
@Layout(layoutId = R.layout.activity_order_detail)
public class OrderDetailActivity extends HqPayActivity {
    @Bind(R.id.tx_payChannel)
    TextView payChannel;
    @Bind(R.id.tx_tradeType)
    TextView tradeType;
    @Bind(R.id.amount)
    TextView amount;
    @Bind(R.id.totalAmount)
    TextView totalAmount;
    @Bind(R.id.receiptAmount)
    TextView receiptAmount;
    @Bind(R.id.buyerPayAmount)
    TextView buyerPayAmount;
    @Bind(R.id.terminalId)
    TextView terminalId;
    @Bind(R.id.operatorId)
    TextView operatorId;
    @Bind(R.id.operatorName)
    TextView operatorName;

    @Bind(R.id.createDateTime)
    TextView createDateTime;
    @Bind(R.id.endDateTime)
    TextView endDateTime;

    @Bind(R.id.tx_id)
    TextView id;
    @Bind(R.id.orderId)
    TextView orderId;
    @Bind(R.id.attach)
    TextView attach;


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.icon)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        //setActTitle("订单详情");
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_pay_success));
        Bundle bundle = getIntent().getExtras();
        Order order = bundle.getParcelable(OrderListFragment.ORDER_ITEM);
        if (order != null) {

            initOrderDetail(order);


           /* HttpTask httpTask = HttpService.getInstance().getOrderDetail(operatorId, tempOrderId, type);
            NetTools.excute(httpTask, new LoadingDialog(this), new NetTools.CallBack() {
                @Override
                public void onComplete(TaskResult taskResult) {
                    if (taskResult.isSuccess()) {
                        String result = (String) taskResult.getResult();
                        OrderDetail orderDetail = JSON.parseObject(result, OrderDetail.class);
                        String tempOrderId = orderDetail.gettOrderId();
                        if (tempOrderId == null || TextUtils.isEmpty(tempOrderId.trim())) {
                            tempOrderId = orderDetail.getOrderId();
                        }
                        orderId.setTextValue(tempOrderId);
                        tradeTime.setTextValue(TextHelper.formatDateTimeStr(orderDetail.getTradeTime()));
                        PayStatusEnum status = PayStatusEnum.DEFAULT.getPayStatus(orderDetail.getTradeType());
                        tradeType.setTextValue(status.getDesc());

                        if(TextUtils.isEmpty(orderDetail.getSn())){
                            Viewlayout.setVisibility(View.GONE);
                        }else{
                            Viewlayout.setVisibility(View.VISIBLE);
                        }
                        sn.setTextValue(orderDetail.getSn());
//                        runningNo.setTextValue(orderDetail.getRunningNo());
                        traceAuditNo.setTextValue(orderDetail.getTraceAuditNo());
                        DecimalFormat format = new DecimalFormat("##,##0.##");
                        amount.setTextValue(format.format(orderDetail.getAmount()) + "元");
                        PayStatusEnum state = PayStatusEnum.DEFAULT.getPayStatus(orderDetail.getTradeState());
                        tradeState.setTextValue(state.getDesc());
                    } else {
                        new MaterialDialog.Builder(OrderDetailActivity.this)
                                .title("提示")
                                .content(taskResult.getError())
                                .positiveText("确定")
                                .cancelable(false)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    }
                }
            });*/
        }else{
            final String orderId = bundle.getString("orderId");
            NetTools.excute(HttpService.getInstance().getOrder(orderId) ,new HqNetCallBack(getRootView()){

                @Override
                protected void doComplete(TaskResult taskResult) {
                    Order getOrder = JSON.parseObject((String) taskResult.getResult(),Order.class);
                    initOrderDetail(getOrder);
                }
            });
        }


    }

    private void initOrderDetail(Order order) {
        setActTitle(PayStatusEnum.getPayStatus(order.getTradeState()).getDesc());
        if ( !TextUtils.isEmpty(order.getPayChannel())) {
           TextHelper.setText(payChannel, displayPayChannelText(PayChannelEnum.getChannel(order.getPayChannel())));
        }
        if ( !TextUtils.isEmpty(order.getTradeType())) {
            TextHelper.setText(tradeType, TradeTypeEnum.getType(order.getTradeType()).getDesc());
        }

        if ( order.getTotalAmount()>0) {
            TextHelper.setText(totalAmount,  String .format("%.2f",Double.valueOf(order.getTotalAmount())/100));
            TextHelper.setText(amount,String .format("%.2f",Double.valueOf(order.getTotalAmount())/100));
        }
        if ( order.getReceiptAmount()>0) {
            TextHelper.setText( receiptAmount,  String .format("%.2f",Double.valueOf(order.getReceiptAmount())/100));
        }else{
            TextHelper.setText(receiptAmount,"--");
        }

        if ( order.getBuyerPayAmount()>0) {
            TextHelper.setText( buyerPayAmount, String .format("%.2f",Double.valueOf(order.getBuyerPayAmount())/100));
        }else{
            TextHelper.setText(buyerPayAmount,"--");
        }

        if ( !TextUtils.isEmpty(order.getTerminalId())) {
            TextHelper.setText(terminalId, order.getTerminalId());
        }
        if ( !TextUtils.isEmpty(order.getOperatorId())) {
            TextHelper.setText(operatorId, order.getOperatorId());
        }
        if (!TextUtils.isEmpty(order.getOperatorName())) {
            TextHelper.setText( operatorName, order.getOperatorName());
        }

        if (!TextUtils.isEmpty(order.getCreateDate())
                && !TextUtils.isEmpty(order.getCreateTime())) {
            TextHelper.setText( createDateTime, TextHelper.formatDateTimeStr(order.getCreateDate()+order.getCreateTime()));
        }

        if (!TextUtils.isEmpty(order.getEndDate())
                && !TextUtils.isEmpty(order.getEndTime())) {
            TextHelper.setText(endDateTime, TextHelper.formatDateTimeStr(order.getEndDate()+order.getEndTime()));
        }

        if (!TextUtils.isEmpty(order.getId())) {
            TextHelper.setText(id, order.getId());
        }
        if (!TextUtils.isEmpty(order.getOrderId())) {
            TextHelper.setText(orderId, order.getOrderId());
        }
        if (!TextUtils.isEmpty(order.getAttach())) {
            TextHelper.setText(attach, order.getAttach());
        }
    }

    private  String displayPayChannelText(PayChannelEnum channel){
        switch (channel){
            case WEIXIN_PAY:
                return  "微信";
            case ALI_PAY:
                return  "支付宝";
            default: return  "其它";
        }
    }
}
