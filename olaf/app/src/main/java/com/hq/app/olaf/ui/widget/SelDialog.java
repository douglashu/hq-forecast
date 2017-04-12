package com.hq.app.olaf.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.hq.component.utils.DensityUtils;
import com.hq.component.utils.TextHelper;
import com.orhanobut.logger.Logger;

import java.util.List;


import com.hq.app.olaf.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huwentao on 16-4-28.
 */
public class SelDialog extends Dialog {
    @Bind(R.id.icon) ImageView icon;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.listView) ListView listView;

    private final View DialogView;
    private SelAdapter selAdapter = null;
    private OnItemClickListener itemClickListener;

    public SelDialog(Context context, SelAdapter selAdapter) {
        super(context);
        DialogView = LayoutInflater.from(getContext()).inflate(R.layout.layout_seldialog, null);
        ButterKnife.bind(this, DialogView);
        this.selAdapter = selAdapter;
        TextHelper.setText(title, selAdapter.getTitle());
        listView.setAdapter(this.selAdapter);
        setTotalHeigh(listView, DensityUtils.dip2px(getContext(), 350));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(DialogView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClickListener.onItemClick(SelDialog.this,position, (SelItem) selAdapter.getItem(position));
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void refresh(List<? extends SelItem> items) {
        if (selAdapter != null) {
            selAdapter.refreshItems(items);
        }
    }

    public void setTitle(String title) {
        TextHelper.setText(this.title, title);
    }

    public interface OnItemClickListener {
        void onItemClick(SelDialog selDialog, int position, SelItem selItem);
    }

    private static void setTotalHeigh(ListView listView, int maxHeigh) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int count = mAdapter.getCount() > 10 ? 10 : mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //mView.measure(0, 0);
            totalHeight += mView.getMeasuredHeight();
            Logger.w("HEIGHT" + i, String.valueOf(totalHeight));
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        params.height = params.height > maxHeigh ? maxHeigh : params.height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
