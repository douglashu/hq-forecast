package com.hq.app.olaf.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.adapter.Index1Adapter;
import com.hq.app.olaf.ui.adapter.Index2Adapter;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.component.annotation.Layout;
import com.hq.component.utils.TextHelper;


import com.hq.app.olaf.R;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;

import butterknife.Bind;

/**
 * Created by huwentao on 16/9/22.
 */
@Layout(layoutId = R.layout.fragment_new_index)
public class NewIndexFragment extends HqPayFragment {
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title) TextView title;

    private RecyclerView.Adapter adapter = null;

    @Override
    public void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        Session session = Session.load();
        getAppActivity().initToolbar(toolbar);
        ActionBar actionBar = getAppActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle("");
        }

        if (TextUtils.isEmpty(session.getUserId() )) {
            TextHelper.setText(title, "慧钱");
            adapter = new Index1Adapter((NewIndexActivity) getAppActivity(), recyclerView);
        } else {
            if (session.getMchs() .size()>0 && !TextUtils.isEmpty(session.getMchs().get(0).getMchShortName())) {
                TextHelper.setText(title, session.getMchs().get(0).getMchShortName());
            } else {
                TextHelper.setText(title, "慧钱");
            }

            adapter = new Index1Adapter((NewIndexActivity) getAppActivity(), recyclerView);
            //老板
            /*if ("5510161200001002".equals(session.getRole().getRoleId())
                   // || "1474424723721".equals(session.getUserRoleId())
                    ) {
                adapter = new Index2Adapter((NewIndexActivity) getAppActivity(), recyclerView, refreshLayout);
            } else if ("1474424723719".equals(session.getRole().getRoleId())) {// 店小二
                adapter = new Index1Adapter((NewIndexActivity) getAppActivity(), refreshLayout);
            } else if (TextUtils.isEmpty(session.getRole().getRoleId())) {
                adapter = new Index1Adapter((NewIndexActivity) getAppActivity(), refreshLayout);
            }*/
        }
        recyclerView.setAdapter(adapter);
        //设置进度动画的颜色。
        recyclerView.setRefreshingColorResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_3,R.color.refresh_color_4);
        //设置手势滑动监听器
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapter instanceof Index1Adapter) {
                    ((Index1Adapter) adapter).loadInfo();
                } else if (adapter instanceof Index2Adapter) {
                    ((Index2Adapter) adapter).loadInfo();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter instanceof Index1Adapter) {
            ((Index1Adapter) adapter).loadInfo();
        } else if (adapter instanceof Index2Adapter) {
            ((Index2Adapter) adapter).loadInfo();
        }
    }

    public void notifyMenuItemData(){
        if(adapter instanceof Index1Adapter){
            ((Index1Adapter)adapter).notifyMenuItemData();
        }
    }
}
