package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import cn.passguard.PassGuardEdit;

/**
 * Created by huwentao on 16/10/22.
 */

public class MyPassGuardEdit extends PassGuardEdit {
    public MyPassGuardEdit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        addTextChangedListener(new TextWatcher() {
            int length = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > getMaxWidth()) {
                    CharSequence input = s.subSequence(0, s.length() - 1);
                    setText(input);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
