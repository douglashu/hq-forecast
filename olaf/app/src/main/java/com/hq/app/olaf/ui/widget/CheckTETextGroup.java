package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by huwentao on 16-4-28.
 */
public class CheckTETextGroup extends LinearLayout {
    public CheckTETextGroup(Context context) {
        super(context);
    }

    public CheckTETextGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckTETextGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean check() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof TextEdittextView) {
                TextEdittextView textEdittextView = (TextEdittextView) getChildAt(i);
                boolean checkResult = textEdittextView.checkInput();
                if (!checkResult) return false;
            }
        }
        return true;
    }

    public void clear(){
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof TextEdittextView) {
                TextEdittextView textEdittextView = (TextEdittextView) getChildAt(i);
                textEdittextView.setTextValue("");
            }
        }
    }

    @Override
    public void invalidate() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof TextEdittextView) {
                view.invalidate();
            }
        }
        super.invalidate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof TextEdittextView) {
                view.setEnabled(enabled);
            }
        }
    }
}
