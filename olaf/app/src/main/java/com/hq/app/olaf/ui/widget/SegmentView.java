package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;



import java.util.List;

import com.hq.app.olaf.R;

/**
 * SegmentView是一个类似于iOS的segment
 * control显示效果的一个控件，使用RadioGroup与RadioButton实现，使用时用户需要调用{import com.cardinfo.anypay.R;
 * <p>
 * import java.util.List;
 *
 * @author mrsimple
 * @link #setTabs(List)}
 * 方法设置tabs,然后调用 {@link #setOnItemCheckedListener(OnItemCheckedListener)}
 * 设置点击每个tab时的回调函数.
 */
public class SegmentView extends RadioGroup {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 默认的RadioButton id,从0 开始
     */
    int ids = 0;
    /**
     * RadioButton的数量
     */
    int childCount = 0;
    /**
     * 绘制分割线时的padding值
     */
    int linePadding = 15;
    /**
     * 选中回调
     */
    private OnItemCheckedListener mCheckedListener;

    public SegmentView(Context context) {
        this(context, null);
    }

    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        mPaint.setColor(getResources().getColor(R.color.radio_stroke_color));
        setupOnItenClickListener();
    }

    private void setupOnItenClickListener() {
        super.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 包装回调
                if (mCheckedListener != null) {
                    RadioButton checkedButton = (RadioButton) findViewById(checkedId);
                    mCheckedListener.onCheck(checkedButton, checkedId, checkedButton.getText()
                            .toString());
                }
            }
        });
    }

    public OnItemCheckedListener getmCheckedListener() {
        return mCheckedListener;
    }

    public void setTabs(String[] tabTitles) {
        removeAllViews();
        for (int i = 0; i < tabTitles.length; i++) {
            if (i == 0) {
                addTab(tabTitles[i], true);
            } else {
                addTab(tabTitles[i], false);
            }
        }
    }

    public void setTabs(List<String> tabTitles) {
        removeAllViews();
        for (int i = 0; i < tabTitles.size(); i++) {
            if (i == 0) {
                addTab(tabTitles.get(i), true);
            } else {
                addTab(tabTitles.get(i), false);
            }
        }
    }

    public void addTab(String title, boolean isChecked) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(
                R.layout.layout_radio_button_item, this, false);
        radioButton.setId(ids++);
        radioButton.setText(title);
        radioButton.setChecked(isChecked);
        // 添加到当前ViewGroup中
        this.addView(radioButton);
    }

    /**
     * 绘制tab之间的分割线
     *
     * @param canvas
     */
    private void drawSeparateLines(Canvas canvas) {
        childCount = getChildCount();
        if (childCount == 0) {
            throw new IllegalArgumentException("SegmentView's child is zero !");
        }

        if (getOrientation() == HORIZONTAL) {
            int childWidth = this.getWidth() / childCount;
            for (int i = 1; i < childCount; i++) {
                int startX = childWidth * i;
                canvas.drawLine(startX, linePadding, startX, this.getHeight()
                        - linePadding, mPaint);
            }
        } else {
            int childHeight = this.getHeight() / childCount;
            for (int i = 1; i < childCount; i++) {
                int startY = childHeight * i;
                canvas.drawLine(linePadding, startY, this.getWidth() - linePadding, startY, mPaint);
            }
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawSeparateLines(canvas);
    }

    public void setOnItemCheckedListener(OnItemCheckedListener listener) {
        mCheckedListener = listener;
    }

    /*
     * 使用 @see setOnItemCheckedListener 来设置回调
     * @see android.widget.RadioGroup#setOnCheckedChangeListener(android.widget.
     * RadioGroup.OnCheckedChangeListener)
     */
    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(listener);
    }

    /**
     * tab点击时的回调接口类
     *
     * @author mrsimple
     */
    public static interface OnItemCheckedListener {
        /**
         * @param button   被选中的按钮
         * @param position 被选中的按钮所在的位置
         * @param title    被选中的按钮的文本,即标题
         */
        public void onCheck(RadioButton button, int position, String title);
    }

}
