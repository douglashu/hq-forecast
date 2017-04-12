package com.hq.app.olaf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.fragment.NewOperatorFragment;
import com.hq.app.olaf.ui.fragment.SetOperatorRightFragment;
import com.hq.app.olaf.ui.fragment.ShowOperatorFragment;
import com.hq.component.annotation.Layout;
import com.hq.component.base.AppActManager;

import butterknife.Bind;

@Layout(layoutId = R.layout.activity_operator_right)
public class OperatorRightActivity extends HqPayActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private FragmentManager fragmentManager;
    private HqPayFragment newOperatorFragment = new NewOperatorFragment();
    private HqPayFragment showOperatorFragment = new ShowOperatorFragment();
    private HqPayFragment setOperatorRightFragment = new SetOperatorRightFragment();
    private HqPayFragment currentFragment;

    public final static String OPERATOR_FUNC = "operator_func";
    private boolean isNewOperator;
    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        isNewOperator = intent.getBooleanExtra(OPERATOR_FUNC, true);
        newOperator();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentByTag(newOperatorFragment.getTAG());
        if (fragment != null) {
            if (currentFragment instanceof NewOperatorFragment) {
                Operator operator = getApp().getCache(Operator.class);
                getApp().getCacheClear(Operator.class);
                setResult(RESULT_OK);
                AppActManager.getInstance().finish(this);
//                setActTitle("查看操作员");
//                showEditMenu();
                fragmentManager.popBackStack();
            } else if (currentFragment instanceof SetOperatorRightFragment) {
                newOperator();
            } else if (currentFragment instanceof ShowOperatorFragment) {
                getFragmentManager().popBackStack();
            }
        } else {
            setResult(RESULT_OK);
            AppActManager.getInstance().finish(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        if (currentFragment instanceof ShowOperatorFragment)
            getMenuInflater().inflate(R.menu.menu_opr_edit, menu);
        return true;
    }

    private void hiddenEditMenu() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(false);
            }
        }
    }

    private void showEditMenu() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.manager:
                currentFragment = newOperatorFragment;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out);
                transaction.replace(R.id.container, currentFragment, newOperatorFragment.getTAG());
                transaction.addToBackStack(null);
                transaction.commit();
                setActTitle("编辑");
                hiddenEditMenu();
                break;
        }
        return true;
    }

    /**
     * 新增操作员
     */
    public void newOperator() {
        Operator operator = getApp().getCache(Operator.class);
        if (operator != null && operator.getId()!=null) {
            setActTitle("详情");
        } else {
            setActTitle("新增人员");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out);
        if (isNewOperator) {
            currentFragment = newOperatorFragment;
            transaction.replace(R.id.container, currentFragment, newOperatorFragment.getTAG());
        } else {
            currentFragment = showOperatorFragment;
            transaction.replace(R.id.container, currentFragment, showOperatorFragment.getTAG());
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 设置操作员权限
     */
    public void setOperatorRight() {
        Operator operator = getApp().getCache(Operator.class);
        if (operator != null && operator.getId()!=null) {
            setActTitle("修改权限");
        } else {
            setActTitle("权限设置");
        }
        currentFragment = setOperatorRightFragment;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out);
        transaction.replace(R.id.container, currentFragment, setOperatorRightFragment.getTAG());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 修改操作员权限
     */
    public void modifyOperatorRight() {
        Operator operator = getApp().getCache(Operator.class);
        if (operator != null && operator.getId()!=null) {
            setActTitle("修改权限");
        } else {
            setActTitle("权限设置");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, setOperatorRightFragment, setOperatorRightFragment.getTAG());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public boolean isNewOperator() {
        return isNewOperator;
    }
}
