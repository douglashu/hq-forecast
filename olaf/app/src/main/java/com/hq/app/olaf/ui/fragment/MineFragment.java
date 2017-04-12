package com.hq.app.olaf.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hq.app.olaf.ui.activity.msgcenter.MsgCenterActivity;
import com.hq.app.olaf.ui.activity.passwd.PassManagerActivity;
import com.hq.app.olaf.ui.base.HqPayFragment;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.ui.widget.MenuItemView;
import com.hq.app.olaf.util.StringUtil;
import com.hq.component.annotation.Layout;
import com.hq.component.network.service.impl.HttpTask;
import com.github.siyamed.shapeimageview.CircularImageView;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liaob on 2016/4/13.
 */
@Layout(layoutId = R.layout.fragment_mine)
public class MineFragment extends HqPayFragment {
    @Bind(R.id.merchantPic)
    ImageView merchantPic;
    @Bind(R.id.operatorName) TextView operatorName;
    @Bind(R.id.roleName) TextView roleName;
    @Bind(R.id.mobile) TextView mobile;
    @Bind(R.id.merchantInfo) MenuItemView merchantInfo;
    @Bind(R.id.prod_signs) MenuItemView prod_signs;
    @Bind(R.id.device_manage) MenuItemView device_manage;
    @Bind(R.id.aboutItem) MenuItemView aboutItem;
    private HttpTask getAssets;


    @Override
    public void init() {
        Session session = Session.load();
        operatorName.setText(session.getUserName());
        if(session.getRole()!=null && !StringUtil.isEmpty(session.getRole().getRoleName())){
            roleName.setText(session.getRole().getRoleName());
        }else{
            roleName.setText("");
        }
        mobile.setText(session.getMobile());
       /* Operators operators = session.getDefaultSettle();
        if (operators != null) {
            TextHelper.setText(merchantName, operators.getOperatorName());
            TextHelper.setText(merchantDesc, session.getMobile());
            if (!"3".equals(operators.getState())) {
                TextHelper.setText(merchantName, session.getMobile());
                TextHelper.setText(merchantDesc, "未商家认证");
            } else {
                if ("1474424723719".equals(operators.getUserRoleId())) {
                    TextHelper.setText(merchantName, operators.getOperatorName());
                    TextHelper.setText(merchantDesc, MessageFormat.format("经营者  {0}", session.getMobile()));
                } else if ("1474424723720".equals(operators.getUserRoleId())) {
                    TextHelper.setText(merchantName, operators.getOperatorName());
                    TextHelper.setText(merchantDesc, MessageFormat.format("店长  {0}", session.getMobile()));
                } else if ("1474424723721".equals(operators.getUserRoleId())) {
                    TextHelper.setText(merchantName, operators.getOperatorName());
                    TextHelper.setText(merchantDesc, MessageFormat.format("店小二  {0}", session.getMobile()));
                } else if (TextUtils.isEmpty(operators.getUserRoleId())) {
                    TextHelper.setText(merchantName, session.getMobile());
                    TextHelper.setText(merchantDesc, "未商家认证");
                }
            }
        } else {
            TextHelper.setText(merchantName, session.getMobile());
            TextHelper.setText(merchantDesc, "未商家认证");
        }*/
    }

//    @OnClick(R.id.passwd)
//    public void passwd() {
//        forward(PassManagerActivity.class);
//    }


//    @OnClick(R.id.msgCenter)
//    public void msgCenter() {
//        forward(MsgCenterActivity.class);
//    }

//    @OnClick(R.id.customPhone)
//    public void customPhone() {
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4008815516"));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
