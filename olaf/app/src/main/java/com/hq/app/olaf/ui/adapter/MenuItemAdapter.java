package com.hq.app.olaf.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.app.Apps;
import com.hq.component.utils.TextHelper;
import com.umeng.message.entity.UMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.badgeview.BGABadgeImageView;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {
    private List<Apps> appsList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_grid_index_item, parent, false);
        return new MenuItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        final Apps apps = appsList.get(position);
        Integer resourceId=0;
        switch (position){
            case 0:
                resourceId=R.mipmap.ic_home_multiple_bill_2x;
                break;
            case 1:
                resourceId=R.mipmap.ic_home_staff_2x;
                break;
            case 2:
                resourceId=R.mipmap.ic_home_revenue_data_disabled_2x;
                break;
            case 3:
                resourceId=R.mipmap.ic_home_card_manage_disabled_2x;
                break;
            case 4:
                resourceId=R.mipmap.ic_home_coupon_manage_disabled_2x;
                break;
            case 5:
                resourceId=R.mipmap.ic_home_send_coupons_center_disabled_2x;
                break;
            case 6:
                resourceId=R.mipmap.ic_home_member_center_disabled_2x;
                break;
            case 7:
                resourceId=R.mipmap.ic_home_micro_page_marketing_disabled_2x;
                break;
            default:resourceId=R.drawable.icon_app_default;
                break;
        }
        Glide.with(holder.ivIcon.getContext())
               // .load(apps.getIcon())
                .load(resourceId)
                .placeholder(R.drawable.icon_app_default)
                .crossFade()
                .skipMemoryCache(true)
                .into(holder.ivIcon);
     /*   UMessage uMessage = HqPayApplication.getAppContext().getCache(HqPayApplication.HAVE_NEW_MESSAGE, UMessage.class);
        if (uMessage != null && "RATE_MANAGE".equals(apps.getId())) {
            holder.ivIcon.showCirclePointBadge();
        }*/
        TextHelper.setText(holder.title, apps.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(apps);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon) BGABadgeImageView ivIcon;
        @Bind(R.id.title) TextView title;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void refresh(List<Apps> apps) {
        appsList.clear();
        if (apps != null && apps.size() > 0) {
            appsList.addAll(apps);
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(Apps apps);
    }


}
