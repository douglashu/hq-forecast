package com.hq.app.olaf.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.component.annotation.Layout;
import com.google.gson.Gson;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.activity.order.OrderDetailActivity;
import com.hq.app.olaf.ui.adapter.OrderAdapter;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.order.Order;
import com.hq.app.olaf.ui.bean.page.Pager;
import com.hq.app.olaf.ui.enums.PayChannelEnum;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.date.DateTime;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;
import com.hq.uicomponet.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.Bind;

@Layout(layoutId = R.layout.fragment_order_list)
public class OrderListFragment extends HqPayFragment implements IQuery {
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.loadingView) LoadingView loadingView;

    public final static String ORDER_ITEM = "orderItem";
    private int position;
    private HttpTask getOrders;
    private OrderAdapter orderAdapter;
    private DateTime startTime;
    private DateTime endTime;
    private String operatorId;
    private PayChannelEnum channel = PayChannelEnum.ALL;
    private OrderFragment orderFragment;
    private Pager<Order> pager=new Pager<Order>();
    Gson gson = new Gson();

    private  int currPage=0;
    public OrderListFragment() {
    }

    public static OrderListFragment newInstance(String operatorId, String title, int position, DateTime startTime, DateTime endTime) {
        OrderListFragment fragment = new OrderListFragment();
        fragment.position = position;
        fragment.setTITLE(title);
        fragment.startTime = startTime;
        fragment.endTime = endTime;
        fragment.operatorId = operatorId;
        return fragment;
    }

    @Override
    public void init() {
        orderFragment = (OrderFragment) getParentFragment();
        channel = orderFragment.getChannel();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        orderAdapter = new OrderAdapter(getContext());
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshingColorResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_3,R.color.refresh_color_4);
        recyclerView.setErrorView(R.layout.layout_recyclerview_error);
        orderAdapter.setNoMore(R.layout.layout_recyclerview_nomore);

        orderAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Order order = orderAdapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ORDER_ITEM, order);
                bundle.putString("operatorId", operatorId);
                forward(OrderDetailActivity.class, bundle);
            }
        });
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Logger.d("---------onRefresh---------");
                refreshList();
            }
        });


        orderAdapter.setMore(R.layout.layout_recyclerview_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Logger.d("---------onLoadMore---------");
                loadMoreList();
            }
        });
       /* Operators operators = Session.load().getDefaultSettle();
        if (operators == null || !"3".equals(operators.getState())) {
            loadingView.setEmptyText("您当前没有订单信息");
            loadingView.noData();
        } else {
        }*/


        //默认加载今天的数据
        if (position==1) {
            getList();
        }

        loadingView.setRetryListener(new RetryListener() {
            @Override
            public void retry() {
                Logger.d("---------retry---------");
                getList();
            }
        });
        Logger.d("---------init---------");
    }

    @Override
    public void getList() {
        currPage=1;
        Logger.d("---------getList---------");
        if (orderAdapter != null && orderAdapter.getCount() > 0) {
            return;
        }
        channel = orderFragment.getChannel();
       /* Operators operators = Session.load().getDefaultSettle();
        if (operators == null || !"3".equals(operators.getState())) {
            loadingView.setEmptyText("您当前没有订单信息");
            loadingView.noData();
            return;
        }*/
        if (orderAdapter == null) return;
        getOrders = HttpService.getInstance().getOrders(operatorId,
                        startTime.format("YYYYMMDD"),
                        endTime.format("YYYYMMDD"), currPage, channel.getCode());
        NetTools.excute(getOrders, loadingView, new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    orderAdapter.clear();
                    pager=  JSONObject.parseObject((String) taskResult.getResult(),
                            new TypeReference<Pager<Order>>() {
                            }.getType());
                    orderAdapter.refresh(pager.getList());
                    //orderAdapter.resumeMore();
                } else if (taskResult.isEmptyData()) {
                    if (loadingView != null) {
                        loadingView.showProgress();
                        loadingView.setEmptyText("您当前没有订单信息");
                        loadingView.noData();
                        orderAdapter.refresh(new ArrayList<Order>());
                    }
                }
            }
        });
    }

    @Override
    public void refreshList() {
        Logger.d("---------refreshList---------");
        currPage=1;
        if (orderFragment == null) return;
        channel = orderFragment.getChannel();
      /*  Operators operators = Session.load().getDefaultSettle();
        if (operators == null || !"3".equals(operators.getState())) {
            loadingView.setEmptyText("您当前没有订单信息");
            loadingView.noData();
            return;
        }*/
        if (orderAdapter == null) return;
        getOrders = HttpService.getInstance().getOrders(operatorId,
                        startTime.format("YYYYMMDD"),
                        endTime.format("YYYYMMDD"), currPage, channel.getCode());
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
                   // orderAdapter.resumeMore();
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
        if(orderAdapter.getCount()>0){
            //Order order = orderAdapter.getItem(orderAdapter.getCount() - 1);
            currPage++;
            getOrders = HttpService.getInstance().getOrders(operatorId,
                    startTime.format("YYYYMMDD"),
                    endTime.format("YYYYMMDD"),
                    currPage,
                    channel.getCode()
            );
            NetTools.excute(getOrders, new HqNetCallBack(getView()) {
                @Override
                public void doComplete(TaskResult taskResult) {
                    TaskStatus status = taskResult.getStatus();
                    if (taskResult.isSuccess()) {
                        pager=  JSONObject.parseObject((String) taskResult.getResult(),
                                new TypeReference<Pager<Order>>() {
                                }.getType());
                        orderAdapter.loadMore(pager.getList());
                    } else if (status.isDataEmpty()) {
                        orderAdapter.stopMore();
                        taskResult.setError("没有更多数据");
                        orderAdapter.notifyDataSetChanged();
//                    orderAdapter.stopMore();
//                    RequestFailedHandler.handleFailed(getView(),taskResult);
                    }
                }
            });
        }

    }


    @Override
    public void cancel() {
        if (getOrders != null) {
            ThreadPoolTool.getInstance().cancel(getOrders.getTAG());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        cancel();
    }

    public int getPosition() {
        return position;
    }
}
