package com.hq.app.olaf.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hq.app.olaf.ui.fragment.OperListFragment;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.Progress;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.JSON;
import com.google.gson.reflect.TypeToken;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.adapter.OperatorListAdapter;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;

@Layout(layoutId = R.layout.activity_staff)
public class OperatorActivity extends HqPayActivity {

    private FragmentManager fragmentManager;
    private OperListFragment operListFragment = new OperListFragment();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
//
    @Bind(R.id.title)
    TextView title;
//
//    @Bind(R.id.listView)
//    ListView listView;
//
//    @Bind(R.id.recyclerView)
//    EasyRecyclerView recyclerView;

//    private List<OperatorListAdapter.Item> itemList = new ArrayList<>();
//    private OperatorListAdapter opratorAdapter;
//    private boolean isDelete = false;
//    private List<Operator> operators;
//    private boolean isStoreManager = false;//是否为查看子商户操作员,如是则不能对操作列表做修改


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        title.setText("员工管理");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, operListFragment, operListFragment.getTAG());
//        transaction.addToBackStack(null);
        transaction.commit();
//        isStoreManager = getIntent().getBooleanExtra("isStoreManager", false);
//        if (isStoreManager) {
//            setActTitle("查看管理员");
//        }

//        opratorAdapter = new OperatorListAdapter();
//        listView.setAdapter(opratorAdapter);
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if (isStoreManager) return true;
//                for (OperatorListAdapter.Item i : itemList) {
//                    i.setDelete(true);
//                }
//                opratorAdapter.notifyDataSetChanged();
//                return true;
//            }
//        });
//        opratorAdapter.setOnDelClickListener(new OperatorListAdapter.onDelClickListener() {
//            @Override
//            public void onDelete(final int position, final OperatorListAdapter.Item item) {
////                showTipsDialog("确定要删除当前操作员?", "删除", new MaterialDialog.SingleButtonCallback() {
////                    @Override
////                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
////                        deleteOperatorRight(position, item.getOperator());
////                    }
////                });
//                new InfoDialog(OperatorActivity.this, true, "提示", "是否删除该操作员？")
//                        .setCancel("取消", new InfoDialog.OnClickListener() {
//                            @Override
//                            public void onClick(InfoDialog infoDialog, View button) {
//                                infoDialog.dismiss();
//                            }
//                        }).setConfirm("确定", new InfoDialog.OnClickListener() {
//                    @Override
//                    public void onClick(InfoDialog infoDialog, View button) {
//                        infoDialog.dismiss();
//                        deleteOperatorRight(position, item.getOperator());
//                    }
//                }).setDialogCancelable(true).show();
//            }
//        });
//        getOperators();
    }

//    @Override
//    public void onBackPressed() {
//        if (opratorAdapter.isDeleteState()) {
//            opratorAdapter.resetDeleteState();
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.operator_menu, menu);
        return true;
    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.manager:
                Bundle bundle = new Bundle();
                bundle.putBoolean(OperatorRightActivity.OPERATOR_FUNC, true);
                getApp().getCacheClear(Operator.class);
                forward(OperatorRightActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        operListFragment.refreshList();
    }
    //
//    private void getOperators() {
//        Progress progress = new LoadingDialog(this);
//        HttpTask httpTask = HttpService.getInstance()
//                .getOperators();
//        NetTools.excute(httpTask, progress, new NetTools.CallBack() {
//            @Override
//            public void onComplete(TaskResult taskResult) {
//                if (taskResult.isSuccess()) {
//                    listView.setVisibility(View.VISIBLE);
//                    String result = (String) taskResult.getResult();
//                    operators = JSON.parseArray(result, new TypeToken<List<Operator>>() {
//                    });
//
//                    itemList.clear();
//                    for (Operator operator : operators) {
//                        OperatorListAdapter.Item item = new OperatorListAdapter.Item(operator, false);
//                        itemList.add(item);
//                    }
//                    opratorAdapter.refresh(itemList);
//                } else if (taskResult.isEmptyData()) {
////                        loadingView.setEmptyText("您还没有操作员");
//                    /*showTipsDialog("提示", "暂无操作员数据,是否立即创建?", "取消",
//                            (@NonNull MaterialDialog dialog, @NonNull DialogAction which) -> {
//                                dialog.dismiss();
//                            }, "立即创建", (@NonNull MaterialDialog dialog, @NonNull DialogAction which) -> {
//                                dialog.dismiss();
//                                Bundle bundle = new Bundle();
//                                bundle.putBoolean(OperatorRightActivity.OPERATOR_FUNC, true);
//                                forward(OperatorRightActivity.class);
//                            }, true, null);*/
//                } else {
//                        RequestFailedHandler.handleFailed(getRootView(), taskResult);
//                }
//            }
//        });
//    }
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 101 && resultCode == RESULT_OK) {
//            listView.setVisibility(View.GONE);
//            getOperators();
//        }
//    }
//
//    @OnItemClick(R.id.listView)
//    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (isStoreManager) return;
//
//        OperatorListAdapter.Item item = itemList.get(position);
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(OperatorRightActivity.OPERATOR_FUNC, false);
//        getApp().saveCache(item.getOperator());
//        forwardForResult(OperatorRightActivity.class, bundle, 101);
//    }
//
//
//    private void deleteOperatorRight(final int position, Operator operator) {
//        HttpTask httpTask = HttpService.getInstance().deleteOperator(operator.getId());
//        NetTools.excute(httpTask, new LoadingDialog(this), new NetTools.CallBack() {
//            @Override
//            public void onComplete(TaskResult taskResult) {
//                if (taskResult.isSuccess()) {
//                    showSnackBar("删除成功");
//                    opratorAdapter.remove(position);
//                    finish();
//                } else {
//                    RequestFailedHandler.handleFailed(getRootView(), taskResult);
//                }
//            }
//        });
//    }
}
