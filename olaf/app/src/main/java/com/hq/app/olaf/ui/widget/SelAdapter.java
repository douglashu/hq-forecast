package com.hq.app.olaf.ui.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hq.component.utils.TextHelper;

import java.util.ArrayList;
import java.util.List;

import com.hq.app.olaf.R;


/**
 * Created by huwentao on 16-4-28.
 */
public class SelAdapter extends BaseAdapter {
    private List<SelItem> items = new ArrayList<>();
    private String title;

    public SelAdapter(List<? extends SelItem> items) {
        this.items.addAll(items);
    }

    public SelAdapter(List<? extends SelItem> items, String title) {
        this.items.addAll(items);
        this.title = title;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_seldialog_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.selItem = (TextView) convertView.findViewById(R.id.selItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextHelper.setText(viewHolder.selItem, items.get(position).getValue());
        return convertView;
    }

    static class ViewHolder {
        TextView selItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void refreshItems(List<? extends SelItem> selItems) {
        items.clear();
        items.addAll(selItems);
        notifyDataSetChanged();
    }
}
