package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hq.component.utils.DensityUtils;
import com.orhanobut.logger.Logger;

import java.util.regex.Pattern;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16/7/5.
 */
public class CustomItemText extends LinearLayout implements ICheckText {

    @Bind(R.id.leftIcon) ImageView leftIcon;
    @Bind(R.id.label) TextView label;
    @Bind(R.id.text) TextView text;
    @Bind(R.id.rightIcon) ImageView rightIcon;
    @Bind(R.id.viewLayout) LinearLayout viewLayout;

    private String mText;
    private String mLabel;
    private int mTextColor;
    private int mLabelColor;
    private float mLableSize;
    private float mTextSize;
    private String mForward;
    private Drawable mLeftIcon;
    private Drawable mRightIcon;
    private Bundle mBundle;
    private String mHint;

    public CustomItemText(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.layout_menu_item_view, this);
        ButterKnife.bind(layoutView);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ICheckText);
        mText = typedArray.getString(R.styleable.ICheckText_ict_text);
        mLabel = typedArray.getString(R.styleable.ICheckText_ict_label);
        mHint = typedArray.getString(R.styleable.ICheckText_ict_hint);
        mTextColor = typedArray.getColor(R.styleable.ICheckText_ict_textColor, -1);
        mLabelColor = typedArray.getColor(R.styleable.ICheckText_ict_labelColor, -1);
        mLableSize = typedArray.getDimensionPixelSize(R.styleable.ICheckText_ict_labelSize, DensityUtils.sp2px(getContext(), 16));
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ICheckText_ict_textSize, DensityUtils.sp2px(getContext(), 16));
        mForward = typedArray.getString(R.styleable.ICheckText_ict_menu_forward);
        mRightIcon = typedArray.getDrawable(R.styleable.ICheckText_ict_menu_right_icon);
        mLeftIcon = typedArray.getDrawable(R.styleable.ICheckText_ict_left_icon);
        typedArray.recycle();

        initView();
    }

    private void initView() {
        //1
        setLabel(mLabel);
        setLabelColor(mLabelColor);
        setLabelSize(mLableSize);
        //2
        setText(mText);
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setHint(mHint);
        //3
        setLeftIcon(mLeftIcon);
        //4
        setRightIcon(mRightIcon);
        //5
        if (!TextUtils.isEmpty(mForward)) {
            viewLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Class<?> aClass = Class.forName(mForward);
                        Intent intent = new Intent(getContext(), aClass);
                        if (mBundle != null) {
                            intent.putExtras(mBundle);
                        }
                        getContext().startActivity(intent);
                    } catch (Exception e) {
                        Logger.e(e.getMessage(), e);
                    }
                }
            });
        }
    }

    @Override
    public void setLabel(String label) {
        if (!TextUtils.isEmpty(label)) {
            this.label.setText(label);
            this.label.setVisibility(VISIBLE);
        } else {
            this.label.setVisibility(GONE);
        }
    }

    @Override
    public void setText(String textValue) {
        if (!TextUtils.isEmpty(textValue)) {
            this.text.setText(textValue);
            this.text.setVisibility(VISIBLE);
        } else {
            this.text.setVisibility(GONE);
        }
    }

    /**
     * @param iconResId
     */
    public void setLeftIcon(int iconResId) {
        if (iconResId > 0) {
            this.leftIcon.setImageResource(iconResId);
        } else {
            this.leftIcon.setVisibility(GONE);
        }
    }

    public void setLeftIcon(Drawable leftIcon) {
        if (leftIcon != null) {
            this.leftIcon.setImageDrawable(leftIcon);
        } else {
            this.leftIcon.setVisibility(GONE);
        }
    }

    /**
     * @param rightIconResId
     */
    public void setRightIcon(int rightIconResId) {
        if (rightIconResId > 0) {
            this.rightIcon.setImageResource(rightIconResId);
            this.rightIcon.setVisibility(VISIBLE);
        } else {
            this.rightIcon.setVisibility(GONE);
        }
    }

    public void setRightIcon(Drawable rightIcon) {
        if (rightIcon != null) {
            this.rightIcon.setImageDrawable(rightIcon);
        } else {
            this.rightIcon.setVisibility(GONE);
        }
    }

    public void setForwardParam(Bundle bundle) {
        this.mBundle = bundle;
    }

    /**
     * @param onClickListener
     */
    public void setOnMenuItemClickListener(OnClickListener onClickListener) {
        viewLayout.setOnClickListener(onClickListener);
    }

    @Override
    public String getText() {
        return this.text.getText().toString();
    }

    @Override
    public void setTextSize(float textSize) {
        this.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    public void setLabelSize(float textSize) {
        this.label.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    public void setHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            this.text.setText(hint);
        }
    }

    @Override
    public void setHintColor(int colorResId) {

    }

    @Override
    public void setLabelColor(int color) {
        this.label.setTextColor(color);
    }

    @Override
    public void setTextColor(int color) {
        this.text.setTextColor(color);
    }

    @Override
    public boolean checkInput() {
        return true;
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public void setNotEmpty(boolean isNotEmpty) {

    }

    @Override
    public void setEmptyMessage(String emptyMessage) {

    }

    @Override
    public void setPattern(Pattern pattern) {

    }

    @Override
    public void setPatternErrorMessage(String regErrorMessage) {

    }

    @Override
    public void setTextWatcher(TextWatcher textWatcher) {

    }

    @Override
    public void setViewEnable(boolean isEnable) {

    }
}
