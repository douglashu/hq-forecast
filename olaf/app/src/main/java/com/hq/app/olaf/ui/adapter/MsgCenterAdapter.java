package com.hq.app.olaf.ui.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.bean.msgcenter.PushMessage;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.date.DateTime;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


import com.hq.app.olaf.R;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16/11/12.
 */

public class MsgCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final EasyRecyclerView recyclerView;
    private final LoadingView loadingView;
    private LayoutInflater inflater;
    private Context mContext;
    private List<PushMessage> pushMessages = new ArrayList<>();

    private final static int VIEWTYPE_FEEREMIND = 0;
    private static final int VIEWTYPE_LOADMORE = 2;
    private static final int VIEWTYPE_NODATA = 3;
    private static final int VIEWTYPE_LOADING = 4;
    private static final int VIEWTYPE_LOADFAILED = 5;
    private int totalItemCount;
    private int lastVisibleItemPosition;
    private boolean isLoadMore = false;
    private boolean isNoData = false;
    private boolean isLoading = false;
    private boolean isLoadFailed = false;
    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 5;

    public MsgCenterAdapter(Context mContext, EasyRecyclerView recyclerView, LoadingView loadingView) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.recyclerView = recyclerView;
        this.loadingView = loadingView;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getRecyclerView().getLayoutManager();
   /*     recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
//                Log.d("test", "totalItemCount =" + totalItemCount + "-----" + "lastVisibleItemPosition =" + lastVisibleItemPosition);
                if (!isNoData && !isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                    //此时是刷新状态
                    loadMore();
                }
            }
        });

        loadMessages();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });*/
        setNoData();
        return;
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        HttpTask httpTask = HttpService.getInstance().management_PushMessage_getMessages(0);
        NetTools.excute(httpTask, new HqNetCallBack(recyclerView) {
            @Override
            public void doComplete(TaskResult taskResult) {
                //refreshLayout.setRefreshing(false);
                if (taskResult.isSuccess()) {
                    String jsonResult = (String) taskResult.getResult();
                    List<PushMessage> messageList = JSON.parseArray(jsonResult, PushMessage.class);
                    refreshMessage(messageList);
                    setLoadMoreData();
                }
            }

            @Override
            protected void dofinally(TaskResult taskResult) {
                    if (taskResult.isEmptyData()) {
                        setNoData();
                    } else {
                        // RequestFailedHandler.handleFailed(refreshLayout, taskResult);
                        setLoadFailed();
                    }
            }

            @Override
            public void onComplete(Map<String, TaskResult> taskResults) {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 载入数据
     */
    private void loadMessages() {
        setLoadingData();
        HttpTask httpTask = HttpService.getInstance().management_PushMessage_getMessages(0);
        NetTools.excute(httpTask, new HqNetCallBack(recyclerView) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    String jsonResult = (String) taskResult.getResult();
                    List<PushMessage> messageList = JSON.parseArray(jsonResult, PushMessage.class);
                    refreshMessage(messageList);
                    setLoadMoreData();
                }
            }

            @Override
            protected void dofinally(TaskResult taskResult) {
                    if (taskResult.isEmptyData()) {
                        setNoData();
                    } else {
                        //RequestFailedHandler.handleFailed(refreshLayout, taskResult);
                        setLoadFailed();
                    }
            }

            @Override
            public void onComplete(Map<String, TaskResult> taskResults) {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 载入更多数据
     */
    public void loadMore() {
        if (isNoData()) return;
        setLoadingData();
        HttpTask httpTask = HttpService.getInstance().management_PushMessage_getMessages(pushMessages.size());
        NetTools.excute(httpTask, new NetTools.CallBack() {
            @Override
            public void onComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    String jsonResult = (String) taskResult.getResult();
                    List<PushMessage> messageList = JSON.parseArray(jsonResult, PushMessage.class);
                    loadMoreMessage(messageList);
                    setLoadMoreData();
                } else {
                    if (taskResult.isEmptyData()) {
                        setNoData();
                    } else {
                        //RequestFailedHandler.handleFailed(refreshLayout, taskResult);
                        setLoadFailed();
                    }
                }
            }

            @Override
            public void onComplete(Map<String, TaskResult> taskResults) {
                notifyDataSetChanged();
            }
        });
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
        if (loadingView != null) {
            loadingView.showProgress();
            loadingView.setEmptyText("您当前还没有消息");
            loadingView.noData();
        }
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        if (viewType == VIEWTYPE_FEEREMIND) {
            view = inflater.inflate(R.layout.layout_messagecenter_feeremind_item, parent, false);
            viewHolder = new FeeRemind(view);
        } else if (viewType == VIEWTYPE_LOADMORE) {
            view = inflater.inflate(R.layout.layout_recyclerview_more, parent, false);
            return new LOADMORE(view);
        } else if (viewType == VIEWTYPE_NODATA) {
            view = inflater.inflate(R.layout.layout_recyclerview_nomore, parent, false);
            return new NODATA(view);
        } else if (viewType == VIEWTYPE_LOADING) {
            view = inflater.inflate(R.layout.layout_recyclerview_loading, parent, false);
            return new LOADING(view);
        } else if (viewType == VIEWTYPE_LOADFAILED) {
            view = inflater.inflate(R.layout.layout_recyclerview_loadfailed, parent, false);
            return new LOADING(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int tempPosition = position;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEWTYPE_FEEREMIND:
                final PushMessage pushMessage = pushMessages.get(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick(tempPosition, pushMessage);
                        }
                    }
                });
                FeeRemind feeRemind = (FeeRemind) holder;
                feeRemind.titleView.setText(pushMessage.getTitle());
                DateTime dateTime = DateTime.forInstant(pushMessage.getTimestamp(), TimeZone.getDefault());
                feeRemind.dateStringView.setText(dateTime.format("MM月DD日"));
                feeRemind.contentView.setText(pushMessage.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return pushMessages.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMore && position == getItemCount() - 1) {
            return VIEWTYPE_LOADMORE;
        } else if (isNoData && position == getItemCount() - 1) {
            return VIEWTYPE_NODATA;
        } else if (isLoading && position == getItemCount() - 1) {
            return VIEWTYPE_LOADING;
        } else if (isLoadFailed && position == getItemCount() - 1) {
            return VIEWTYPE_LOADFAILED;
        } else {
            return VIEWTYPE_FEEREMIND;
        }
    }

    /**
     * 费率信息提醒
     */
    class FeeRemind extends RecyclerView.ViewHolder {
        @Bind(R.id.title) TextView titleView;
        @Bind(R.id.dateString) TextView dateStringView;
        @Bind(R.id.content) TextView contentView;

        public FeeRemind(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public List<PushMessage> getPushMessages() {
        return pushMessages;
    }

    /**
     * 刷新显示数据
     *
     * @param pushMessages
     */
    public void refreshMessage(List<PushMessage> pushMessages) {
        this.pushMessages.clear();
        if (pushMessages != null && pushMessages.size() > 0) {
            this.pushMessages.addAll(pushMessages);
            notifyDataSetChanged();
        }
    }

    /**
     * 插入显示消息
     *
     * @param pushMessage
     */
    public void insertMessage(int position, List<PushMessage> pushMessage) {
        if (pushMessage != null && pushMessage.size() > 0) {
            pushMessages.addAll(position, pushMessage);
            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多数据
     *
     * @param pushMessages
     */
    public void loadMoreMessage(List<PushMessage> pushMessages) {
        if (pushMessages != null && pushMessages.size() > 0) {
            this.pushMessages.addAll(pushMessages);
            notifyDataSetChanged();
        }
    }

    private ItemClickListener itemClickListener;

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, PushMessage pushMessage);
    }
}
