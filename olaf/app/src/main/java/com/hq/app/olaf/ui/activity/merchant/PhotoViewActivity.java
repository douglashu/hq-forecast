package com.hq.app.olaf.ui.activity.merchant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hq.component.annotation.Layout;
import com.hq.app.olaf.ui.base.HqPayActivity;


import com.hq.app.olaf.R;
import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by huwentao on 16/9/29.
 */
@Layout(layoutId = R.layout.activity_photo_view)
public class PhotoViewActivity extends HqPayActivity {
    @Bind(R.id.photoView)
    PhotoView photoView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    PhotoViewAttacher mAttacher;

    public final static String PHOTO_URL = "photo_view_image_url";
    public final static String PHOTO_TITLE = "photo_view_image_title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        String photoUrl = (String) getApp().getCacheClear(PHOTO_URL);
        String photoTitle = (String) getApp().getCacheClear(PHOTO_TITLE);
        if (!TextUtils.isEmpty(photoTitle)) {
            setActTitle(photoTitle);
        }
        Glide.with(this).load(photoUrl).crossFade().into(photoView);
        mAttacher = new PhotoViewAttacher(photoView);
        mAttacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }
}
