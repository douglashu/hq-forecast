package com.hq.app.olaf.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.app.Apps;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.util.SharedPrefUtil;
import com.hq.component.network.service.impl.HttpTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwentao on 16/9/20.
 */

public class Index2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    private static final int TYPE_HEADER_POSITION = 0;
    private static final int TYPE_PROFIT_POSITION = 1;
    private static final int TYPE_LOADMORE = 2;
    private static final int TYPE_NODATA = 3;
    private static final int TYPE_LOADING = 4;
    private static final int TYPE_LOADFAILED = 5;


    private Session session = Session.load();
    private NewIndexActivity newIndex;
    private List<Apps> appsList = new ArrayList<>();
    private SharedPrefUtil sharedPref;
    private DecimalFormat format = new DecimalFormat("###,###,##0.00");
    private int totalItemCount;
    private int lastVisibleItemPosition;
    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 5;
    private boolean isLoadMore = false;
    private boolean isNoData = false;
    private boolean isLoading = false;
    private boolean isLoadFailed = false;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private HttpTask getAssets;
    private HttpTask queryOrder;

    public Index2Adapter(NewIndexActivity context, RecyclerView recyclerView, SwipeRefreshLayout refreshLayout) {
        this.newIndex = context;
        this.sharedPref = SharedPrefUtil.getInstance();
        this.recyclerView = recyclerView;
        this.refreshLayout = refreshLayout;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("test", "totalItemCount =" + totalItemCount + "-----" + "lastVisibleItemPosition =" + lastVisibleItemPosition);
                if (!isNoData && !isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                    //此时是刷新状态
                    //loadMore();
                }
            }
        });
        loadInfo();
    }

    /**
     *
     */
    public void loadInfo() {
        queryOrder = HttpService.getInstance().getScanPayRecords(null);
        getAssets = HttpService.getInstance().getAssets(session.getMobile());
    //    NetTools.excute(queryCallback, queryOrder, getAssets);
    }


    @Override
    public int getItemCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER_POSITION;
        } else if (isLoadMore && position == getItemCount() - 1) {
            return TYPE_LOADMORE;
        } else if (isNoData && position == getItemCount() - 1) {
            return TYPE_NODATA;
        } else if (isLoading && position == getItemCount() - 1) {
            return TYPE_LOADING;
        } else if (isLoadFailed && position == getItemCount() - 1) {
            return TYPE_LOADFAILED;
        } else {
            return TYPE_PROFIT_POSITION;
        }
    }




    /**
     * 加载更多
     */
    public void setLoadMoreData() {
        isLoadMore = true;
        isLoading = false;
        isNoData = false;
        isLoadFailed = false;
//        notifyDataSetChanged();
    }

    /**
     * 加载数据中
     */
    public void setLoadingData() {
        isLoadMore = false;
        isLoading = true;
        isNoData = false;
        isLoadFailed = false;
//        notifyDataSetChanged();
    }

    /**
     * 无更多数据
     */
    public void setNoData() {
        isLoadMore = false;
        isLoading = false;
        isNoData = true;
        isLoadFailed = false;
//        notifyDataSetChanged();
    }

    /**
     * 加载失败
     */
    public void setLoadFailed() {
        isLoadMore = false;
        isLoading = false;
        isNoData = false;
        isLoadFailed = true;
//        notifyDataSetChanged();
    }

    /**
     * @return
     */
    public boolean isNoData() {
        return isNoData;
    }

    /**
     * @return
     */
    public boolean isLoading() {
        return isLoading;
    }

    /**
     * @return
     */
    public boolean isLoadMore() {
        return isLoadMore;
    }

    /**
     * @return
     */
    public boolean isLoadFailed() {
        return isLoadFailed;
    }



    /**
     *
     */
    public class LOADMORE extends RecyclerView.ViewHolder {

        public LOADMORE(View itemView) {
            super(itemView);
        }
    }

    /**
     *
     */
    public class NODATA extends RecyclerView.ViewHolder {
        public NODATA(View itemView) {
            super(itemView);
        }
    }

    /**
     *
     */
    public class LOADING extends RecyclerView.ViewHolder {

        public LOADING(View itemView) {
            super(itemView);
        }
    }

    /**
     * 页面跳转
     *
     * @param aClass
     * @param bundle
     */
    private void forward(Class<? extends HqPayActivity> aClass, Bundle bundle) {
        Intent intent = new Intent(newIndex, aClass);
        newIndex.startActivity(intent, bundle);
    }
}
