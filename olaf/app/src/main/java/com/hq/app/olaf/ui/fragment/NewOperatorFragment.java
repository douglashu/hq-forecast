package com.hq.app.olaf.ui.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HqNetCallBack;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.net.LoadingView;
import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.ui.activity.OperatorRightActivity;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.oper.Function;
import com.hq.app.olaf.ui.bean.oper.Operator;
import com.hq.app.olaf.ui.bean.role.Role;
import com.hq.app.olaf.ui.widget.CheckTextGroup;
import com.hq.app.olaf.ui.widget.InfoDialog;
import com.hq.app.olaf.ui.widget.SelAdapter;
import com.hq.app.olaf.ui.widget.SelDialog;
import com.hq.app.olaf.ui.widget.SelItem;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.component.annotation.Layout;
import com.hq.component.base.AppActManager;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;
import com.hq.component.utils.JSON;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@Layout(layoutId = R.layout.fragment_new_operator)
public class NewOperatorFragment extends HqPayFragment implements View.OnClickListener {
    @Bind(R.id.opratorName) EditText opratorName;
    @Bind(R.id.phoneNum) EditText phoneNum;
    @Bind(R.id.checkedGroup)
    CheckTextGroup checkedGroup;
    @Bind(R.id.txt_check_first) TextView txtCheckFirst;
    @Bind(R.id.txt_info_first) TextView txtInfoFirst;
    @Bind(R.id.txt_check_other) TextView txtCheckOther;
    @Bind(R.id.txt_info_other) TextView txtInfoOther;
    @Bind(R.id.refundPermissions) TextView refundPermissions;
    @Bind(R.id.next) Button next;
    @Bind(R.id.layout_one) RelativeLayout layoutOne;
    @Bind(R.id.layout_two) RelativeLayout layoutTwo;
    @Bind(R.id.loadingView) LoadingView loadingView;
    @Bind(R.id.layout_opr) LinearLayout layoutOpr;
    private boolean isEditable = true;



    private Operator mOperator;
    private Map<Integer, String> emptyMessage = new HashMap<>();
    boolean isCheckHost = true;
    private HttpTask newOperator;
    private HttpTask modifyOperator;
    List<Role> roles;
    private SelItem[] typeItems = new SelItem[]{new SelItem("00", "不允许退款"),
            new SelItem("01", "只可退自己的"),new SelItem("02", "退全部")};//退款选择
    private SelAdapter refundPermissionsAdapter;
    private SelItem refundPermItem = typeItems[2];
    public NewOperatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void init() {
        emptyMessage.put(R.id.opratorName, "请填写操作员名称");
        emptyMessage.put(R.id.phoneNum, "请填写正确的操作员手机号");
        emptyMessage.put(R.id.refundPermissions, "请选择退款权限");
        layoutOne.setOnClickListener(this);
        layoutTwo.setOnClickListener(this);
        refundPermissionsAdapter = new SelAdapter(Arrays.asList(typeItems), "选择退款权限");
        //默认退全部
        refundPermItem = typeItems[2];
        mOperator = getApp().getCacheClear(Operator.class);
        if (mOperator != null) {
            opratorName.setText(mOperator.getOperName());
            phoneNum.setText(mOperator.getMobilePhone());
            OperatorRightActivity rightActivity = (OperatorRightActivity) getAppActivity();
            if (!rightActivity.isNewOperator()) {
                phoneNum.setEnabled(false);
            } else {
                phoneNum.setEnabled(true);
            }
            for(SelItem item:typeItems){
                if(item.getKey().equals(mOperator.getRefundAuth())){
                    refundPermItem = item;
                    refundPermissions.setText(item.getValue());
                    break;
                }
            }
        }

        initUserRight();
    }

    /**
     * 选择退款权限
     */
    @OnClick(R.id.accountTypeLayout)
    public void accountTypeLayout() {
        if (!isEditable) return;
        SelDialog selDialog = new SelDialog(getContext(), refundPermissionsAdapter);
        selDialog.setOnItemClickListener(new SelDialog.OnItemClickListener() {
            @Override
            public void onItemClick(SelDialog selDialog, int position, SelItem selItem) {
                selDialog.dismiss();
                refundPermItem = selItem;
                refundPermissions.setText(selItem.getValue());
            }
        });
        selDialog.show();
    }

    @OnClick(R.id.next)
    public void next() {
        if (checkedGroup.checkEmpty(emptyMessage, null)) {
            if (mOperator == null) {
                mOperator = new Operator();
            }
            mOperator.setOperName(opratorName.getText().toString());
            mOperator.setMobilePhone(phoneNum.getText().toString());
            mOperator.setRefundAuth(refundPermItem.getKey());
            commit();
        }
    }

    private void initUserRight() {
        HttpTask getUserRight = HttpService.getInstance().getOperatorRights();
        NetTools.excute(getUserRight, loadingView, new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    String result = (String) taskResult.getResult();
                    roles = JSON.parseArray(result, new TypeToken<List<Role>>() {
                    });
                    for (int i = 0; i < roles.size(); i++) {
                        if (i == 0) {
                            txtCheckFirst.setText(roles.get(i).getRoleName());
                            txtInfoFirst.setText(roles.get(i).getIntroduce());
                            if (mOperator != null && mOperator.getRole().get(0).getRoleName().equals(roles.get(i).getRoleName())) {
                                changeOperatorFirst();
                                roles.get(i).setChecked(true);
                            }
                        } else {
                            txtCheckOther.setText(roles.get(i).getRoleName());
                            txtInfoOther.setText(roles.get(i).getIntroduce());
                            if (mOperator != null && mOperator.getRole().get(0).getRoleName().equals(roles.get(i).getRoleName())) {
                                changeOperatorSecond();
                                roles.get(i).setChecked(true);
                            }
                        }
                    }
                    if (roles.size() >= 1) {
                        layoutOne.setVisibility(View.VISIBLE);
                    }
                    if(roles.size() > 1){
                        layoutTwo.setVisibility(View.VISIBLE);
                    }
                    layoutOpr.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setOperatorId() {
        if(mOperator.getRole()==null || mOperator.getRole().size()<=0){
            mOperator.getRole().add(new Role());
        }
        if (isCheckHost) {
            mOperator.getRole().get(0).setRoleType(roles.get(0).getRoleType());
            mOperator.getRole().get(0).setRoleId(roles.get(0).getRoleId());
        } else {
            mOperator.getRole().get(0).setRoleType(roles.get(1).getRoleType());
            mOperator.getRole().get(0).setRoleId(roles.get(1).getRoleId());
        }
    }

    private void commit() {
        String permissions = handlerPermissions(roles);
        HttpTask httpTask = null;
        if (mOperator.getId()!=null) {
            setOperatorId();
            httpTask = modifyOperator = HttpService.getInstance()
                    .modifyOperaor(mOperator);
        } else {
            setOperatorId();
            httpTask = newOperator = HttpService.getInstance()
                    .newOperator(mOperator);
        }
        NetTools.excute(httpTask, new LoadingDialog(getContext()), new HqNetCallBack(getView()) {
            @Override
            public void doComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    if (newOperator != null && newOperator.getTAG().equals(taskResult.getTag())) {
//                        getAppActivity().showTipsDialog("添加成功,确定后返回操作员列表", null, null, "确定",
//                                new MaterialDialog.SingleButtonCallback() {
//                                    @Override
//                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                        dialog.dismiss();
//                                        getApp().getCacheClear(Operator.class);
//                                        AnyPayActivity rightActivity = getAppActivity();
//                                        rightActivity.setResult(Activity.RESULT_OK);
//                                        rightActivity.finish();
//                                    }
//                                }, true, null);
                        new InfoDialog(getAppActivity(), true, "提示", "人员 " + opratorName.getText().toString() + " 添加成功")
                                .setContentColor(R.color.black)
                                .setCancel("返回列表页", new InfoDialog.OnClickListener() {
                                    @Override
                                    public void onClick(InfoDialog infoDialog, View button) {
                                        infoDialog.dismiss();
                                        getApp().getCacheClear(Operator.class);
                                        HqPayActivity rightActivity = getAppActivity();
                                        rightActivity.setResult(Activity.RESULT_OK);
                                        rightActivity.finish();
                                    }
                                })
                                .setConfirm("返回首页", new InfoDialog.OnClickListener() {
                                    @Override
                                    public void onClick(InfoDialog infoDialog, View button) {
                                        infoDialog.dismiss();
                                        AppActManager.getInstance().finishExcept(NewIndexActivity.INDEX_TAG);
                                    }
                                }).setDialogCancelable(true).show();
                    } else {
//                        getAppActivity().showTipsDialog("修改成功,确定后返回操作员列表", null, null, "确定",
//                                new MaterialDialog.SingleButtonCallback() {
//                                    @Override
//                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                        dialog.dismiss();
//                                        getApp().getCacheClear(Operator.class);
//                                        AnyPayActivity rightActivity = getAppActivity();
//                                        rightActivity.setResult(Activity.RESULT_OK);
//                                        rightActivity.finish();
//                                    }
//                                }, true, null);
                        new InfoDialog(getAppActivity(), true, "提示", "人员 " + opratorName.getText().toString() + " 修改成功")
                                .setContentColor(R.color.black)
                                .setCancel("返回列表页", new InfoDialog.OnClickListener() {
                                    @Override
                                    public void onClick(InfoDialog infoDialog, View button) {
                                        infoDialog.dismiss();
                                        getApp().getCacheClear(Operator.class);
                                        HqPayActivity rightActivity = getAppActivity();
                                        rightActivity.setResult(Activity.RESULT_OK);
                                        rightActivity.finish();
                                    }
                                })
                                .setConfirm("返回首页", new InfoDialog.OnClickListener() {
                                    @Override
                                    public void onClick(InfoDialog infoDialog, View button) {
                                        infoDialog.dismiss();
                                        AppActManager.getInstance().finishExcept(NewIndexActivity.INDEX_TAG);
                                    }
                                }).setDialogCancelable(true).show();
                    }
                }
            }
        });
    }

    /**
     * 得到角色权限值
     *
     * @param roles
     * @return
     */
    private String handlerPermissions(List<Role> roles) {
        StringBuilder permissions = new StringBuilder("");
        for (Role role : roles) {
            if (role.isChecked()) {
                permissions.append(role.getRoleId()).append(",");
            }
        }
        if (permissions.lastIndexOf(",") > 0) {
            permissions.setCharAt(permissions.lastIndexOf(","), ' ');
        }
        return permissions.toString().trim();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void changeOperatorFirst() {
        if (isCheckHost) return;
        layoutOne.setBackgroundResource(R.drawable.bg_radius_coner_blue);
//        iconCheckFirst.setBackgroundResource(R.drawable.icon_check_blue);
        txtCheckFirst.setTextColor(Color.parseColor("#00A0EA"));
        txtInfoFirst.setTextColor(Color.parseColor("#00A0EA"));
        layoutTwo.setBackgroundResource(R.drawable.bg_edt_radius_corner_gray);
//        iconCheckOther.setBackgroundResource(R.drawable.icon_check_gray1);
        txtCheckOther.setTextColor(Color.parseColor("#222222"));
        txtInfoOther.setTextColor(Color.parseColor("#555555"));
        isCheckHost = true;
        if (roles != null) {
            for (Role role : roles) {
                if ("2".equals(role.getRoleType())) {
                    role.setChecked(true);

                } else {
                    role.setChecked(false);
                }
            }
        }
    }

    private void changeOperatorSecond() {
        if (!isCheckHost) return;
        layoutTwo.setBackgroundResource(R.drawable.bg_radius_coner_blue);
//        iconCheckOther.setBackgroundResource(R.drawable.icon_check_blue);
        txtCheckOther.setTextColor(Color.parseColor("#00A0EA"));
        txtInfoOther.setTextColor(Color.parseColor("#00A0EA"));
        layoutOne.setBackgroundResource(R.drawable.bg_edt_radius_corner_gray);
//        iconCheckFirst.setBackgroundResource(R.drawable.icon_check_gray1);
        txtCheckFirst.setTextColor(Color.parseColor("#222222"));
        txtInfoFirst.setTextColor(Color.parseColor("#555555"));
        isCheckHost = false;
        if (roles != null) {
            for (Role role : roles) {
                if ("3".equals(role.getRoleType())) {
                    role.setChecked(true);

                } else {
                    role.setChecked(false);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_one:
                changeOperatorFirst();
                break;
            case R.id.layout_two:
                if (roles != null && roles.size() <= 1)
                    return;
                changeOperatorSecond();
                break;
        }
    }
}
