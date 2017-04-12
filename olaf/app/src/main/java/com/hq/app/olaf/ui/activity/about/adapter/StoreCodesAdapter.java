package com.hq.app.olaf.ui.activity.about.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.adapter.ArrayListAdapter;
import com.hq.app.olaf.ui.bean.about.ProSigns;
import com.hq.app.olaf.ui.bean.about.StoreCodes;
import com.hq.app.olaf.util.DateUtil;

import java.util.Date;

/**
 * Created by jyl on 2017/2/14.
 */

public class StoreCodesAdapter extends ArrayListAdapter<StoreCodes> {

    private Context mContext;

    public StoreCodesAdapter(Activity context) {
        super(context);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreCodesAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_store_codes, null);
            holder = new StoreCodesAdapter.ViewHolder();
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_createTime = (TextView) convertView.findViewById(R.id.txt_createTime);
            holder.txt_state = (TextView) convertView.findViewById(R.id.txt_state);
            convertView.setTag(holder);
        } else {
            holder = (StoreCodesAdapter.ViewHolder) convertView.getTag();
        }

        if(mList!=null && mList.size()>0) {
            holder.txt_name.setText(mList.get(position).getName());
            holder.txt_createTime.setText(DateUtil.getSimpleDateString(new Date(mList.get(position).getCreateTime())));
            holder.txt_state.setText(getStateStr(mList.get(position).getState()));
        }
        return convertView;
    }

   private String getStateStr (String state){
        switch (state){
            case  "S":
                return "正常";
            case  "N":
                return "异常";
        }
       return "";
    }

    static class ViewHolder {
        TextView txt_name;
        TextView txt_createTime;
        TextView txt_state;
    }
}
