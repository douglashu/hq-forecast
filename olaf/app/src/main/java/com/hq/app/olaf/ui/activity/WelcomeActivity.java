package com.hq.app.olaf.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.hq.app.olaf.ui.adapter.WelcomeAdapter;
import com.hq.app.olaf.ui.base.HqPayActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.OnClick;

public class WelcomeActivity extends HqPayActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.btn_welcome) Button btnWelcome;
    private int imageId[] = new int[]{R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3};
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    EdgeEffectCompat leftEdge;
    EdgeEffectCompat rightEdge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        //设置下次不进入欢迎界面
        SharedPreferences preferences = getSharedPreferences(
                "first_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstIn", false);
        editor.commit();
        List<View> imageViews = new ArrayList<>();
        for (int i = 0; i < imageId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageId[i]);

            imageViews.add(imageView);
        }
        WelcomeAdapter adapter = new WelcomeAdapter(imageViews);
        viewPager.setAdapter(adapter);
        //实现最后一页跳转；
        viewPager.setOnPageChangeListener(this);
        Field leftEdgeField = null;
        try {
            leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(viewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (rightEdge != null && !rightEdge.isFinished()) {//到了最后一张并且还继续拖动，出现蓝色限制边条了
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == imageId.length - 3) {
            btnWelcome.setBackgroundResource(R.drawable.shape_welcome_button);
            btnWelcome.setText("跳过");
            btnWelcome.setTextColor(getResources().getColor(R.color.btnWelcome1));
        } else if (position == imageId.length - 2) {
            btnWelcome.setBackgroundResource(R.drawable.shape_welcome_button1);
            btnWelcome.setText("跳过");
            btnWelcome.setTextColor(getResources().getColor(R.color.btnWelcome2));
        } else if (position == imageId.length - 1) {
            btnWelcome.setBackgroundResource(R.drawable.shape_welcome_button2);
            btnWelcome.setText("马上体验");
            btnWelcome.setTextColor(getResources().getColor(R.color.btnWelcome3));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.btn_welcome)
    public void onClick() {
//        int s = viewPager.getCurrentItem() + 1;
//        if (s == imageId.length) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            viewPager.setCurrentItem(s);
//        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

