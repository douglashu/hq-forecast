package com.hq.app.olaf.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hq.component.annotation.Layout;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayActivity;

import butterknife.Bind;

@Layout(layoutId = R.layout.activity_marketing)
public class MarketingActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        title.setText("营收分析");
    }
}

