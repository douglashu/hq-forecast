package com.hq.app.olaf.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hq.app.olaf.ui.base.HqPayApplication;

import com.hq.app.olaf.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by liaob on 2016/4/20.
 */
public  class GlideUtils {
    private static int defaultResId= R.mipmap.ic_launcher;
    public static void  loadCircleImage(Context context, String url, ImageView imageView){
        Glide.with(context).load(url)
                .placeholder(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform( new CropCircleTransformation(context))//圆形
                .into(imageView);
    }

    //扁平
    public static  void loadRoundedCornersImage(Context context,String url,ImageView imageView){
        Glide.with(context).load(url)
                .placeholder(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new RoundedCornersTransformation(context,10,10))
                .into(imageView);
    }
    public static  void loadSquareImage(Context context,String url,ImageView imageView){
        Glide.with(context).load(url)
                .placeholder(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform( new CropSquareTransformation(context))//方形
                .into(imageView);
    }
    public static  void loadCropImage(Context context,String url,ImageView imageView){
        Glide.with(context).load(url)
                .placeholder(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform( new CropTransformation(context))
                .into(imageView);
    }


    public static void loadImageView(String url,ImageView imageView)
    {
        loadImageView(HqPayApplication.getAppContext().getApplicationContext(),url,imageView);
    }
    public static void loadImageView(Context context,String url,ImageView imageView){
        loadImageView(context,url,imageView,R.drawable.icon_error);
    }



    public static void loadImageView(Context context,String url,ImageView imageView,int defaultResId){
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(defaultResId).into(imageView);
    }


}
