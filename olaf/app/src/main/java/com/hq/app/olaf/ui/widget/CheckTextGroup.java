package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hq.component.network.net.KeyValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by huwentao on 16-4-28.
 */
public class CheckTextGroup extends LinearLayout {
    private boolean enable = true;
    private SparseArray<Integer> viewInput = new SparseArray<>();
    private Map<Integer, String> emptyMessage = new HashMap<>();

    public CheckTextGroup(Context context) {
        super(context);
    }

    public CheckTextGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckTextGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean check(int... exceptId) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof ICheckText) {
                if (view.getVisibility() == VISIBLE && view.isEnabled()) {
                    ICheckText iCheckText = (ICheckText) view;
                    if (!iCheckText.checkInput()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param exceptId
     * @return
     */
    public boolean checkEmpty(Map<Integer, String> emptyMessage, Map<Integer, KeyValue> selectValue, int... exceptId) {
        this.emptyMessage.clear();
        this.emptyMessage.putAll(emptyMessage);
        List<View> childViewList = getChildViews(this);
        for (int i = 0; i < childViewList.size(); i++) {
            View view = childViewList.get(i);
            if (view instanceof EditText) {
                boolean isEmpty = checkEmpty((EditText) view, null, exceptId);
                if (isEmpty) {
                    showTips(emptyMessage.get(view.getId()));
                    return false;
                }
            } else if (view instanceof TextView) {
                boolean isEmpty = checkEmpty((TextView) view, selectValue, exceptId);
                if (isEmpty) {
                    showTips(selectValue.get(view.getId()).getValueStr());
                    return false;
                }
            }
        }
        return true;
    }

    private List<View> getChildViews(ViewGroup viewGroup) {
        List<View> childViewSet = new LinkedList<>();
        if (viewGroup == null) return childViewSet;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof Button) continue;
            if (view instanceof ViewGroup) {
                childViewSet.addAll(getChildViews((ViewGroup) view));
            } else if (view instanceof EditText && view.getId() > 0) {
                childViewSet.add(view);
            } else if (view instanceof TextView && view.getId() > 0) {
                childViewSet.add(view);
            }
        }
        return childViewSet;
    }

    private void showTips(String msg) {
        Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show();
    }

    private int findId(int id, int... exceptIds) {
        for (int i = 0; i < exceptIds.length; i++) {
            if (id == exceptIds[i]) return i;
        }
        return -1;
    }

    private boolean checkEmpty(TextView view, Map<Integer, KeyValue> selectValue, int... exceptId) {
        if (exceptId != null && exceptId.length > 0) {
            int index = findId(view.getId(), exceptId);
            if (index < 0) {
                if (selectValue == null) {
                    return TextUtils.isEmpty(view.getText());
                } else {
                    KeyValue keyValue = selectValue.get(view.getId());
                    if (keyValue == null) return true;
                    return TextUtils.isEmpty(keyValue.key);
                }
            }
            return false;
        } else {
            if (selectValue == null) {
                return TextUtils.isEmpty(view.getText());
            } else {
                return TextUtils.isEmpty(selectValue.get(view.getId()).key);
            }
        }
    }

    /**
     * @param selectValues
     * @param title
     * @return
     */
    public CheckTextGroup init(View selectView,
                               final List<? extends SelItem> selectValues,
                               final String title,
                               final SelDialog.OnItemClickListener onItemClickListener) {
        selectView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enable) {
                    SelAdapter mSelAdapter = null;
                    if (TextUtils.isEmpty(title)) {
                        mSelAdapter = new SelAdapter(selectValues);
                    } else {
                        mSelAdapter = new SelAdapter(selectValues, title);
                    }
                    SelDialog mSelectDialog = new SelDialog(getContext(), mSelAdapter);
                    mSelectDialog.setOnItemClickListener(onItemClickListener);
                    mSelectDialog.show();
                }
            }
        });
        return this;
    }

    /**
     * @param editText
     * @param pattern
     * @return
     */
    public boolean checkPattern(EditText editText, String pattern) {
        Pattern reg = Pattern.compile(pattern);
        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text))
            return false;
        return reg.matcher(text).matches();
    }

    public void clear() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) view;
                for (int j = 0; j < layout.getChildCount(); j++) {
                    View childView = layout.getChildAt(j);
                    if (childView instanceof EditText) {
                        ((EditText) childView).setText("");
                    }
                }
            } else if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }

    @Override
    public void invalidate() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof ICheckText) {
                view.invalidate();
            }
        }
        super.invalidate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enable = enabled;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) view;
                for (int j = 0; j < layout.getChildCount(); j++) {
                    View childView = layout.getChildAt(j);
                    if (childView instanceof EditText) {
                        setEnable(childView, enabled);
                    }
                }
            } else if (view instanceof EditText) {
                setEnable(view, enabled);
            }
        }
    }

    private void setEnable(View view, boolean enabled) {
        EditText text = (EditText) view;
        if (!enabled) {
            text.setClickable(false);
            text.setFocusable(false);
        } else {
            text.setClickable(true);
            text.setFocusable(true);
        }
    }
}
