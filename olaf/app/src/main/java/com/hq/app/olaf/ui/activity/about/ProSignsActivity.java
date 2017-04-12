package com.hq.app.olaf.ui.activity.about;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpMessage;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.activity.about.adapter.ProSignsAdapter;
import com.hq.app.olaf.ui.activity.passwd.ForgetPwdActitivy;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.about.ProSigns;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.util.RequestFailedHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@Layout(layoutId = R.layout.activity_pro_signs)
public class ProSignsActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.listview)
    ListView listView;

    ProSignsAdapter proSignsAdapter;

    List<ProSigns> proSigns ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        setActTitle("签约信息");
        proSignsAdapter=new ProSignsAdapter(this);

        listView.setAdapter(proSignsAdapter);

        proSigns =new ArrayList<ProSigns>();

        HttpTask putAccount = HttpService.getInstance().getProdSigns();
        NetTools.excute(putAccount, new LoadingDialog(this), new HqNetCallBack(getRootView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    proSigns = JSONArray.parseArray((String)taskResult.getResult(), ProSigns.class);
                    if(proSigns!=null && proSigns.size()>0) {
                        proSignsAdapter.setList(proSigns);
                    }
                }
            }
        });
    }


}
