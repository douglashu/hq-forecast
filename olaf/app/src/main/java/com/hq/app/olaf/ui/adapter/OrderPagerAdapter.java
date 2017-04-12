package com.hq.app.olaf.ui.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.fragment.IQuery;
import com.hq.component.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwentao on 16-5-9.
 */
public class OrderPagerAdapter extends MyFragmentAdapter {
    private List<IQuery> orderFragments = new ArrayList<>();

    public View getTabView(Context context, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_order_custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        HqPayFragment baseFragment = (HqPayFragment) orderFragments.get(position);
        tv.setText(baseFragment.getTITLE());
//        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        //img.setImageResource(imageResId[position]);
        return v;
    }

    public OrderPagerAdapter(FragmentManager fm, List<IQuery> orderFragments) {
        super(fm);
        this.orderFragments.addAll(orderFragments);
    }

    @Override
    public HqPayFragment getItem(int position) {
        return (HqPayFragment) orderFragments.get(position);
    }

    @Override
    public int getCount() {
        return orderFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        BaseFragment orderFragment = (BaseFragment) orderFragments.get(position);
        return orderFragment.getTITLE();
    }
}
