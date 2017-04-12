package com.hq.app.olaf.ui.activity.about.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.adapter.ArrayListAdapter;
import com.hq.app.olaf.ui.bean.about.ProSigns;
import com.hq.app.olaf.ui.enums.PayChannelEnum;

import butterknife.Bind;

import static com.hq.app.olaf.R.id.bankIcon;

/**
 * Created by jyl on 2017/2/14.
 */

public class ProSignsAdapter extends ArrayListAdapter<ProSigns> {

    private Context mContext;

    public ProSignsAdapter(Activity context) {
        super(context);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProSignsAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pro_signs, null);
            holder = new ProSignsAdapter.ViewHolder();
            holder.channelIcon = (ImageView) convertView.findViewById(R.id.channelIcon);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_rate = (TextView) convertView.findViewById(R.id.txt_rate);
            holder.txt_state = (TextView) convertView.findViewById(R.id.txt_state);
            holder.txt_discountRate = (TextView) convertView.findViewById(R.id.txt_discountRate);
            convertView.setTag(holder);
        } else {
            holder = (ProSignsAdapter.ViewHolder) convertView.getTag();
        }

        if(mList!=null && mList.size()>0){
            holder.channelIcon.setImageResource(setChannelImg(PayChannelEnum.getChannel(mList.get(position).getChannel())));
            holder.txt_name.setText(mList.get(position).getName());
            holder.txt_state.setText(getStateStr(mList.get(position).getState()));
            holder.txt_rate.setText("手续费:"+mList.get(position).getRate()*100+"%");
            if(mList.get(position).getRate()>mList.get(position).getDiscountRate()) {
                holder.txt_discountRate.setText("正在享受" + mList.get(position).getDiscountRate() * 100 + "%的优惠费率");
            }else{
                holder.txt_rate.setGravity(Gravity.CENTER);
            }
        }

        return convertView;
    }

    private int setChannelImg(PayChannelEnum channel){
        switch(channel){
            case WEIXIN_PAY:
                return R.drawable.icon_weixinpay;
            case ALI_PAY:
                return  R.drawable.icon_alipay;
        }
        return R.drawable.icon_weixinpay;
    }

    private String getStateStr(int state){
        switch (state){
            case 0:
                return "未开通";
            case 1:
                return "已开通";
        }
        return  "";
    }

    static class ViewHolder {
        ImageView channelIcon;
        TextView txt_name;
        TextView txt_rate;
        TextView txt_state;
        TextView txt_discountRate;
    }
}
