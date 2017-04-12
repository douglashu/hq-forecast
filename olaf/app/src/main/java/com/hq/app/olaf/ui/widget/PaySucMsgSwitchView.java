package com.hq.app.olaf.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hq.app.olaf.R;
import com.hq.component.utils.DensityUtils;

/**
 * Created by huwentao on 16/11/9.
 */

public class PaySucMsgSwitchView extends FrameLayout implements View.OnClickListener {

    private TextView mSwitchBackgroudView;
    private TextView mStateView;
    private int mStateViewWidth;
    private int mState = 2;
    public final static int STATE_OPEN = 1;
    public final static int STATE_CLOSE = 2;

    public PaySucMsgSwitchView(Context context) {
        super(context);
    }

    public PaySucMsgSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_msg_switchview, this);
        mStateView = (TextView) view.findViewById(R.id.state);
        mSwitchBackgroudView = (TextView) view.findViewById(R.id.switchBackgroud);
        ViewTreeObserver viewTreeObserver = mStateView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mStateViewWidth = mStateView.getMeasuredWidth();
                return true;
            }
        });
        setOnClickListener(this);
    }

    public void switchState(boolean isOpen, boolean isShowAnim) {
        if (isOpen) {
            mState = STATE_OPEN;
        } else {
            mState = STATE_CLOSE;
        }
        if (mStateViewWidth == 0) {
            mStateViewWidth = DensityUtils.dip2px(getContext(), 32);
        }
        switch (mState) {
            case STATE_OPEN:
                if (isShowAnim) {
                    showStateOpen();
                } else {
                    mSwitchBackgroudView.setBackgroundResource(R.drawable.shape_msg_switch_state1);
                    mStateView.setBackgroundResource(R.drawable.shape_msg_switch_state3);
                    LayoutParams layoutParams = (LayoutParams) mStateView.getLayoutParams();
                    layoutParams.setMargins(mStateViewWidth, 0, 0, 0);
                    mStateView.setLayoutParams(layoutParams);
                }
                break;
            case STATE_CLOSE:
                if (isShowAnim) {
                    showStateClose();
                } else {
                    mSwitchBackgroudView.setBackgroundResource(R.drawable.shape_msg_switch_state0);
                    mStateView.setBackgroundResource(R.drawable.shape_msg_switch_state2);
                }
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void showStateClose() {
        mState = STATE_CLOSE;
        mSwitchBackgroudView.setBackgroundResource(R.drawable.shape_msg_switch_state0);
        mStateView.setBackgroundResource(R.drawable.shape_msg_switch_state2);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mStateView, "x", mStateViewWidth, 0);
        objectAnimator.setDuration(150);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void showStateOpen() {

        mState = STATE_OPEN;
        mSwitchBackgroudView.setBackgroundResource(R.drawable.shape_msg_switch_state1);
        mStateView.setBackgroundResource(R.drawable.shape_msg_switch_state3);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mStateView, "x", 0, mStateViewWidth);
        objectAnimator.setDuration(150);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    @Override
    public void onClick(View view) {
        int tempState = mState;
        switch (tempState) {
            case STATE_CLOSE:
                tempState = STATE_OPEN;
                break;
            case STATE_OPEN:
                tempState = STATE_CLOSE;
                break;
        }
        if (switchListener != null) {
            boolean flag = switchListener.onSwitch(tempState);
            if (flag) {
                switch (mState) {
                    case STATE_CLOSE:
                        showStateOpen();
                        break;
                    case STATE_OPEN:
                        showStateClose();
                        break;
                }
            }
        } else {
            switch (mState) {
                case STATE_CLOSE:
                    showStateOpen();
                    break;
                case STATE_OPEN:
                    showStateClose();
                    break;
            }
        }
    }

    private SwitchListener switchListener;

    public void setSwitchListener(SwitchListener switchListener) {
        this.switchListener = switchListener;
    }

    public int getSwitchState() {
        return mState;
    }

    public interface SwitchListener {
        boolean onSwitch(int state);
    }
}
