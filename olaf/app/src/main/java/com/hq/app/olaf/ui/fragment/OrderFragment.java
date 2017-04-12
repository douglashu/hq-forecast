package com.hq.app.olaf.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.adapter.OrderPagerAdapter;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.component.annotation.Layout;
import com.hq.component.date.DateTime;
import com.hq.app.olaf.ui.enums.PayChannelEnum;
import com.hq.app.olaf.ui.widget.SegmentView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;

/**
 * Created by liaob on 2016/4/13.
 */
@Layout(layoutId = R.layout.fragment_order)
public class OrderFragment extends HqPayFragment implements SegmentView.OnItemCheckedListener {
    @Bind(R.id.tabLayout)
    TabLayout tableLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
/*    @Bind(R.id.segmentView1) SegmentView segmentView1;
    @Bind(R.id.segmentView2) SegmentView segmentView2;*/
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar2)
    Toolbar toolbar2;
    @Bind(R.id.title)
    TextView title;

    private List<IQuery> orderList = new ArrayList<>();
    private OrderListFragment todayFragment = null;
    private OrderListFragment yestodayFragment = null;
    private OrderListFragment thisWeekFragment = null;
    private OrderListFragment weekFragment = null;
    private OrderListFragment thisMonthFragment = null;
    private OrderListFragment monthFragment = null;
    //private OrderDatePickFragment dateFragment = OrderDatePickFragment.newInstance(null, "指定时间段", 7);
    private DateTime todayTime = DateTime.now(TimeZone.getDefault());
    private OrderPagerAdapter pagerAdapter = null;
    private IQuery orderFragment;
    private PayChannelEnum channel = PayChannelEnum.ALL;
    private boolean isShowBack;
    private boolean isDetached;

    @Override
    public void init() {
        if (isShowBack) {
          /*  segmentView1.setVisibility(View.GONE);
            segmentView2.setVisibility(View.VISIBLE);*/
            toolbar.setVisibility(View.GONE);
            title.setText("订单");
            //toolbar2.setVisibility(View.VISIBLE);
            getAppActivity().setSupportActionBar(toolbar2);
            ActionBar actionBar = getAppActivity().getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
            toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAppActivity().finish();
                    getAppActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                }
            });
           /* // 设置纵向排列
            segmentView2.setOrientation(SegmentView.HORIZONTAL);
            // 设置tabs
            segmentView2.setTabs(new String[]{
                    "全部", "微信", "支付宝"
            });
            // 设置点击事件
            segmentView2.setOnItemCheckedListener(this);*/
        } else {
           /* segmentView1.setVisibility(View.VISIBLE);
            segmentView2.setVisibility(View.GONE);*/
            toolbar.setVisibility(View.VISIBLE);
            title.setText("订单");
            getAppActivity().setSupportActionBar(toolbar);
            ActionBar actionBar = getAppActivity().getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAppActivity().finish();
                    getAppActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                }
            });
            //toolbar2.setVisibility(View.GONE);
            // 设置纵向排列
           /* segmentView1.setOrientation(SegmentView.HORIZONTAL);
            // 设置tabs
            segmentView1.setTabs(new String[]{
                    "全部", "微信", "支付宝"
            });
            // 设置点击事件
            segmentView1.setOnItemCheckedListener(this);*/
        }
//        title.setText("订单");
        orderList.clear();
        //今天
        todayFragment = OrderListFragment.newInstance(null, "今天", 1, todayTime.getStartOfDay(), todayTime.getEndOfDay());
        orderList.add(todayFragment);
        //昨天
        DateTime yestodayTime = todayTime.minus(0, 0, 1, 0, 0, 0, 0, DateTime.DayOverflow.LastDay);
        yestodayFragment = OrderListFragment.newInstance(null, "昨天", 2, yestodayTime.getStartOfDay(), yestodayTime.getEndOfDay());
        orderList.add(yestodayFragment);
        //本周内
        int minusDay = todayTime.getWeekDay() - 2;
        if (todayTime.getWeekDay() == 1) {
            minusDay = 6;
        }
        DateTime thisWeekTime = todayTime.minus(
                0, 0,
                minusDay,
                0, 0, 0, 0,
                DateTime.DayOverflow.LastDay
        );
        thisWeekFragment = OrderListFragment.newInstance(null, "本周", 3, thisWeekTime.getStartOfDay(), todayTime.getEndOfDay());
        orderList.add(thisWeekFragment);
        //一周内
        DateTime weekTime = todayTime.minus(
                0, 0,
                7,
                0, 0, 0, 0,
                DateTime.DayOverflow.LastDay
        );
        weekFragment = OrderListFragment.newInstance(null, "一周内", 4, weekTime.getStartOfDay(), todayTime.getEndOfDay());
//        orderList.add(weekFragment);
        //本月
        thisMonthFragment = OrderListFragment.newInstance(null, "本月", 5, todayTime.getStartOfMonth(), todayTime.getEndOfDay());
        orderList.add(thisMonthFragment);

        //一个月内
        DateTime monthTime = todayTime.minus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.LastDay);
        monthFragment = OrderListFragment.newInstance(null, "一月内", 6, monthTime.getStartOfDay(),
                todayTime.getEndOfDay());
//        orderList.add(monthFragment);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                

        /******指定时间段暂时不要*******/
        //orderList.add(dateFragment);

        pagerAdapter = new OrderPagerAdapter(getChildFragmentManager(), orderList);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(pagerAdapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Logger.d("selected position =>%s,pager count=>%s", tab.getPosition(), pagerAdapter.getCount());
                viewPager.setCurrentItem(tab.getPosition(), true);
                orderFragment = orderList.get(tab.getPosition());
                /*if (orderFragment instanceof OrderDatePickFragment) {
                    OrderDatePickFragment fragment = (OrderDatePickFragment) orderFragment;
                    if (isDetached) return;
                    fragment.resetData();
                }*/
                orderFragment.getList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                IQuery orderFragment = orderList.get(position);
                if (isDetached) return;
                orderFragment.cancel();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
      /*  TabLayout.Tab tab = tableLayout.getTabAt(pagerAdapter.getCount() - 1);
        if (tab != null) {
            tab.setCustomView(pagerAdapter.getTabView(getContext(), pagerAdapter.getCount() - 1));
            if (tab.getCustomView() != null) {
                View tabView = (View) tab.getCustomView().getParent();
                tabView.setTag(pagerAdapter.getCount() - 1);
                tabView.setOnClickListener(mTabOnClickListener);
            }
        }*/
        orderFragment = todayFragment;
//        viewPager.setCurrentItem(0, true);
//        segmentView.check(0);
        isDetached = false;

    }

    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(pagerAdapter.getCount() - 1);
            if (isDetached) return;
          //  dateFragment.resetData();
        }
    };

    @Override
    public void onCheck(RadioButton button, int position, String title) {
        Logger.d("--------onCheck------------");
        switch (position) {
            case 0:
                channel = PayChannelEnum.ALL;
                break;
            case 1:
                channel = PayChannelEnum.WEIXIN_PAY;
                break;
            case 2:
                channel = PayChannelEnum.ALI_PAY;
                break;
        }
        IQuery fragment = (IQuery) pagerAdapter.getItem(viewPager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof OrderListFragment) {
                if (isDetached) return;
                fragment.refreshList();
            }/* else if (fragment instanceof OrderDatePickFragment) {
                if (isDetached) return;
                fragment.refreshList();
            }*/
        }
    }

    public boolean isShowBack() {
        return isShowBack;
    }

    public void setShowBack(boolean showBack) {
        isShowBack = showBack;
    }

    public PayChannelEnum getChannel() {
        return channel;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isDetached = true;
        for (IQuery fragment : orderList) {
            fragment.cancel();
        }
    }
}
