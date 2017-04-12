package com.hq.app.olaf.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hq.app.olaf.net.LoadingView;
import com.hq.component.annotation.Layout;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.adapter.MsgCenterAdapter;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

@Layout(layoutId = R.layout.activity_msg_center)
public class MessageFragment extends HqPayFragment {

    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.loadingView)
    LoadingView loadingView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;


    /*@Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;*/

    private MsgCenterAdapter msgCenterAdapter;

    @Override
    public void init() {
        title.setText("消息");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getAppActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        msgCenterAdapter = new MsgCenterAdapter(this.getContext(), recyclerView,loadingView);
        recyclerView.setAdapter(msgCenterAdapter);
        recyclerView.setRefreshingColorResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_3,R.color.refresh_color_4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }


}
