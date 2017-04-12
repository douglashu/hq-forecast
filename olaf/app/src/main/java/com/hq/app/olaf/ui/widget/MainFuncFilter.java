package com.hq.app.olaf.ui.widget;

import android.view.View;

/**
 * Created by huwentao on 16-5-26.
 */
public class MainFuncFilter implements FuncFilter {

    @Override
    public boolean clickBefore(View funcBtnView) {
        return true;
    }

    @Override
    public void clickAfter(View funcBtnView) {

    }
}
