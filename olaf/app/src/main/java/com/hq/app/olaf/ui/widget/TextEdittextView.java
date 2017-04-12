package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hq.component.utils.TextHelper;
import com.hq.uicomponet.utils.SnackbarTool;

import java.util.regex.Pattern;

import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16-4-25.
 */
public class TextEdittextView extends LinearLayout {
    @Bind(R.id.label) TextView textLabel;
    @Bind(R.id.subLabel) TextView textSubLabel;
    @Bind(R.id.selectHint) TextView selectHintText;
    @Bind(R.id.content) EditText editContent;
    @Bind(R.id.selectLayout) LinearLayout selectLayout;
    @Bind(R.id.selectImage) ImageView selectImage;
    @Bind(R.id.labelIcon) ImageView labelIconView;

    private int textType;
    private String unitText;
    private boolean isNotEmpty;
    private String regexp;
    private SelAdapter adapters;
    private Pattern pattern = null;
    private String emptyMessage;
    private String regErrorMessage;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private TextWatcher textWatcher;
    private int selectHintColorEnable;
    private int selectHintColorDisable;
    private int editTextColorEnable;
    private int editTextColorDisable;

    public TextEdittextView(Context context) {
        super(context);
    }

    public TextEdittextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layoutView = LayoutInflater.from(context).inflate(R.layout.layout_textedit_view, this);
        ButterKnife.bind(this, layoutView);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextEdittextView);
        String label = typedArray.getString(R.styleable.TextEdittextView_label);
        String subLabel = typedArray.getString(R.styleable.TextEdittextView_subLabel);
        String textHint = typedArray.getString(R.styleable.TextEdittextView_hint);
        String textValue = typedArray.getString(R.styleable.TextEdittextView_text);
        String selectHint = typedArray.getString(R.styleable.TextEdittextView_selectHint);
        emptyMessage = typedArray.getString(R.styleable.TextEdittextView_emptyMessage);
        regErrorMessage = typedArray.getString(R.styleable.TextEdittextView_regErrorMessage);
        unitText = typedArray.getString(R.styleable.TextEdittextView_unitText);
        regexp = typedArray.getString(R.styleable.TextEdittextView_regexp);
        textType = typedArray.getInt(R.styleable.TextEdittextView_type, 3);
        float labelSize = typedArray.getDimension(R.styleable.TextEdittextView_labelSize, 16);
        float editTextSize = typedArray.getDimension(R.styleable.TextEdittextView_editTextSize, 14);
        int labelIconResId = typedArray.getResourceId(R.styleable.TextEdittextView_labelIcon, -1);
        int maxLength = typedArray.getInteger(R.styleable.TextEdittextView_maxLength, -1);
        int maxLines = typedArray.getInteger(R.styleable.TextEdittextView_maxLines, 1);
        int textColor = typedArray.getResourceId(R.styleable.TextEdittextView_textColor, -1);
        int labelColor = typedArray.getResourceId(R.styleable.TextEdittextView_labelColor, -1);
        isNotEmpty = typedArray.getBoolean(R.styleable.TextEdittextView_notEmpty, false);

        textLabel.setTextSize(labelSize);
        editContent.setTextSize(editTextSize);
        TextHelper.setText(textLabel, label);
        TextHelper.setText(textSubLabel, subLabel);

        selectHintColorEnable = editContent.getCurrentTextColor();
        selectHintColorDisable = textLabel.getCurrentTextColor();
        editTextColorEnable = selectHintColorEnable;
        editTextColorDisable = selectHintColorDisable;


        if (!TextUtils.isEmpty(regexp)) {
            pattern = Pattern.compile(regexp);
        }
        if (TextUtils.isEmpty(subLabel)) {
            textSubLabel.setVisibility(GONE);
        } else {
            textSubLabel.setVisibility(VISIBLE);
        }
        if (labelIconResId > 0) {
            labelIconView.setVisibility(VISIBLE);
            labelIconView.setImageResource(labelIconResId);
        } else {
            labelIconView.setVisibility(GONE);
        }
        if (maxLength >= 0) {
            editContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            editContent.setFilters(NO_FILTERS);
        }
        if (maxLines == 1) {
            editContent.setSingleLine();
            selectHintText.setSingleLine();
        } else {
            editContent.setMaxLines(maxLines);
            selectHintText.setMaxLines(maxLines);
        }
        switch (textType) {
            case 1://select
                selectLayout.setVisibility(VISIBLE);
                editContent.setVisibility(GONE);
                editContent.setText("");
                TextHelper.setText(selectHintText, selectHint);
                int hintColor = editContent.getCurrentHintTextColor();
                selectHintText.setTextColor(hintColor);
                break;
            case 2://text
                selectLayout.setVisibility(VISIBLE);
                editContent.setVisibility(GONE);
                selectImage.setVisibility(GONE);
                setTextText(textValue);
                break;
            case 3://edittext
                selectLayout.setVisibility(GONE);
                editContent.setVisibility(VISIBLE);
                editContent.setText("");
                if (!TextUtils.isEmpty(textHint)) {
                    editContent.setHint(textHint);
                }
                setEditTextText(textValue);
                break;
            case 4://金额
                selectLayout.setVisibility(GONE);
                editContent.setVisibility(VISIBLE);
                editContent.setText("");
                final String placeText = TextUtils.isEmpty(unitText) ? "元" : unitText;
                editContent.setHint(placeText);
                editContent.setInputType(InputType.TYPE_CLASS_NUMBER);
                editContent.setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String textStr = editContent.getText().toString();
                        if (!hasFocus) {
                            TextHelper.setText(editContent, "%s %s", textStr, placeText);
                        } else {
                            editContent.setText("");
                        }
                    }
                });
                setAmountText(textValue);
                break;
            case 5://纯数字输入
                selectLayout.setVisibility(GONE);
                editContent.setVisibility(VISIBLE);
                editContent.setText("");
                if (!TextUtils.isEmpty(textHint)) {
                    editContent.setHint(textHint);
                }
                editContent.setInputType(InputType.TYPE_CLASS_NUMBER);
                setNumberText(textValue);
                break;
            case 6:
                selectLayout.setVisibility(GONE);
                editContent.setVisibility(VISIBLE);
                editContent.setText("");
                if (!TextUtils.isEmpty(textHint)) {
                    editContent.setHint(textHint);
                }
                editContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                setEditTextText(textValue);
                break;
            case 7:
                selectLayout.setVisibility(GONE);
                editContent.setVisibility(VISIBLE);
                editContent.setText("");
                if (!TextUtils.isEmpty(textHint)) {
                    editContent.setHint(textHint);
                }
                editContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                setEditTextText(textValue);
                break;
        }
        if (textColor > 0) {
            selectHintText.setTextColor(getResources().getColor(textColor));
            editContent.setTextColor(getResources().getColor(textColor));
        }
        if (labelColor > 0) {
            textLabel.setTextColor(getResources().getColor(labelColor));
        }
        typedArray.recycle();
    }

    /**
     * @param textValue
     */
    private void setTextText(String textValue) {
        TextHelper.setText(selectHintText, textValue);
    }

    /**
     * @param textValue
     */
    private void setEditTextText(String textValue) {
        TextHelper.setText(editContent, textValue);
    }

    private void setAmountText(String textValue) {
        TextHelper.setText(editContent, textValue);
    }

    private void setNumberText(String textValue) {
        TextHelper.setText(editContent, textValue);
    }

    /**
     * @param label
     */
    public void setTextLabel(String label) {
        TextHelper.setText(textLabel, label);
    }

    /**
     * @param size
     */
    public void setLabelSize(int size) {
        textLabel.setTextSize(size);
    }

    /**
     * @param hintText
     */
    public void setTextHint(String hintText) {
        if (textType >= 3 && !TextUtils.isEmpty(hintText)) {
            editContent.setHint(hintText);
        }
    }

    /**
     * @param textValue
     */
    public void setTextValue(String textValue) {
        switch (textType) {
            case 1://select
                setSelectHint(textValue);
                break;
            case 2://text
                setTextText(textValue);
                break;
            case 3://edittext
                setEditTextText(textValue);
                break;
            case 4://金额
                setAmountText(textValue);
                break;
            case 5://纯数字输入
                setNumberText(textValue);
                break;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            editContent.setEnabled(false);
            editContent.setTextColor(editTextColorDisable);
            selectHintText.setTextColor(selectHintColorDisable);
        } else {
            editContent.setEnabled(true);
            editContent.setTextColor(editTextColorEnable);
            selectHintText.setTextColor(selectHintColorEnable);
        }
    }

    /**
     * @param hintText
     */
    public void setSelectHint(String hintText) {
        if (textType == 1) {
            selectHintText.setError(null);
            selectHintText.setTextColor(selectHintColorEnable);
            TextHelper.setText(selectHintText, hintText);
        }
    }

    /**
     * @param iconResId
     */
    public void setLabelIcon(int iconResId) {
        if (iconResId > 0) {
            labelIconView.setVisibility(VISIBLE);
            labelIconView.setImageResource(iconResId);
        } else {
            labelIconView.setVisibility(GONE);
        }
    }

    /**
     * @param subLabel
     */
    public void setTextSubLabel(String subLabel) {
        TextHelper.setText(textSubLabel, subLabel);
        if (TextUtils.isEmpty(subLabel)) {
            textSubLabel.setVisibility(GONE);
        } else {
            textSubLabel.setVisibility(VISIBLE);
        }
    }

    /**
     * @return
     */
    public String getEditTextValue() {
        if (textType >= 3) {
            String value = editContent.getText().toString();
            String placeText = TextUtils.isEmpty(unitText) ? "元" : unitText;
            return textType == 4 ? value.replace(placeText, "").trim() : value.trim();
        }
        return "";
    }

    @Deprecated
    public View getSelectView() {
        return selectLayout;
    }

    /**
     * @param onSelectClick
     */
    public void setOnSelectClick(final OnClickListener onSelectClick) {
        selectLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnabled()) {
                    onSelectClick.onClick(v);
                }
            }
        });
    }

    public boolean checkInput() {
        String text = editContent.getText().toString();
        if (textType >= 3 && isNotEmpty) {
            if (editContent.getVisibility() == VISIBLE && TextUtils.isEmpty(text.trim())) {
//                editContent.setError(emptyMessage);
                showSnackBar(emptyMessage);
                return false;
            }
        }
        if (textType == 1 && isNotEmpty) {
            String selValue = selectHintText.getText().toString();
            if (TextUtils.isEmpty(selValue)) {
//                selectHintText.setError(emptyMessage);
                showSnackBar(emptyMessage);
                return false;
            }
        }
        if (textType >= 3 && !TextUtils.isEmpty(regexp)) {
            if (editContent.getVisibility() == VISIBLE && !pattern.matcher(text).matches()) {
//                editContent.setError(regErrorMessage);
                showSnackBar(regErrorMessage);
                return false;
            }
        }
        return true;
    }

    protected void showSnackBar(String content) {
        View mRootView = getRootView();
        if (mRootView != null) {
            SnackbarTool.show(this, content);
        }
    }

    public void setOnSelectListener(OnClickListener clickListener) {
        selectLayout.setOnClickListener(clickListener);
    }

    public boolean isNotEmpty() {
        return isNotEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        isNotEmpty = notEmpty;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public String getRegErrorMessage() {
        return regErrorMessage;
    }

    public void setRegErrorMessage(String regErrorMessage) {
        this.regErrorMessage = regErrorMessage;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
        if (textWatcher != null) {
            editContent.addTextChangedListener(textWatcher);
        }
    }

}
