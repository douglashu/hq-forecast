package com.hq.app.olaf.ui.widget;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16-4-25.
 */
public class MenuItemView extends LinearLayout {
    @Bind(R.id.menuImage) ImageView menuImage;
    @Bind(R.id.rightArrow) ImageView rightArrow;
    @Bind(R.id.menuText) TextView menuText;
    @Bind(R.id.labelHint) TextView labelHintText;
    @Bind(R.id.rootLayout) LinearLayout rootLayout;
    @Bind(R.id.function) Button function;
    @Bind(R.id.menuRightText) TextView menuRightText;


    private Class<?> targetClass;
    private Map<String, String> params = new HashMap<>();

    public MenuItemView(Context context) {
        super(context);
    }

    public MenuItemView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.layout_menuitem_view, this);
        ButterKnife.bind(this, layoutView);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.menuitemview);
        String itemText = typedArray.getString(R.styleable.menuitemview_itemText);
        String itemRightText = typedArray.getString(R.styleable.menuitemview_itemRightText);
        String labelHint = typedArray.getString(R.styleable.menuitemview_labelHint);
        String labelText = typedArray.getString(R.styleable.menuitemview_labelText);
        String funcText = typedArray.getString(R.styleable.menuitemview_funcText);
        boolean isShowRight = typedArray.getBoolean(R.styleable.menuitemview_showRight, false);
        int resourceId = typedArray.getResourceId(R.styleable.menuitemview_itemImage, -1);
        final String to = typedArray.getString(R.styleable.menuitemview_menuTo);
        if (!TextUtils.isEmpty(itemText)) {
            menuText.setText(itemText);
        }
        if (!TextUtils.isEmpty(itemRightText)) {
            menuRightText.setText(itemRightText);
        }
        if (resourceId > 0) {
            menuImage.setImageResource(resourceId);
        } else {
            menuImage.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(labelHint)) {
            labelHintText.setVisibility(VISIBLE);
            labelHintText.setHint(labelHint);
        }
        if (!TextUtils.isEmpty(labelText)) {
            labelHintText.setVisibility(VISIBLE);
            labelHintText.setText(labelText);
        }
        if (!TextUtils.isEmpty(funcText)) {
            function.setVisibility(VISIBLE);
            function.setText(funcText);
        }
        if (isShowRight) {
            rightArrow.setVisibility(VISIBLE);
        }
        rootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(to)) {
                    try {
                        targetClass = Class.forName(to);
                        Intent intent = new Intent(getContext(), targetClass);
                        Set<String> keySet = params.keySet();
                        if (keySet.size() > 0) {
                            for (String key : keySet) {
                                intent.putExtra(key, params.get(key));
                            }
                        }
                        getContext().startActivity(intent);
                        if(context instanceof Activity){
                            ((Activity) context).overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
                        }
                    } catch (ClassNotFoundException e) {
                        Logger.e(e, e.getMessage());
                    }
                }
            }
        });
        typedArray.recycle();

    }

    public MenuItemView setShowArrow() {
        rightArrow.setVisibility(VISIBLE);
        return this;
    }

    public MenuItemView setLabelHint(String text) {
        if (!TextUtils.isEmpty(text)) {
            labelHintText.setVisibility(VISIBLE);
            labelHintText.setHint(text);
        }
        return this;
    }

    public MenuItemView setLabelText(String text) {
        if (!TextUtils.isEmpty(text)) {
            labelHintText.setVisibility(VISIBLE);
            labelHintText.setText(text);
        }
        return this;
    }

    public MenuItemView setFuncText(String funcText) {
        setFuncText(funcText, false, null);
        return this;
    }

    public MenuItemView setFuncText(String funcText, OnClickListener listener) {
        setFuncText(funcText, true, listener);
        return this;
    }

    public MenuItemView setFuncText(String funcText, boolean enable, OnClickListener listener) {
        if (!TextUtils.isEmpty(funcText)) {
            function.setText(funcText);
            function.setEnabled(enable);
            if (listener != null)
                setOnFuncClickListener(listener);
        }
        return this;
    }


    public void addParams(String key, String value) {
        params.put(key, value);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        rootLayout.setOnClickListener(l);
    }

    public void setOnFuncClickListener(OnClickListener l) {
        if (l != null) {
            function.setOnClickListener(l);
        }
    }
}
