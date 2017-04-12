package com.hq.app.olaf.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.hq.app.olaf.ui.adapter.GuideViewPagerAdapter;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.component.annotation.Layout;

import java.util.ArrayList;
import java.util.List;


import com.hq.app.olaf.R;
import butterknife.Bind;

@Layout(layoutId = R.layout.activity_guide)
public class GuideActivity extends HqPayActivity implements OnClickListener, OnPageChangeListener {
    @Bind(R.id.view_invisible)
    View  viewInvisible;
    @Bind(R.id.skip)
    View skip;
    private ViewPager vp;
    private GuideViewPagerAdapter vpAdapter;
    private List<View> views;
    //记录当前选中位置
    private int currentIndex;

    //引导图片资源
    private static final int[] pics = {R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewInvisible.setOnClickListener(this);
        skip.setOnClickListener(this);
        views = new ArrayList<View>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        vp = (ViewPager) findViewById(R.id.viewpager);
        //初始化Adapter
        vpAdapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.setOnPageChangeListener(this);

    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }

        vp.setCurrentItem(position);
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_invisible:
                if(currentIndex>=2){
                    forward(NewIndexActivity.class);
                    finish();
                }else {
                    setCurView(currentIndex + 1);
                }
                break;
            case R.id.skip:
                forward(NewIndexActivity.class);
                finish();
                break;
        }

    }

}
