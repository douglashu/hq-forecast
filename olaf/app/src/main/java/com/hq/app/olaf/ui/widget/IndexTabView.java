package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;


import com.hq.app.olaf.R;
import cn.bingoogolapple.badgeview.BGABadgeRadioButton;

/**
 * Created by huwentao on 16/11/5.
 */

public class IndexTabView extends BGABadgeRadioButton {
    public IndexTabView(Context context) {
        super(context);
    }

    public IndexTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Drawable[] drawables = getCompoundDrawables();
                Drawable tabIcon = drawables[1];
                if (tabIcon != null) {
                    if (isChecked) {
                        tabIcon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    } else {
                        tabIcon.setColorFilter(getResources().getColor(R.color.index_tab_text), PorterDuff.Mode.SRC_ATOP);
                    }
                }
            }
        });
    }

    public void setDefault() {
        setChecked(true);
        Drawable[] drawables = getCompoundDrawables();
        Drawable tabIcon = drawables[1];
        if (tabIcon != null) {
            tabIcon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
