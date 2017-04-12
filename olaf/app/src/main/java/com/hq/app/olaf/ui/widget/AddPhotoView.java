package com.hq.app.olaf.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import com.hq.app.olaf.ui.activity.merchant.PhotoViewActivity;
import com.hq.app.olaf.ui.base.HqPayApplication;
import com.hq.app.olaf.ui.bean.merchant.Picture;
import com.hq.uicomponet.utils.SnackbarTool;

import java.io.File;

import com.hq.app.olaf.R;

/**
 * Created by huwentao on 16/9/21.
 * 拍照、选图
 */

public class AddPhotoView extends LinearLayout implements View.OnClickListener {
    private ImageView photoView;
    private LinearLayout addPhotoView;
    private TakePhotoListener takePhotoListener;
    private Uri photoFileUri;
    private int requestCode;
    private String photoFileUrl;
    private boolean isChooseAble = true;
    private String nonChooseImageUrl = "";
    private String photoName = "";

    public AddPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_import_addphotoview, this);
        photoView = (ImageView) view.findViewById(R.id.photo);
        addPhotoView = (LinearLayout) view.findViewById(R.id.addPhoto);
        addPhotoView.setVisibility(VISIBLE);
        photoView.setVisibility(GONE);
        addPhotoView.setOnClickListener(this);
        photoView.setOnClickListener(this);
    }

    public AddPhotoView(Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
        if (!isChooseAble) {
            HqPayApplication.getAppContext()
                    .saveCache(PhotoViewActivity.PHOTO_URL, nonChooseImageUrl);
            HqPayApplication.getAppContext()
                    .saveCache(PhotoViewActivity.PHOTO_TITLE, photoName);
            Intent intent = new Intent(getContext(), PhotoViewActivity.class);
            getContext().startActivity(intent);
            return;
        }
        new MaterialDialog.Builder(getContext())
                .title("图片上传")
                .items("拍照", "选择照片")
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        dialog.dismiss();
                        if (which == 0) {
                            if (takePhotoListener != null) {
                                takePhotoListener.takePhoto(requestCode, AddPhotoView.this);
                            }
                        } else {
                            if (takePhotoListener != null) {
                                takePhotoListener.choicePhoto(requestCode, AddPhotoView.this);
                            }
                        }
                    }
                }).show();
    }

    public Uri getPhotoFileUri() {
        return photoFileUri;
    }

    public String getPhotoFilePath() {
        return Picture.getRealFilePath(getContext(), photoFileUri);
    }

    public File getPhotoFile() {
        String filePath = getPhotoFilePath();
        if (!TextUtils.isEmpty(filePath)) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public AddPhotoView setPhotoFileUri(Uri photoFileUri) {
        this.photoFileUri = photoFileUri;
        return this;
    }

    public AddPhotoView loadPhotoFileUri(String photoFileUrl) {
        this.photoFileUrl = photoFileUrl;
        if (!TextUtils.isEmpty(photoFileUrl) && photoFileUrl.startsWith("http")) {
            if (photoFileUri != null) {
                String filePath = Picture.getRealFilePath(getContext(), photoFileUri);
                if (new File(filePath).exists()) {
                    showPhoto();
                } else {
                    photoView.setVisibility(VISIBLE);
                    addPhotoView.setVisibility(GONE);
                    photoView.setPadding(0, 0, 0, 0);
                    Glide.with(getContext()).load(photoFileUrl).crossFade().into(photoView);
                }
            } else {
                photoView.setVisibility(VISIBLE);
                addPhotoView.setVisibility(GONE);
                photoView.setPadding(0, 0, 0, 0);
                Glide.with(getContext()).load(photoFileUrl).crossFade().into(photoView);
            }
        }
        return this;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * @param requestCode
     * @param takePhotoListener
     */
    public void setTakePhotoListener(int requestCode, TakePhotoListener takePhotoListener) {
        this.takePhotoListener = takePhotoListener;
        this.requestCode = requestCode;
    }

    /**
     * 加载照片
     */
    public void showPhoto() {
        if (photoFileUri != null) {
            photoView.setVisibility(VISIBLE);
            addPhotoView.setVisibility(GONE);
            photoView.setPadding(0, 0, 0, 0);
            Glide.with(getContext()).load(photoFileUri).crossFade().into(photoView);
        } else {
            SnackbarTool.show(this, "图片加载失败,请重新选择!");
        }
    }

    /**
     * 拍照、选择图片监听
     */
    public interface TakePhotoListener {
        /**
         * 拍照
         */
        void takePhoto(int requestCode, AddPhotoView addPhotoView);

        /**
         * 选择图片
         */
        void choicePhoto(int requestCode, AddPhotoView addPhotoView);
    }

    public boolean isChooseAble() {
        return isChooseAble;
    }

    public void setChooseAble(boolean chooseAble, String nonChooseImageUrl) {
        this.isChooseAble = chooseAble;
        this.nonChooseImageUrl = nonChooseImageUrl;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
