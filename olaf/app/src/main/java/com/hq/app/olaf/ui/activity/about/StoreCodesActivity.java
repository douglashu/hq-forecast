package com.hq.app.olaf.ui.activity.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.ui.activity.about.adapter.ProSignsAdapter;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.activity.about.adapter.StoreCodesAdapter;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.about.ProSigns;
import com.hq.app.olaf.ui.bean.about.StoreCodes;
import com.hq.app.olaf.util.RequestFailedHandler;

import java.util.List;

import butterknife.Bind;

@Layout(layoutId = R.layout.activity_store_codes)
public class StoreCodesActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.store_codes_listview)
    ListView listView;

    StoreCodesAdapter storeCodesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        setActTitle("我的设备");

        storeCodesAdapter=new StoreCodesAdapter(this);

        listView.setAdapter(storeCodesAdapter);

        HttpTask putAccount = HttpService.getInstance().getStoreCodes();
        NetTools.excute(putAccount, new LoadingDialog(this), new HqNetCallBack(getRootView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    List<StoreCodes> storeCodes = JSONArray.parseArray((String) taskResult.getResult(), StoreCodes.class);
                    if(storeCodes!=null && storeCodes.size()>0) {
                        storeCodesAdapter.setList(storeCodes);
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreCodes storeCodes=(StoreCodes)storeCodesAdapter.getItem(position);
                if(storeCodes!=null){
                    Bundle bundle=new Bundle();
                    bundle.putString(ShoukuanMaActivity.composeUrlKey,storeCodes.getComposeUrl());
                    forward(ShoukuanMaActivity.class,bundle);
                }

            }
        });
    }
}
