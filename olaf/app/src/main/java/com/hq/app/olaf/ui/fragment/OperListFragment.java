package com.hq.app.olaf.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.reflect.TypeToken;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.activity.OperatorRightActivity;
import com.hq.app.olaf.ui.adapter.OperatorListAdapter;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.bean.order.Order;
import com.hq.app.olaf.ui.bean.page.Pager;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.RetryListener;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.ThreadPoolTool;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.JSON;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;
import com.hq.uicomponet.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@Layout(layoutId = R.layout.fragment_oper_list)
public class OperListFragment extends HqPayFragment implements IQuery {
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.loadingView) LoadingView loadingView;
    private int currPage;
    private HttpTask getOpers;
    private OperatorListAdapter operatorListAdapter;

    @Override
    public void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        operatorListAdapter = new OperatorListAdapter(getContext());
        recyclerView.setAdapter(operatorListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshingColorResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_3,R.color.refresh_color_4);
        recyclerView.setErrorView(R.layout.layout_recyclerview_error);
        operatorListAdapter.setNoMore(R.layout.layout_recyclerview_nomore);

        operatorListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Operator oper = operatorListAdapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putBoolean(OperatorRightActivity.OPERATOR_FUNC, false);
                getApp().saveCache(oper);
                forward(OperatorRightActivity.class, bundle);
            }
        });
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Logger.d("---------onRefresh---------");
                refreshList();
            }
        });


        operatorListAdapter.setMore(R.layout.layout_recyclerview_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
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


        //默认加载的数据
            getList();

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
        if (operatorListAdapter == null) return;
        getOpers = HttpService.getInstance().getOperators();
        final Progress progress = operatorListAdapter.getCount() > 0 ? null : loadingView;
        if (progress == null) {
            recyclerView.setRefreshing(true);
        }
        NetTools.excute(getOpers, progress, new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if(progress==null){
                    recyclerView.setRefreshing(false);
                }
                if (taskResult.isSuccess()) {
                    operatorListAdapter.clear();
                    List<Operator> operators = JSON.parseArray((String) taskResult.getResult(), new TypeToken<List<Operator>>() {
                    });
                    operatorListAdapter.refresh(operators);
                    //orderAdapter.resumeMore();
                } else if (taskResult.isEmptyData()) {
                    if (loadingView != null) {
                        loadingView.showProgress();
                        loadingView.setEmptyText("您还没有下属操作员");
                        loadingView.noData();
                        operatorListAdapter.refresh(new ArrayList<Operator>());
                    }
                } 
            }
        });
    }

    @Override
    public void refreshList() {
        getList();
    }

    @Override
    public void loadMoreList() {
        loadingView.hideProgress();
        recyclerView.setRefreshing(false);
        operatorListAdapter.stopMore();
//                        operatorListAdapter.clear();
    }

//    @Override
//    public void refreshList() {
//        Logger.d("---------refreshList---------");
//        currPage=1;
//        if (orderFragment == null) return;
//        channel = orderFragment.getChannel();
//      /*  Operators operators = Session.load().getDefaultSettle();
//        if (operators == null || !"3".equals(operators.getState())) {
//            loadingView.setEmptyText("您当前没有订单信息");
//            loadingView.noData();
//            return;
//        }*/
//        if (orderAdapter == null) return;
//        getOrders = HttpService.getInstance().getOrders(operatorId,
//                        startTime.format("YYYYMMDD"),
//                        endTime.format("YYYYMMDD"), currPage, channel.getCode());
//        Progress progress = orderAdapter.getCount() > 0 ? null : loadingView;
//        if (progress == null) {
//            recyclerView.setRefreshing(true);
//        }
//        NetTools.excute(getOrders, progress, new NetTools.CallBack() {
//            @Override
//            public void onComplete(TaskResult taskResult) {
//                TaskStatus status = taskResult.getStatus();
//                recyclerView.setRefreshing(false);
//                if (status.isSuccess()) {
//                    loadingView.hideProgress();
//                    orderAdapter.clear();
//                    pager=  JSONObject.parseObject((String) taskResult.getResult(),
//                            new TypeReference<Pager<Order>>() {
//                            }.getType());
//                    orderAdapter.refresh(pager.getList());
//                   // orderAdapter.resumeMore();
//                } else if (status.isDataEmpty()) {
//                    if (loadingView != null) {
//                        loadingView.showProgress();
//                        loadingView.setEmptyText("您当前没有订单信息");
//                        loadingView.noData();
//                        orderAdapter.clear();
//                    }
//                } else {
//                    RequestFailedHandler.handleFailed(getView(), taskResult);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void loadMoreList() {
//        this.channel = orderFragment.getChannel();
//        if(orderAdapter.getCount()>0){
//            //Order order = orderAdapter.getItem(orderAdapter.getCount() - 1);
//            currPage++;
//            getOrders = HttpService.getInstance().getOrders(operatorId,
//                    startTime.format("YYYYMMDD"),
//                    endTime.format("YYYYMMDD"),
//                    currPage,
//                    channel.getCode()
//            );
//            NetTools.excute(getOrders, new NetTools.CallBack() {
//                @Override
//                public void onComplete(TaskResult taskResult) {
//                    TaskStatus status = taskResult.getStatus();
//                    if (taskResult.isSuccess()) {
//                        pager=  JSONObject.parseObject((String) taskResult.getResult(),
//                                new TypeReference<Pager<Order>>() {
//                                }.getType());
//                        orderAdapter.loadMore(pager.getList());
//                    } else if (status.isDataEmpty()) {
//                        orderAdapter.stopMore();
//                        taskResult.setError("没有更多数据");
//                        orderAdapter.notifyDataSetChanged();
////                    orderAdapter.stopMore();
////                    RequestFailedHandler.handleFailed(getView(),taskResult);
//                    } else {
//                        RequestFailedHandler.handleFailed(getView(), taskResult);
//                    }
//                }
//            });
//        }
//
//    }


    @Override
    public void cancel() {
        if (getOpers != null) {
            ThreadPoolTool.getInstance().cancel(getOpers.getTAG());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        cancel();
    }

}
