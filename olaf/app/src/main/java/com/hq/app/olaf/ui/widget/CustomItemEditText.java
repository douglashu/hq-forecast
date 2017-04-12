package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hq.component.utils.DensityUtils;
import com.hq.uicomponet.utils.SnackbarTool;

import java.text.DecimalFormat;
import java.util.regex.Pattern;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16/7/5.
 */
public class CustomItemEditText extends LinearLayout implements ICheckText {

    @Bind(R.id.leftIcon) ImageView leftIcon;
    @Bind(R.id.label) TextView label;
    @Bind(R.id.subLabel) TextView subLabel;
    @Bind(R.id.text) EditText text;

    private int mMaxLength;
    private String mUnit;
    private int mType;
    private int mLabelColor;
    private boolean isFormat;
    private String mLabel;
    private String mSublabel;
    private String mText;
    private String mHint;
    private String mEmptyMessage;
    private boolean isNotEmpty;
    private String mPattern;
    private String mPatternErrorMessage;
    private boolean mEnable;
    private int mHintColor;
    private int mTextColor;
    private float mLableSize;
    private float mTextSize;
    private Pattern pattern;
    private Drawable mLeftIcon;
    private boolean mSigleLine;
    private int mLines;

    public CustomItemEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.layout_item_edittext_view, this);
        ButterKnife.bind(layoutView);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ICheckText);
        mText = typedArray.getString(R.styleable.ICheckText_ict_text);
        mLabel = typedArray.getString(R.styleable.ICheckText_ict_label);
        mHint = typedArray.getString(R.styleable.ICheckText_ict_hint);
        mEmptyMessage = typedArray.getString(R.styleable.ICheckText_ict_emptyMessage);
        isNotEmpty = typedArray.getBoolean(R.styleable.ICheckText_ict_isNotEmpty, false);
        mPattern = typedArray.getString(R.styleable.ICheckText_ict_pattern);
        mPatternErrorMessage = typedArray.getString(R.styleable.ICheckText_ict_patternErrorMessage);
        mEnable = typedArray.getBoolean(R.styleable.ICheckText_ict_enable, true);
        mHintColor = typedArray.getColor(R.styleable.ICheckText_ict_hintColor, -1);
        mTextColor = typedArray.getColor(R.styleable.ICheckText_ict_textColor, -1);
        mLabelColor = typedArray.getColor(R.styleable.ICheckText_ict_labelColor, -1);
        mLableSize = typedArray.getDimensionPixelSize(R.styleable.ICheckText_ict_labelSize, DensityUtils.sp2px(getContext(), 16));
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ICheckText_ict_textSize, DensityUtils.sp2px(getContext(), 16));
        mLeftIcon = typedArray.getDrawable(R.styleable.ICheckText_ict_left_icon);
        mMaxLength = typedArray.getInt(R.styleable.ICheckText_ict_maxLength, -1);
        mSigleLine = typedArray.getBoolean(R.styleable.ICheckText_ict_sigleLine, false);
        mLines = typedArray.getInt(R.styleable.ICheckText_ict_lines, -1);
        mType = typedArray.getInt(R.styleable.ICheckText_ict_edittext_type, 1);
        mUnit = typedArray.getString(R.styleable.ICheckText_ict_edittext_unit);
        mSublabel = typedArray.getString(R.styleable.ICheckText_ict_edittext_subLabel);
        isFormat = typedArray.getBoolean(R.styleable.ICheckText_ict_edittext_isFormat, false);
        typedArray.recycle();

        initView();
    }

    private void initView() {
        setMaxLength(mMaxLength);
        setSingleLine(mSigleLine);
        if (!mSigleLine)
            setLines(mLines);
        setViewEnable(mEnable);
        setLeftIcon(mLeftIcon);
        //1
        setLabel(mLabel);
        setLabelColor(mLabelColor);
        setLabelSize(mLableSize);
        setSubLabel(mSublabel);
        //2
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setHint(mHint);
        setHintColor(mHintColor);
        //3
        if (!TextUtils.isEmpty(mPattern))
            pattern = Pattern.compile(mPattern);
        //4
        switch (mType) {
            case 2://money
                this.text.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (!TextUtils.isEmpty(mText)) {
                    try {
                        String money = mText;
                        if(isFormat){
                            double m = Double.parseDouble(money);
                            DecimalFormat format = new DecimalFormat("##,##0.##");
                            money = format.format(m);
                        }
                        if (!TextUtils.isEmpty(mUnit)) {
                            mText = String.format("%s%s",money , mUnit);
                        } else {
                            mText = money;
                        }
                        this.text.setOnFocusChangeListener(new OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (!hasFocus) {
                                    String moneyInput = getText();
                                    if (isFormat) {
                                        double m = Double.parseDouble(moneyInput);
                                        DecimalFormat format = new DecimalFormat("##,##0.##");
                                        moneyInput = format.format(m);
                                    }
                                    if (!TextUtils.isEmpty(mUnit)) {
                                        moneyInput = String.format("%s%s", moneyInput, mUnit);
                                    }
                                    setText(moneyInput);
                                }
                            }});
                    } catch (Exception e) {
                        mText = "";
                    }
                }
                break;
            case 3:
                this.text.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (isFormat) {
                    try {
                        if (!TextUtils.isEmpty(mText)) {
                            double number = Double.parseDouble(mText);
                            DecimalFormat format = new DecimalFormat("##,##0.##");
                            mText = format.format(number);
                        }
                    } catch (Exception e) {
                        mText = "";
                    }
                }
                break;
            case 4:
                this.text.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 5:
                this.text.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
        setText(mText);
    }

    public void setMaxLength(int maxLength) {
        if (mMaxLength >= 0) {
            this.text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        } else {
            this.text.setFilters(new InputFilter[0]);
        }
    }

    public void setSingleLine(boolean isSingleLine) {
        if (isSingleLine) {
            setLines(1);
        }
    }

    public void setLines(int lines) {
        if (lines > 0) {
            this.text.setLines(lines);
        }
    }

    @Override
    public void setLabel(String label) {
        this.label.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(label)) {
            this.label.setText(label);
        } else {
            this.label.setText("");
        }
    }

    public void setSubLabel(String subLabel) {
        if (!TextUtils.isEmpty(subLabel)) {
            this.subLabel.setText(subLabel);
            this.subLabel.setVisibility(VISIBLE);
        } else {
            this.subLabel.setText("");
            this.subLabel.setVisibility(GONE);
        }
    }

    @Override
    public void setText(String textValue) {
        this.text.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(textValue)) {
            this.text.setText(textValue);
        } else {
            this.text.setText("");
        }
    }


    @Override
    public String getText() {
        if (!TextUtils.isEmpty(mUnit)) {
            return this.text.getText().toString().replace(mUnit, "");
        } else {
            return this.text.getText().toString();
        }
    }

    @Override
    public void setTextSize(@NonNull float textSize) {
        this.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    public void setLabelSize(float textSize) {
        this.label.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    public void setHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            this.text.setHint(hint);
        } else {
            this.text.setHint("");
        }
    }

    @Override
    public void setHintColor(int color) {
        if (color > 0) {
            this.text.setHintTextColor(color);
        }
    }

    @Override
    public void setLabelColor(int color) {
        if (color > 0) {
            this.label.setTextColor(color);
        }
    }

    @Override
    public void setTextColor(int color) {
        if (color > 0) {
            this.text.setTextColor(color);
        }
    }

    @Override
    public boolean checkInput() {
        if (isNotEmpty) {
            if (TextUtils.isEmpty(this.text.getText())) {
                showSnacker(mEmptyMessage);
                return false;
            }
        }
        if (pattern != null) {
            boolean match = pattern.matcher(this.text.getText().toString()).matches();
            if (!match) {
                showSnacker(mPatternErrorMessage);
            }
        }
        return true;
    }

    private void showSnacker(String message) {
        SnackbarTool.show(this, message);
    }

    @Override
    public boolean isNotEmpty() {
        return isNotEmpty;
    }

    @Override
    public void setNotEmpty(boolean isNotEmpty) {
        this.isNotEmpty = isNotEmpty;
    }

    @Override
    public void setEmptyMessage(String emptyMessage) {
        this.mEmptyMessage = emptyMessage;
    }

    @Override
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void setPatternErrorMessage(String regErrorMessage) {
        this.mPatternErrorMessage = regErrorMessage;
    }

    @Override
    public void setTextWatcher(TextWatcher textWatcher) {
        this.text.addTextChangedListener(textWatcher);
    }

    @Override
    public void setViewEnable(boolean isEnable) {
        this.text.setEnabled(isEnable);
    }

    public void setLeftIcon(int iconResId) {
        if (iconResId > 0) {
            this.leftIcon.setImageResource(iconResId);
        } else {
            this.leftIcon.setVisibility(GONE);
        }
    }

    public void setLeftIcon(Drawable leftIcon){
        if (leftIcon!=null) {
            this.leftIcon.setImageDrawable(leftIcon);
        } else {
            this.leftIcon.setVisibility(GONE);
        }
    }
}
