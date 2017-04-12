package com.hq.app.olaf.ui.activity.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hq.component.annotation.Layout;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.fragment.OrderFragment;

import butterknife.Bind;

import static com.hq.app.olaf.ui.bean.login.Session.getTAG;

/**
 * Created by Administrator on 2017/2/6.
 */
@Layout(layoutId = R.layout.activity_order)
public class OrderActivity extends HqPayActivity {

    /*@Bind(R.id.toolbar)
    Toolbar toolbar;*/
    public static String INDEX_TAG = "";

    private FragmentManager fragmentManager;
    private OrderFragment orderFragment = new OrderFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INDEX_TAG = getTAG();
        //initToolbar(toolbar);
        setActTitle("订单");
        loadOrderPage();
    }

    /**
     * 订单页
     */
    public void loadOrderPage() {
        //MobclickAgent.onEvent(this, "OrderButton");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, orderFragment, orderFragment.getTAG());
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}
