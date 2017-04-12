package com.hq.app.olaf.ui.activity.about;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.TextHelper;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.about.MerchantInfo;
import com.hq.app.olaf.ui.bean.about.ProSigns;
import com.hq.app.olaf.util.RequestFailedHandler;

import java.util.List;

import butterknife.Bind;


@Layout(layoutId = R.layout.activity_merchant_info)
public class MerchantInfoActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.mercId) TextView mercId;
    @Bind(R.id.mercName) TextView mercName;
    @Bind(R.id.mercShotName) TextView mercShotName;
    @Bind(R.id.busiLicenseProvAndCity) TextView busiLicenseProvAndCity;
    @Bind(R.id.mercAddr) TextView mercAddr;
    @Bind(R.id.busiScope) TextView busiScope;
    @Bind(R.id.contactName) TextView contactName;
    @Bind(R.id.contactMobile) TextView contactMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        setActTitle("营业信息");

        HttpTask putAccount = HttpService.getInstance().getMch();
        NetTools.excute(putAccount, new LoadingDialog(this), new HqNetCallBack(getRootView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    MerchantInfo merchantInfo= JSON.parseObject((String) taskResult.getResult(), MerchantInfo.class);
                    if(merchantInfo!=null) {
                        TextHelper.setText(mercId, merchantInfo.getMercId());
                        TextHelper.setText(mercName, merchantInfo.getMercName());
                        TextHelper.setText(mercShotName, merchantInfo.getMercShotName());
                        TextHelper.setText(busiLicenseProvAndCity, merchantInfo.getBusiProvName()+merchantInfo.getBusiCityName());
                        TextHelper.setText(mercAddr, merchantInfo.getMercAddr());
                        TextHelper.setText(busiScope, merchantInfo.getBusiScope());
                        TextHelper.setText(contactName, merchantInfo.getContactName());
                        TextHelper.setText(contactMobile, merchantInfo.getContactMobile());
                    }
                }
            }
        });
    }



}
