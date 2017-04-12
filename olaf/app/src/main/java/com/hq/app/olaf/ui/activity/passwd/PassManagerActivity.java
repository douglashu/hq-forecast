package com.hq.app.olaf.ui.activity.passwd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.impl.HttpTask;


import com.hq.app.olaf.R;
import butterknife.Bind;

/**
 * 密码管理
 */
@Layout(layoutId = R.layout.activity_pass_manager)
public class PassManagerActivity extends HqPayActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private HttpTask FIN001_00032;
    private HttpTask getAssets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);

       // Operators operators = Session.load().getDefaultSettle();

    }

}
