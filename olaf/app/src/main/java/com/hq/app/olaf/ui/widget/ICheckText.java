package com.hq.app.olaf.ui.widget;

import android.text.TextWatcher;

import java.util.regex.Pattern;

/**
 * Created by huwentao on 16/7/5.
 */
public interface ICheckText {
    /**
     * @param label
     */
    void setLabel(String label);

    /**
     * @param textValue
     */
    void setText(String textValue);

    /**
     * @return
     */
    String getText();

    /**
     *
     * @param textSize
     */
    void setTextSize(float textSize);

    /**
     *
     * @param textSize
     */
    void setLabelSize(float textSize);

    /**
     * @param hint
     */
    void setHint(String hint);

    /**
     * @param colorResId
     */
    void setHintColor(int colorResId);

    /**
     *
     * @param colorResId
     */
    void setLabelColor(int colorResId);
    /**
     * @param colorResId
     */
    void setTextColor(int colorResId);

    /**
     * @return
     */
    boolean checkInput();

    /**
     * @return
     */
    boolean isNotEmpty();

    /**
     * @param isNotEmpty
     */
    void setNotEmpty(boolean isNotEmpty);

    /**
     * @param emptyMessage
     */
    void setEmptyMessage(String emptyMessage);

    /**
     * @param pattern
     */
    void setPattern(Pattern pattern);

    /**
     * @param regErrorMessage
     */
    void setPatternErrorMessage(String regErrorMessage);

    /**
     * @param textWatcher
     */
    void setTextWatcher(TextWatcher textWatcher);

    /**
     * @param isEnable
     */
    void setViewEnable(boolean isEnable);
}
