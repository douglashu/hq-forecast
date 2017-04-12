package com.hq.app.olaf.ui.activity.shoukuan;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hq.component.annotation.Layout;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayActivity;

import butterknife.Bind;

@Layout(layoutId = R.layout.activity_sao_ma)
public class SaoMaActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;


    @Bind(R.id.tv_money)
    TextView tv_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        Bundle bundle =  getIntent().getExtras();
        String money=bundle.getString("money");
        title.setText("收款");
        tv_money.setText(money);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.pay_help_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.pay_help_menu) {
            Toast.makeText(this,"跳转到付款帮助页面", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
