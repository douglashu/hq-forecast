package com.hq.app.olaf.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;

import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.ui.activity.msgcenter.MsgCenterActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.fragment.MessageFragment;
import com.hq.app.olaf.ui.fragment.MineFragment;

import com.hq.app.olaf.ui.fragment.NewIndexFragment;
import com.hq.app.olaf.ui.fragment.OrderFragment;
import com.hq.app.olaf.util.HomeVersionDownLoad;
import com.hq.app.olaf.util.SharedPrefUtil;
import com.hq.component.annotation.Layout;
import com.hq.component.date.DateTime;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.uicomponet.changeicontext.ChangeColorIconWithTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


import com.hq.app.olaf.R;
import butterknife.Bind;

@Layout(layoutId = R.layout.activity_new_index)
public class NewIndexActivity extends HqPayActivity implements View.OnClickListener {
    @Bind(R.id.homeTab)
    ChangeColorIconWithTextView homeTab;
    @Bind(R.id.messageTab)
    ChangeColorIconWithTextView messageTab;
    @Bind(R.id.mineTab)
    ChangeColorIconWithTextView mineTab;
    @Bind(R.id.container) FrameLayout container;

    private FragmentManager fragmentManager;
    private boolean isExit = false;

    private NewIndexFragment newIndexFragment = new NewIndexFragment();
    private MessageFragment messageFragment = new MessageFragment();
    private MineFragment mineFragment = new MineFragment();
    private HqPayFragment[] fragments = new HqPayFragment[]{newIndexFragment, messageFragment, mineFragment};
    private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();
    private int position = 0;
    public static String INDEX_TAG = "";
    private HttpTask getMerchantInfo;
    private boolean isShowFinianceDialog = true;
    private BroadcastReceiver newMessageReveiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INDEX_TAG = getTAG();
        //orderFragment.setShowBack(false);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, newIndexFragment, newIndexFragment.getTAG());
//        transaction.addToBackStack(null);
        transaction.commit();
        position = 0;

        initTabIndicator();
        /*getMerchantInfo = HttpService.getInstance().getMerchantByRunningNo("");
        NetTools.excute(new NetTools.CallBack() {
            @Override
            public void onComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    if (taskResult.isTaskResult(getMerchantInfo)) {
                        Session merchant = JSON.parseObject((String) taskResult.getResult(), Session.class);
                        Session session = Session.load();
                        session.save();
                    }
                }
            }

            @Override
            public void onComplete(Map<String, TaskResult> taskResults) {
               
            }
        }, getMerchantInfo);*/

     /*  registerReceiver(newMessageReveiver = new BroadcastReceiver() {


            @Override
            public void onReceive(Context context, Intent intent) {
                //通知首页刷新通知消息状态
                newIndexFragment.notifyMenuItemData();
            }
        }, new IntentFilter("com.hq.app.olaf.HAVE_NEW_MESSAGE"));*/
        String todayStr= DateTime.now(TimeZone.getDefault()).format("YYYYMMDDhh");
        String checkDownDate=SharedPrefUtil.getInstance().load("checkDownVersionToday");
        if (TextUtils.isEmpty(checkDownDate) ||
                checkDownDate.equals(1)  //强制更新
                || Integer.valueOf(todayStr)>Integer.valueOf(checkDownDate) ) {
//            检查版本
            new HomeVersionDownLoad(NewIndexActivity.this).initStart(true);
        }

    }

    /**
     * 主页
     */
    public void loadHomePage() {
        //MobclickAgent.onEvent(this, "IndexButton");
        position = 0;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out);
        transaction.replace(R.id.container, fragments[position], fragments[position].getTAG());
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * 消息页
     */
    public void loadMessagePage() {
        //MobclickAgent.onEvent(this, "OrderButton");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (position == 0) {
           // transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out);
        }
        if (position == 2) {
           // transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out);
        }
        position = 1;
        transaction.replace(R.id.container, fragments[position], fragments[position].getTAG());
       transaction.addToBackStack(null);
        transaction.commit();

    }

    /**
     * 我的页面
     */
    public void loadMinePage() {
        //MobclickAgent.onEvent(this, "ConfigurationButton");
        position = 2;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out);
        transaction.replace(R.id.container, fragments[position], fragments[position].getTAG());
//        transaction.addToBackStack(null);
        transaction.commit();
        setActTitle("我的");
    }

    private void initTabIndicator() {
        mTabIndicator.add(homeTab);
        mTabIndicator.add(messageTab);
        mTabIndicator.add(mineTab);

        homeTab.setOnClickListener(this);
        messageTab.setOnClickListener(this);
        mineTab.setOnClickListener(this);
        homeTab.setIconAlpha(1.0f);
    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.homeTab:
                //MobclickAgent.onEvent(this, "IndexButton");
                mTabIndicator.get(0).setIconAlpha(1.0f);
                loadHomePage();
                break;
            case R.id.messageTab:
                //MobclickAgent.onEvent(this, "OrderButton");
                mTabIndicator.get(1).setIconAlpha(1.0f);
                loadMessagePage();
                break;
            case R.id.mineTab:
                //MobclickAgent.onEvent(this, "ConfigurationButton");
                mTabIndicator.get(2).setIconAlpha(1.0f);
                loadMinePage();
                break;

        }
    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator.size(); i++) {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.setting:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                loadMinePage();
                break;
        }
        return true;
    }

    /**
     * 重载按键侦听
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                exitBy2Click(); // 调用双击退出函数
                returnToHome();
//                getApp().exitApp();//直接退出应用
//                moveTaskToBack(false);
                return true;
            }

        }
        return super.dispatchKeyEvent(event);
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            showSnackBar(container, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            getApp().exitApp();
        }
    }

    public boolean isShowFinianceDialog() {
        return isShowFinianceDialog;
    }

    public void setShowFinianceDialog(boolean showFinianceDialog) {
        isShowFinianceDialog = showFinianceDialog;
    }
}
