package com.hq.app.olaf.ui.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.activity.OperatorRightActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.widget.CheckTextGroup;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.app.olaf.ui.widget.SelItem;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.fragment_show_operator)
public class ShowOperatorFragment extends HqPayFragment {
    @Bind(R.id.oprator) EditText oprator;
    @Bind(R.id.opratorName) EditText opratorName;
    @Bind(R.id.phoneNum) EditText phoneNum;
    @Bind(R.id.refund) EditText refund;
    @Bind(R.id.checkedGroup)
    CheckTextGroup checkedGroup;
    private Operator mOperator;
    private SelItem[] typeItems = new SelItem[]{new SelItem("00", "不允许退款"),
            new SelItem("01", "只可退自己的"),new SelItem("02", "退全部")};
    private Map<Integer, String> emptyMessage = new HashMap<>();


    public ShowOperatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void init() {
        mOperator = getApp().getCache(Operator.class);
        if (mOperator != null) {
            oprator.setText(mOperator.getRole().get(0).getRoleName());
            opratorName.setText(mOperator.getOperName());
            phoneNum.setText(mOperator.getMobilePhone());
            OperatorRightActivity rightActivity = (OperatorRightActivity) getAppActivity();
            if (!rightActivity.isNewOperator()) {
                phoneNum.setEnabled(false);
            } else {
                phoneNum.setEnabled(true);
            }
            for(SelItem item:typeItems)
            {
                if(item.getKey().equals(mOperator.getRefundAuth())){
                    refund.setText(item.getValue());
                    break;
                }
            }

        }
        emptyMessage.put(R.id.opratorName, "请填写操作员名称");
        emptyMessage.put(R.id.phoneNum, "请填写操作员电话");
    }

    @OnClick(R.id.next)
    public void next() {
        deleteOperatorRight(mOperator);
    }

    private void deleteOperatorRight(final Operator operator) {
        new InfoDialog(getActivity(), true, "提示", "是否删除该操作员？")
                .setContentColor(R.color.black)
                .setCancel("取消", new InfoDialog.OnClickListener() {
                    @Override
                    public void onClick(InfoDialog infoDialog, View button) {
                        infoDialog.dismiss();
                    }
                }).setConfirm("确定", new InfoDialog.OnClickListener() {
            @Override
            public void onClick(InfoDialog infoDialog, View button) {
                infoDialog.dismiss();
                HttpTask httpTask = HttpService.getInstance().deleteOperator(operator.getId());
                NetTools.excute(httpTask, new LoadingDialog(getContext()), new HqNetCallBack(getView()) {
                    @Override
                    public void doComplete(TaskResult taskResult) {
                        if (taskResult.isSuccess()) {
                            getApp().getCacheClear(Operator.class);
                            HqPayActivity rightActivity = getAppActivity();
                            rightActivity.setResult(Activity.RESULT_OK);
                            rightActivity.finish();
                            showSnackBar("删除成功");
                            getAppActivity().finish();
                        }
                    }
                });
            }
        }).setDialogCancelable(true).show();

    }
}
