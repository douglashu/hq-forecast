package com.hq.app.olaf.ui.fragment;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.adapter.OperatorRightAdapter;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.oper.Function;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.JSON;
import com.hq.uicomponet.easyrecyclerview.EasyRecyclerView;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@Layout(layoutId = R.layout.fragment_set_operator_right)
public class SetOperatorRightFragment extends HqPayFragment {
    @Bind(R.id.loadingView) LoadingView loadingView;
    @Bind(R.id.easyRecyclerView)
    EasyRecyclerView easyRecyclerView;
    @Bind(R.id.commit) Button commit;

    private OperatorRightAdapter operatorRightAdapter;
    private Operator mOperator;
    private HttpTask newOperator;
    private HttpTask modifyOperator;


    public SetOperatorRightFragment() {
        // Required empty public constructor
    }

    @Override
    public void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        operatorRightAdapter = new OperatorRightAdapter(getContext());
        easyRecyclerView.setAdapter(operatorRightAdapter);
        easyRecyclerView.setLayoutManager(layoutManager);
        mOperator = getApp().getCache(Operator.class);
        initUserRight();
    }

    private void initUserRight() {
        HttpTask getUserRight = HttpService.getInstance().getOperatorRights();
        NetTools.excute(getUserRight, loadingView, new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    easyRecyclerView.setVisibility(View.VISIBLE);
                    String result = (String) taskResult.getResult();
                    List<Function> functions = JSON.parseArray(result, new TypeToken<List<Function>>() {
                    });
                   /* String funcs = mOperator.getFuncList();
                    if (!TextUtils.isEmpty(funcs)) {
                        String[] funcArray = funcs.split(",");
                        for (Function function : functions) {
                            for (String func : funcArray) {
                                if (function.getId().equals(func)) {
                                    function.setChecked(true);
                                }
                            }
                        }
                    }*/
                    operatorRightAdapter.refresh(functions);
                    if (operatorRightAdapter.getCount() > 0) {
                        commit.setEnabled(true);
                    }
                } else {
                    if (operatorRightAdapter.getCount() <= 0) {
                        commit.setEnabled(false);
                    }
                }
            }
        });
    }


    @OnClick(R.id.commit)
    public void commit() {
        String permissions = handlerPermissions(operatorRightAdapter.getDatas());
        if (TextUtils.isEmpty(permissions)) {
            showSnackBar("请选择操作员权限");
            return;
        }
        HttpTask httpTask = null;
        if (mOperator.getId()!=null) {
            httpTask = modifyOperator = HttpService.getInstance()
                    .modifyOperaor(mOperator);
        } else {
            httpTask = newOperator = HttpService.getInstance()
                    .newOperator(mOperator);
        }
        NetTools.excute(httpTask, new LoadingDialog(getContext()), new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    if (newOperator != null && newOperator.getTAG().equals(taskResult.getTag())) {
                        getAppActivity().showTipsDialog("添加成功,确定后返回操作员列表", null, null, "确定",
                                new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        getApp().getCacheClear(Operator.class);
                                        HqPayActivity rightActivity = getAppActivity();
                                        rightActivity.setResult(Activity.RESULT_OK);
                                        rightActivity.finish();
                                    }
                                }, true, null);
                    } else {
                        getAppActivity().showTipsDialog("修改成功,确定后返回操作员列表", null, null, "确定",
                                new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        getApp().getCacheClear(Operator.class);
                                        HqPayActivity rightActivity = getAppActivity();
                                        rightActivity.setResult(Activity.RESULT_OK);
                                        rightActivity.finish();
                                    }
                                }, true, null);
                    }
                }
            }
        });
    }

    /**
     * 得到权限值
     *
     * @param functions
     * @return
     */
    private String handlerPermissions(List<Function> functions) {
        StringBuilder permissions = new StringBuilder("");
        for (Function function : functions) {
            if (function.isChecked()) {
                permissions.append(function.getId()).append(",");
            }
        }
        if (permissions.lastIndexOf(",") > 0) {
            permissions.setCharAt(permissions.lastIndexOf(","), ' ');
        }
        return permissions.toString().trim();
    }
}

