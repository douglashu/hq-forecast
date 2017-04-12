package com.hq.app.olaf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hq.app.olaf.R;
import com.hq.component.utils.TextHelper;
import com.hq.uicomponet.easyrecyclerview.adapter.BaseViewHolder;
import com.hq.uicomponet.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.hq.app.olaf.ui.bean.order.Order;
import com.hq.app.olaf.ui.enums.PayChannelEnum;
import com.hq.app.olaf.ui.enums.PayStatusEnum;
import com.hq.app.olaf.util.DateUtil;
import com.hq.app.olaf.util.JSONUtil;
import com.hq.app.olaf.util.StringUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16-5-11.
 */
public class OrderAdapter extends RecyclerArrayAdapter<Order> {

    public OrderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderHolder(parent);
    }

    public class OrderHolder extends BaseViewHolder<Order> {
        @Bind(R.id.tradeDate) TextView tradeDate;
        @Bind(R.id.tradeTime) TextView tradeTime;
        @Bind(R.id.operatorName) TextView operatorName;
        @Bind(R.id.amount) TextView amount;
        @Bind(R.id.payChannel) TextView payChannel;
        @Bind(R.id.bankIcon) ImageView bankIcon;
        @Bind(R.id.tradeStatus) TextView tradeStatus;


        public OrderHolder(ViewGroup parent) {
            super(parent, R.layout.layout_orderlist_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Order data) {
            super.setData(data);

            if(!StringUtil.isEmpty(data.getId())){
                if(!StringUtil.isEmpty(data.getCreateDate()) ){
                    TextHelper.setText(tradeDate, DateUtil.formatDateStr(data.getCreateDate()) );
                }
                if(!StringUtil.isEmpty(data.getCreateTime()) ){
                    TextHelper.setText(tradeTime, DateUtil.formatTimeStr(data.getCreateTime()));
                }
                if(data.getTotalAmount()>0){
                    TextHelper.setText(amount,  String .format("%.2f",Double.valueOf(data.getTotalAmount())/100)+"¥");
                }
                if(!StringUtil.isEmpty(data.getPayChannel())) {
                    TextHelper.setText(payChannel, PayChannelEnum.getChannel(data.getPayChannel()).getName());
                    setPayImg(PayChannelEnum.getChannel(data.getPayChannel()));
                }
                if(!StringUtil.isEmpty(data.getOperatorName())) {
                    TextHelper.setText(operatorName, data.getOperatorName());
                }
                if(!StringUtil.isEmpty(data.getTradeState())) {
                    if( PayStatusEnum.getPayStatus(data.getTradeState())!=PayStatusEnum.SUCCESS){
                        tradeStatus.setTextColor(getContext().getResources().getColor(R.color.Logistic_3));
                    }else{
                        tradeStatus.setTextColor(getContext().getResources().getColor(R.color.black));
                    }
                   TextHelper.setText(tradeStatus, PayStatusEnum.getPayStatus(data.getTradeState()).getDesc());
                    //lper.setText(tradeStatus,data.getTradeState());
                }



            }
        }

        private void setPayImg(PayChannelEnum channel){
            switch(channel){
                case WEIXIN_PAY:
                    bankIcon.setImageResource(R.drawable.icon_weixinpay);
                break;
                case ALI_PAY:
                    bankIcon.setImageResource(R.drawable.icon_alipay);
                break;
            }
        }
    }

    public void refresh(List<Order> orders) {
        insert(0, orders);
        notifyDataSetChanged();
    }

    public void loadMore(List<Order> orders) {
        addAll(orders);
        notifyDataSetChanged();
    }


}
