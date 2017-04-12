package com.hq.app.olaf.ui.activity.msgcenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;


import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.adapter.MsgCenterAdapter;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.component.annotation.Layout;


import com.hq.app.olaf.R;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;

import butterknife.Bind;

@Layout(layoutId = R.layout.activity_msg_center)
public class MsgCenterActivity extends HqPayActivity {
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.loadingView)
    LoadingView loadingView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MsgCenterAdapter msgCenterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        msgCenterAdapter = new MsgCenterAdapter(this, recyclerView, loadingView);
        recyclerView.setAdapter(msgCenterAdapter);
        recyclerView.setRefreshingColorResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_3,R.color.refresh_color_4);
    }
}
