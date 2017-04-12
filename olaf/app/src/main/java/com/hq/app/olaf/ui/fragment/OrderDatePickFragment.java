package com.hq.app.olaf.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.activity.order.OrderDetailActivity;
import com.hq.app.olaf.ui.adapter.OrderAdapter;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.login.Operators;
import com.hq.app.olaf.ui.bean.order.Order;
import com.hq.app.olaf.ui.bean.page.Pager;
import com.hq.app.olaf.ui.enums.PayChannelEnum;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.annotation.Layout;
import com.hq.component.date.DateTime;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.TextHelper;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;
import com.hq.uicomponet.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.fragment_datepicker_order)
public class OrderDatePickFragment extends HqPayFragment implements IQuery, DatePickerDialog.OnDateSetListener {
    @Bind(R.id.dateStart) TextView dateStart;
    @Bind(R.id.dateEnd) TextView dateEnd;
    @Bind(R.id.pickDateLayout) LinearLayout pickDateLayout;
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.loadingView) LoadingView loadingView;

    protected int position;
    protected HttpTask getOrders;
    private DateTime startDate;
    private DateTime endDate;
    private DateTime nowDate;
    private OrderAdapter orderAdapter;
    private String operatorId;
    private PayChannelEnum channel = PayChannelEnum.ALL;
    private OrderFragment orderFragment;
    private Pager<Order> pager;

    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 5;

    public static OrderDatePickFragment newInstance(String operatorId , String title, int position) {
        OrderDatePickFragment fragment = new OrderDatePickFragment();
        fragment.position = position;
        fragment.setTITLE(title);
        fragment.operatorId = operatorId;
        return fragment;
    }

    @Override
    public void init() {
        orderFragment = (OrderFragment) getParentFragment();
        channel = orderFragment.getChannel();

        nowDate = DateTime.now(TimeZone.getDefault());
        endDate = nowDate;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        orderAdapter = new OrderAdapter(getContext());
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setErrorView(R.layout.layout_recyclerview_error);
        orderAdapter.setNoMore(R.layout.layout_recyclerview_nomore);
        orderAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Order order = orderAdapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putParcelable(OrderListFragment.ORDER_ITEM, order);
                forward(OrderDetailActivity.class, bundle);
            }
        });
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        orderAdapter.setMore(R.layout.layout_recyclerview_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreList();
            }
        });

        loadingView.setRetryListener(new RetryListener() {
            @Override
            public void retry() {
                getList();
            }
        });
    }

    @Override
    public void getList() {
        channel = orderFragment.getChannel();
       /* Operators operators = Session.load().getDefaultSettle();
        if (operators == null || !"3".equals(operators.getState())) {
            loadingView.setEmptyText("您当前没有订单信息");
            loadingView.noData();
            return;
        }*/
        getOrders = HttpService.getInstance().getOrders(operatorId,
                        startDate.format("YYYYMMDD"),
                        endDate.format("YYYYMMDD"), null, channel.getCode());
        NetTools.excute(getOrders, loadingView, new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    orderAdapter.clear();
                    orderAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    pager=  JSONObject.parseObject((String) taskResult.getResult(),
                            new TypeReference<Pager<Order>>() {
                            }.getType());
                    orderAdapter.refresh(pager.getList());
                   orderAdapter.resumeMore();
                } else if (taskResult.isEmptyData()) {
                    if (loadingView != null) {
                        loadingView.showProgress();
                        loadingView.setEmptyText("您当前没有订单信息");
                        loadingView.noData();
                        orderAdapter.clear();
                    }
                }
            }
        });
    }

    @Override
    public void refreshList() {
        this.channel = orderFragment.getChannel();
        if (startDate == null) return;
       /* Operators operators = Session.load().getDefaultSettle();
        if (operators == null || !"3".equals(operators.getState())) {
            loadingView.setEmptyText("您当前没有订单信息");
            loadingView.noData();
            return;
        }*/
        getOrders = HttpService.getInstance().getOrders(operatorId,
                        startDate.format("YYYYMMDDhhmmss"),
                        endDate.format("YYYYMMDDhhmmss"), null, channel.getCode());
        Progress progress = orderAdapter.getCount() > 0 ? null : loadingView;
        if (progress == null) {
            recyclerView.setRefreshing(true);
        }
        NetTools.excute(getOrders, progress, new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                TaskStatus status = taskResult.getStatus();
                recyclerView.setRefreshing(false);
                if (status.isSuccess()) {
                    loadingView.hideProgress();
                    orderAdapter.clear();
                    pager=  JSONObject.parseObject((String) taskResult.getResult(),
                            new TypeReference<Pager<Order>>() {
                            }.getType());
                    orderAdapter.refresh(pager.getList());
                    orderAdapter.resumeMore();
                } else if (status.isDataEmpty()) {
                    if (loadingView != null) {
                        loadingView.showProgress();
                        loadingView.setEmptyText("您当前没有订单信息");
                        loadingView.noData();
                        orderAdapter.clear();
                    }
                }
            }
        });
    }

    @Override
    public void loadMoreList() {
        this.channel = orderFragment.getChannel();
        Order order = orderAdapter.getItem(orderAdapter.getCount() - 1);
        getOrders = HttpService.getInstance().getOrders(operatorId,
                        startDate.format("YYYYMMDD"),
                        endDate.format("YYYYMMDD"),
                pager.getPages(),
                channel.getCode()
        );
        NetTools.excute(getOrders, new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                TaskStatus status = taskResult.getStatus();
                if (status.isSuccess()) {
                    pager=  JSONObject.parseObject((String) taskResult.getResult(),
                            new TypeReference<Pager<Order>>() {
                            }.getType());
                    orderAdapter.loadMore(pager.getList());

                } else if (status.isDataEmpty()) {
                    if (loadingView != null) {
                        orderAdapter.stopMore();
                        taskResult.setError("没有更多数据");
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @OnClick(R.id.queryOrder)
    public void queryOrder() {
        if (checkDate()) {
            Operators operators =new Operators();// Session.load().getDefaultSettle();
            if (operators == null || !"3".equals(operators.getState())) {
                loadingView.setEmptyText("您当前没有订单信息");
                loadingView.noData();
            } else {
                showLoadData();
                orderAdapter.clear();
                orderAdapter.notifyDataSetChanged();
                recyclerView.setRefreshing(true);
                getList();
            }
        }
    }

    /**
     * 检查日期选择
     */
    private boolean checkDate() {
        if (startDate == null) {
            showSnackBar(dateEnd, "请先选择开始时间");
            return false;
        }

        if (startDate.gt(endDate)) {
            showSnackBar(dateEnd, "开始时间不能大于结束时间");
            return false;
        }
        DateTime dateTime = endDate.minus(0, 1, 0,
                endDate.getHour() == null ? 0 : endDate.getHour(),
                endDate.getMinute() == null ? 0 : endDate.getMinute(),
                endDate.getSecond() == null ? 0 : endDate.getSecond(),
                endDate.getNanoseconds() == null ? 0 : endDate.getNanoseconds(),
                DateTime.DayOverflow.LastDay);
        dateTime = DateTime.forDateOnly(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        if (dateTime.gt(startDate)) {
            showSnackBar(dateEnd, "所选时间区域不能超过一个月");
            return false;
        }
        return true;
    }

    /**
     * 显示时间选择
     */
    private void showPickDate() {
        if (pickDateLayout != null) {
            pickDateLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示数据加载
     */
    private void showLoadData() {
        pickDateLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * 取消网络请求
     */
    @Override
    public void cancel() {
        if (getOrders != null) {
            ThreadPoolTool.getInstance().cancel(getOrders.getTAG());
        }
    }

    /**
     * 选择开始时间
     */
    @OnClick(R.id.dateStartLayout)
    public void showDateStart() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                startDate.getYear(),
                startDate.getMonth()-1,
                startDate.getDay()
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(getActivity().getFragmentManager(), "startDate");
    }

    /**
     * 选择结束时间
     */
    @OnClick(R.id.dateEndLayout)
    public void showDateEnd() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                endDate.getYear(),
                endDate.getMonth()-1,
                endDate.getDay()
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(getActivity().getFragmentManager(), "endDate");
    }

    /**
     * 选中当前页时重置回时间选择
     */
    public void resetData() {
        showPickDate();
        endDate = DateTime.now(TimeZone.getDefault());
        startDate = endDate.minus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.FirstDay);
        TextHelper.setText(dateStart, startDate.format("M月DD日"));
        TextHelper.setText(dateEnd, endDate.format("M月DD日"));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        DateTime dateTime = DateTime.forDateOnly(year, monthOfYear + 1, dayOfMonth);
        if ("startDate".equals(view.getTag())) {
            startDate = dateTime;
            TextHelper.setText(dateStart, dateTime.format("M月DD日"));
        } else {
            endDate = dateTime;
            TextHelper.setText(dateEnd, dateTime.format("M月DD日"));
        }
    }

    public int getPosition() {
        return position;
    }
}
