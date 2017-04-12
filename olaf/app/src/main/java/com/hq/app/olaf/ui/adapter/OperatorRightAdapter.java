package com.hq.app.olaf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.bean.oper.Function;
import com.hq.component.utils.TextHelper;
import com.hq.uicomponet.easyrecyclerview.adapter.BaseViewHolder;
import com.hq.uicomponet.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16-5-11.
 */
public class OperatorRightAdapter extends RecyclerArrayAdapter<Function> {

    public OperatorRightAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderHolder(parent);
    }


    public class OrderHolder extends BaseViewHolder<Function> {
        @Bind(R.id.title) TextView title;
        @Bind(R.id.check) CheckBox check;

        public OrderHolder(ViewGroup parent) {
            super(parent, R.layout.layout_operatorrignt_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(final Function data) {
            super.setData(data);
            TextHelper.setText(title, data.getName());
            check.setChecked(data.isChecked());
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        data.setChecked(false);
                    } else {
                        data.setChecked(true);
                    }
                }
            });
        }
    }

    public void refresh(List<Function> functions) {
        insert(0, functions);
        notifyDataSetChanged();
    }

    public void loadMore(List<Function> functions) {
        addAll(functions);
        notifyDataSetChanged();
    }

}
