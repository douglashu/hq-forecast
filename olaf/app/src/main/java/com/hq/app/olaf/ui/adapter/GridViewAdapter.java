package com.hq.app.olaf.ui.adapter;

/**
 * Created by jyl on 2017/1/12.
 */

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.umeng.message.entity.UMessage;

import cn.bingoogolapple.badgeview.BGABadgeImageView;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listItem;

    public GridViewAdapter(Context context,List<Map<String, Object>> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
        }

        BGABadgeImageView imageView = (BGABadgeImageView) convertView.findViewById(R.id.image_item);
//        UMessage uMessage = HqPayApplication.getAppContext().getCache(HqPayApplication.HAVE_NEW_MESSAGE, UMessage.class);
//        if(position==1)imageView.showCirclePointBadge();
//        if (uMessage != null && position==1) {
//            imageView.showCirclePointBadge();
//        }
        TextView textView = (TextView) convertView.findViewById(R.id.text_item);

        Map<String, Object> map = listItem.get(position);
        imageView.setImageResource((Integer) map.get("image"));
        textView.setText(map.get("title") + "");
        return convertView;
    }

}
