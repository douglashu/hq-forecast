package com.hq.app.olaf.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.hq.component.utils.TextHelper;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16/9/22.
 */

public class InfoDialog extends Dialog {
    @Bind(R.id.close) ImageView closeView;
    @Bind(R.id.cancel) Button cancel;
    @Bind(R.id.confirm) Button confirm;
    @Bind(R.id.title) TextView titleView;
    @Bind(R.id.content) TextView contentView;
    @Bind(R.id.spacer) View spacer;

    public InfoDialog(Context context, String title, String content) {
        this(context, true, title, content);
    }

    public InfoDialog(Context context, boolean hiddenClose, String title, String content) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.layout_info_dialog);
        ButterKnife.bind(this, this);
        TextHelper.setText(titleView, title);
        TextHelper.setText(contentView, content);
        if (hiddenClose) {
            closeView.setVisibility(View.GONE);
        }
        /*
         * 获取窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        // lp.x = 100; // 新位置X坐标
        // lp.y = 100; // 新位置Y坐标
        // lp.width = 300; // 宽度
        // lp.height = 300; // 高度
        // lp.alpha = 0.7f; // 透明度
        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        // dialogWindow.setAttributes(lp);

        //将对话框的大小按屏幕大小的百分比设置
        WindowManager m = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (metrics.widthPixels * 0.9); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public InfoDialog setConfirm(String text, final InfoDialog.OnClickListener onClickListener) {
        confirm.setVisibility(View.VISIBLE);
        TextHelper.setText(confirm, text);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(InfoDialog.this, v);
                }
            }
        });
        return this;
    }

    public InfoDialog setCancel(String text, final InfoDialog.OnClickListener onClickListener) {
        cancel.setVisibility(View.VISIBLE);
        spacer.setVisibility(View.VISIBLE);
        TextHelper.setText(cancel, text);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(InfoDialog.this, v);
                }
            }
        });
        return this;
    }

    public InfoDialog setClose(final InfoDialog.OnClickListener onClickListener) {
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(InfoDialog.this, view);
                }
            }
        });
        return this;
    }

    public InfoDialog setDialogCancelable(boolean isCancelable) {
        setCancelable(isCancelable);
        return this;
    }

    public InfoDialog setContentTextGravity(int textGravy) {
        contentView.setGravity(textGravy);
        return this;
    }

    public InfoDialog setContentColor(int id){
        contentView.setTextColor(this.getContext().getResources().getColor(id));
        return  this;
    }

    public InfoDialog setTitleColor(int id){
        titleView.setTextColor(this.getContext().getResources().getColor(id));
        return  this;
    }

    public interface OnClickListener {
        void onClick(InfoDialog infoDialog, View button);
    }

}
