package com.hq.app.olaf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.bean.role.Role;
import com.hq.app.olaf.util.StringUtil;
import com.hq.component.utils.TextHelper;
import com.hq.uicomponet.easyrecyclerview.adapter.BaseViewHolder;
import com.hq.uicomponet.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16-4-29.
 */
public class OperatorListAdapter extends RecyclerArrayAdapter<Operator> {

    public OperatorListAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        return new OperatorHolder(viewGroup);
    }

    public class OperatorHolder extends BaseViewHolder<Operator> {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.role)
        TextView role;
        @Bind(R.id.phoneNum)
        TextView phoneNum;


        public OperatorHolder(ViewGroup parent) {
            super(parent, R.layout.layout_operator_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Operator data) {
            super.setData(data);

            if (!StringUtil.isEmpty(data.getOperName())) {
                TextHelper.setText(name, data.getOperName());
            }
            if (data.getRole().size() > 0) {
                String roleStr = "";
                for (Role role : data.getRole()) {
                    roleStr += role.getRoleName() + " ";
                }
                TextHelper.setText(role, roleStr);
            }
            if (!StringUtil.isEmpty(data.getMobilePhone())) {
                TextHelper.setText(phoneNum, data.getMobilePhone());
            }


        }


    }

    public void remove(int position) {
        remove(position);
        notifyDataSetChanged();
    }

    public void refresh(List<Operator> operators) {
        insert(0, operators);
        notifyDataSetChanged();
    }

    public void loadMore(List<Operator> operators) {
        addAll(operators);
        notifyDataSetChanged();
    }
}