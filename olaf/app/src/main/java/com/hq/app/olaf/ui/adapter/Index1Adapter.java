package com.hq.app.olaf.ui.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.component.utils.TextHelper;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.ui.activity.MarketingActivity;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.activity.OperatorActivity;
import com.hq.app.olaf.ui.activity.StatementActivity;
import com.hq.app.olaf.ui.activity.order.OrderActivity;
import com.hq.app.olaf.ui.activity.shoukuan.ScannerActivity;
import com.hq.app.olaf.ui.activity.shoukuan.ShoukuanActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.app.Apps;
import com.hq.app.olaf.ui.bean.app.AppsData;
import com.hq.app.olaf.ui.bean.home.StatisticSum;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.widget.WrapGridLayoutManager;
import com.hq.app.olaf.util.Const;
import com.hq.app.olaf.util.IndexDialogUtil;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.util.SharedPrefUtil;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.TaskStatus;
import com.hq.component.network.service.impl.HttpTask;
import com.dinuscxj.itemdecoration.GridDividerItemDecoration;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by huwentao on 16/9/20.
 */

public class Index1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER_POSITION = 0;
    private static final int TYPE_FUNCTION_POSITION = 1;
    private static final int TYPE_PROFIT_POSITION = 2;
    private static final int TYPE_BANNER_POSITION = 3;

    private EasyRecyclerView recyclerView;
    private Session session = Session.load();
    private NewIndexActivity newIndex;
    private List<Apps> appsList = new ArrayList<>();
    private MenuItemAdapter menuItemAdapter;
    private SharedPrefUtil sharedPref;
    private DecimalFormat format = new DecimalFormat("###,###,##0.00");

    private HttpTask getAssets;
    private HttpTask getFuncList;
    HttpTask getTodayData;
    private HttpTask getMerchantInfo;
    private boolean isCreateFinanceAccount = false;
//

    public final static String ISSHOULDSHOW_FINANCE = "isShouldShowFinanceTipDialog";
    private AppsData appsData;
    private  StatisticSum statisticSum;
    private HttpTask deviceToken;

    public Index1Adapter(NewIndexActivity newIndex, final EasyRecyclerView recyclerView) {
        this.newIndex = newIndex;
        this.sharedPref = SharedPrefUtil.getInstance();
        this.recyclerView = recyclerView;
        refreshMerchantInfo = new HqNetCallBack(recyclerView) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
               /* if (taskResult.isTaskResult(getMerchantInfo)) {
                    Session merchant = JSON.parseObject((String) taskResult.getResult(), Session.class);
                    session = Session.load();
                    //session.setSettle(merchant.getRealMerchantInfo());
                    //session.setOperatorRelaList(merchant.getOperatorRelas());
                    session.save();
                } else if (taskResult.isTaskResult(getFuncList)) {
                    String json = taskResult.getResult().toString();
                    AppsData data = JSON.parseObject(json, AppsData.class);
                    getApp().saveCache("App_funclist", data);
                } else*/ if (taskResult.isTaskResult(getTodayData)) {
                        if(taskResult.getResult()!=null) {
                            String json = taskResult.getResult().toString();
                            StatisticSum data = JSON.parseObject(json, StatisticSum.class);
                            getApp().saveCache("App_StatisticSum", data);
                        }
                    }
                }
            }

            @Override
            public void onComplete(Map<String, TaskResult> taskResults) {
                notifyDataSetChanged();
                recyclerView.setRefreshing(false);
            }
        };
        //loadInfo();

    }

    public void notifyMenuItemData(){
        menuItemAdapter.notifyDataSetChanged();
    }

    public void loadInfo() {
        /***********暂时先写死数据***************/
        loadAppListData();

        Session session = Session.load();
       // getMerchantInfo = HttpService.getInstance().getMerchantByRunningNo("");
        appsData = (AppsData) getApp().getCache("App_funclist");
        statisticSum=(StatisticSum)getApp().getCache("App_StatisticSum");
        //注册推送
        String deviceTokenStr = SharedPrefUtil.getInstance().load("deviceToken");
        if (!TextUtils.isEmpty(deviceTokenStr)) {
            deviceToken = HttpService.getInstance().management_push_bind(deviceTokenStr);
        }

        getTodayData = HttpService.getInstance().getStatisticSummary();

        NetTools.excute(refreshMerchantInfo, getTodayData, deviceToken);



       /* if (appsData == null) {
            getFuncList = HttpService.getInstance().getApps(sharedPref.load(Const.SharedPreferences.APPHASHCODE));
        }
        */

    }

    /**
     * 刷新商户状态
     */
    private NetTools.CallBack refreshMerchantInfo;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (viewType == TYPE_HEADER_POSITION) {
            view = inflater.inflate(R.layout.layout_index_gridmenu, parent, false);
            return new HEADER(view);
        }else if (viewType == TYPE_PROFIT_POSITION) {
            view = inflater.inflate(R.layout.layout_index_balance, parent, false);
            return new PROFIT(view);
        }else if(viewType == TYPE_BANNER_POSITION){
            view = inflater.inflate(R.layout.layout_index_banner, parent, false);
            return new Banner(view);
        }
        else {
            view = inflater.inflate(R.layout.layout_index_functions, parent, false);
            return new FUNCTION(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        //Operators operators = session.getDefaultSettle();
        switch (type) {
            case TYPE_HEADER_POSITION:
                HEADER header = (HEADER) holder;
                break;
            case TYPE_PROFIT_POSITION:
                PROFIT profit = (PROFIT) holder;
                statisticSum=(StatisticSum)getApp().getCache("App_StatisticSum");
                if(statisticSum!=null ) {
                    TextHelper.setText(profit.totalAmount, String.format("%.2f", Double.valueOf(statisticSum.getTotalAmount()) / 100));
                    TextHelper.setText(profit.tradeCount, String.valueOf(statisticSum.getTradeCount()));
                }
                break;
            case TYPE_FUNCTION_POSITION:
                FUNCTION function = (FUNCTION) holder;
                if (menuItemAdapter.getItemCount() == 0) {
                    AppsData appsData = (AppsData) getApp().getCache("App_funclist");
                    if (appsData != null) {
                        menuItemAdapter.refresh(appsData.getApps());
                    } else {
                        function.getAppList();
                    }
                }
                break;
            case TYPE_BANNER_POSITION:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER_POSITION;
            case 2:
                return TYPE_PROFIT_POSITION;
            case 3:
                return TYPE_BANNER_POSITION;
            default:
                return TYPE_FUNCTION_POSITION;
        }
    }

    public class Banner extends RecyclerView.ViewHolder{
        public Banner(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    /**
     * 固定功能信息
     */
    public class HEADER extends RecyclerView.ViewHolder {
        @Bind(R.id.gridView)
        GridView gridView;

        private GridViewAdapter adapter;

        // 设置适配器的图片资源
        int[] imageId = new int[] {
                R.drawable.shoukuan_3x,
                R.drawable.order_3x,
                R.drawable.yanquan_3x,
                };

        // 设置标题
        String[] title = new String[] { "收款", "订单", "验券" };
        List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        HEADER(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            gridView = (GridView) itemView.findViewById(R.id.gridView);
            // 将上述资源转化为list集合
            for(int i=0;i<title.length;i++){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("image", imageId[i]);
                map.put("title", title[i]);
                listItem.add(map);
            }
            adapter = new GridViewAdapter(newIndex, listItem);
            gridView.setAdapter(adapter);

            //注册监听事件
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                if(position==0){//收款
                    newIndex.forward(ShoukuanActivity.class);
                }
                if(position==1){//订单
                    newIndex.forward(OrderActivity.class);
                }
                if(position==2){//扫码测试
                    IndexDialogUtil.showDialog(newIndex, session);
                }
                }
            });

        }




    }


    /**
     * 交易今日数据统计
     */
    public class PROFIT extends RecyclerView.ViewHolder {
        @Bind(R.id.totalAmount) TextView totalAmount;
        @Bind(R.id.tx_tradeCount) TextView tradeCount;
        @Bind(R.id.couponVerifyCountToday) TextView couponVerifyCountToday;
        @Bind(R.id.memberIncomesToday) TextView memberIncomesToday;

        PROFIT(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void LoadTodayData(){
            NetTools.excute(HttpService.getInstance().getStatisticSummary(),
                    new HqNetCallBack(recyclerView) {
                        @Override
                        public void doComplete(TaskResult taskResult) {
                            if (taskResult.getStatus() == TaskStatus.success) {
                                StatisticSum data = JSON.parseObject((String) taskResult.getResult(), StatisticSum.class);
                                getApp().saveCache("App_StatisticSum", data);
                            }
                        }

                    });
        }

        //交易信息栏
        @OnClick(R.id.tradeLayout)
        public void tradeLayout(View v) {
            newIndex.forward(OrderActivity.class);
            return;
        }




    }

    /**
     * 其他功能
     */
    public class FUNCTION extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;


        FUNCTION(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            menuItemAdapter = new MenuItemAdapter();
            WrapGridLayoutManager layoutManager = new WrapGridLayoutManager(newIndex, 4, recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            //simple usage
            Drawable divider = itemView.getResources().getDrawable(R.drawable.shape_divider);
            GridDividerItemDecoration dividerItemDecoration = new GridDividerItemDecoration(newIndex,
                    GridDividerItemDecoration.GRID_DIVIDER_VERTICAL);
            dividerItemDecoration.setVerticalDivider(divider);
            dividerItemDecoration.setHorizontalDivider(divider);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(menuItemAdapter);
            menuItemAdapter.refresh(appsList);
            menuItemAdapter.setOnItemClickListener(new FunctionClick(itemView));
        }

        void getAppList() {
           /* NetTools.excute(HttpService.getInstance()
                            .getApps(sharedPref.load(Const.SharedPreferences.APPHASHCODE)),
                    new NetTools.CallBack() {
                        @Override
                        public void onComplete(TaskResult taskResult) {
                            super.onComplete(taskResult);
                            if (taskResult.getStatus() == TaskStatus.success) {
                                AppsData data = JSON.parseObject((String) taskResult.getResult(), AppsData.class);
                                getApp().saveCache("App_funclist", data);
                                menuItemAdapter.refresh(data.getApps());
                            }
                        }
                    });*/

            /***********暂时先写死数据***************/
            loadAppListData();
        }
    }

    private void loadAppListData() {
        InputStream jsonStream = null;
        try {
            jsonStream = newIndex.getAssets().open("app_list.json");
            BufferedSource source = Okio.buffer(Okio.source(jsonStream));
            String json = source.readUtf8();
            AppsData data  = JSON.parseObject(json, AppsData.class);
            getApp().saveCache("App_funclist", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class FunctionClick implements MenuItemAdapter.ItemClickListener {
        private View itemView = null;

        FunctionClick(View itemView) {
            this.itemView = itemView;
        }

        public void forward(Class targetClass) {
            newIndex.forward(targetClass);
        }

        public void forward(Class targetClass, Bundle bundle) {
            if (bundle != null) {
                newIndex.forward(targetClass,bundle);
            } else {
                forward(targetClass);
            }
        }

        @Override
        public void onItemClick(Apps apps) {

            //Operators operators = session.getDefaultSettle();
           // if (operators != null && "3".equals(operators.getState())) {
            if(true){
                if ("multiple_bill".equals(apps.getId())) {
                    //对账单
                    forward(StatementActivity.class);
                }/*else if ("revenue_data".equals(apps.getId())) {
                    //营收分析
                    forward(MarketingActivity.class);
                }*/else if ("staff_manage".equals(apps.getId())) {
                    Session session = Session.load();
                    if(session.getRole().getRoleName().equals("店员")){
                        IndexDialogUtil.showDialog(newIndex, session);
                        return;
                    }
                    //员工管理
                    forward(OperatorActivity.class);
                }else {
                    IndexDialogUtil.showDialog(newIndex, session);
                }
            }
        }

    }

    /**
     * 页面跳转
     *
     * @param aClass
     * @param bundle
     */
    private void forward(Class<? extends HqPayActivity> aClass, Bundle bundle) {
        newIndex.forward(aClass, bundle);
    }


    private HqPayApplication getApp() {
        return HqPayApplication.getAppContext();
    }
}
