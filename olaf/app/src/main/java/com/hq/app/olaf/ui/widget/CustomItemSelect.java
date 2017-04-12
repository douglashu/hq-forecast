package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.InputFilter;
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
import com.hq.uicomponet.utils.SnackbarTool;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16/7/5.
 */
public class CustomItemSelect extends LinearLayout implements ICheckText {

    @Bind(R.id.leftIcon) ImageView leftIcon;
    @Bind(R.id.label) TextView label;
    @Bind(R.id.subLabel) TextView subLabel;
    @Bind(R.id.selectHint) TextView selectHint;
    @Bind(R.id.selectLayout) LinearLayout selectLayout;

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
    private int mLeftIcon;
    private int mMaxLength;
    private boolean mSigleLine;
    private int mLines;

    private Collection<? extends SelItem> mSelectValues;
    private SelItem mSelectedValue;
    private SelAdapter mSelAdapter;
    private SelDialog mSelectDialog;

    public CustomItemSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.layout_item_select_view, this);
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
        mLeftIcon = typedArray.getResourceId(R.styleable.ICheckText_ict_left_icon, -1);
        mMaxLength = typedArray.getInt(R.styleable.ICheckText_ict_maxLength, -1);
        mSigleLine = typedArray.getBoolean(R.styleable.ICheckText_ict_sigleLine, false);
        mLines = typedArray.getInt(R.styleable.ICheckText_ict_lines, -1);
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
        setText(mText);
        setHint(mHint);
        setHintColor(mHintColor);
    }

    public void setMaxLength(int maxLength) {
        if (mMaxLength >= 0) {
            this.selectHint.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        } else {
            this.selectHint.setFilters(new InputFilter[0]);
        }
    }

    public void setSingleLine(boolean isSingleLine) {
        if (isSingleLine) {
            setLines(1);
        }
    }

    public void setLines(int lines) {
        if (lines > 0) {
            this.selectHint.setLines(lines);
        }
    }

    /**
     * @param selectValues
     * @param title
     * @return
     */
    public CustomItemSelect init(List<? extends SelItem> selectValues, String title) {
        mSelectValues = selectValues;
        if (TextUtils.isEmpty(title)) {
            mSelAdapter = new SelAdapter(selectValues);
        } else {
            mSelAdapter = new SelAdapter(selectValues, title);
        }
        mSelectDialog = new SelDialog(getContext(), mSelAdapter);
        mSelectDialog.setTitle("选择所在省");
        selectLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectDialog.show();
            }
        } );
        return this;
    }

    /**
     * @param onItemClickListener
     */
    public void onItemSelected(SelDialog.OnItemClickListener onItemClickListener) {
        mSelectDialog.setOnItemClickListener(onItemClickListener);
    }

    /**
     *
     * @param onClickListener
     */
    public void onSelectClickListener(OnClickListener onClickListener){
        selectLayout.setOnClickListener(onClickListener);
    }

    @Override
    public void setLabel(String label) {
        if (!TextUtils.isEmpty(label)) {
            this.label.setVisibility(VISIBLE);
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
        this.selectHint.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(textValue)) {
            this.selectHint.setText(textValue);
        } else {
            this.selectHint.setText("");
        }
    }

    public void setSelectedValue(SelItem selectedValue) {
        this.mSelectedValue = selectedValue;
        this.selectHint.setText(selectedValue.getValue());
    }

    public SelItem getSelectedValue() {
        return this.mSelectedValue;
    }

    @Override
    public String getText() {
        return this.selectHint.getText().toString();
    }

    @Override
    public void setTextSize(@NonNull float textSize) {
        this.selectHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    public void setLabelSize(float textSize) {
        this.label.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    public void setHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            this.selectHint.setText(hint);
        } else {
            this.selectHint.setText("");
        }
    }

    @Override
    public void setHintColor(int color) {
        if (color > 0) {
            this.selectHint.setHintTextColor(color);
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
            this.selectHint.setTextColor(color);
        }
    }

    @Override
    public boolean checkInput() {
        if (isNotEmpty) {
            if (mSelectedValue == null) {
                showSnacker(mEmptyMessage);
                return false;
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
        this.selectHint.addTextChangedListener(textWatcher);
    }

    @Override
    public void setViewEnable(boolean isEnable) {
        this.selectHint.setEnabled(isEnable);
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
