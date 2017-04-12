package com.hq.app.olaf.ui.activity.about;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

import com.hq.app.olaf.R;
import com.hq.app.olaf.net.HttpService;
import com.hq.app.olaf.net.LoadingDialog;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.login.Session;
import com.hq.app.olaf.util.RequestFailedHandler;
import com.hq.app.olaf.util.ScreenUtils;
import com.hq.component.annotation.Layout;
import com.hq.component.date.DateTime;
import com.hq.component.network.KLog;
import com.hq.component.network.service.NetTools;
import com.hq.component.network.service.TaskResult;
import com.hq.component.network.service.impl.HttpTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimeZone;


import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.activity_shoukuan_ma)
public class ShoukuanMaActivity extends HqPayActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.img)
    ImageView imageView;

    private  static  String imgUrl;
    private  Bitmap bitMap = null;
    private  File file=null;
    private String savePath="HQ/";
    private  String imageName = "";
    protected  final static   String composeUrlKey="composeUrlKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(toolbar);
        setActTitle("门店收款码");
        Bundle bundle =getIntent().getExtras();
        imgUrl=bundle.getString(composeUrlKey);

        int height = ScreenUtils.getScreenHeight(imageView.getContext());
        ViewGroup.LayoutParams para = imageView.getLayoutParams();
        para.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        imageView.setMaxHeight(height);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(para);


        showLoadingDialog("正在加载图片");//显示loading效果
        if(!check()){return;};
        GetImageInputStream() ;

    }

    private boolean check(){
        if(!hasSDCard() ){
            Toast.makeText(this,"没有可用的SD卡",Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();//关闭loading效果
            return false;
        }

        File sdCardPath = Environment.getExternalStorageDirectory();
        File saveDrectory = new File(sdCardPath, savePath);

        Session session = Session.load();
        if(TextUtils.isEmpty(session.getMobile())){
            imageName ="SKM_"+ DateTime.now(TimeZone.getDefault()).format("YYYYMMDD") + ".png";
        }else{
            imageName ="SKM_"+ session.getMobile()+ "_"+DateTime.now(TimeZone.getDefault()).format("YYYYMMDD")  + ".png";
        }
        //已存在图片直接加载
        file=new File(saveDrectory,imageName);
        if(file.exists() ){
            loadImage();
            return  false;
        }
        return true;
    }

    @OnClick(R.id.savePhoto)
    public  void save(){
        saveImageToGallery();
    }

    private   void saveImageToGallery( ){
        try {
            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);

            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent. ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            this .sendBroadcast(intent);
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            KLog.e("保存图片到相册失败",e);

        }
    }

    /**
     * 获取网络图片
     * @return Bitmap 返回位图
     */
    public void GetImageInputStream() {

        final HttpTask download = HttpService.getInstance().download(savePath, imgUrl, imageName);
        NetTools.excute(download, null, new NetTools.CallBack() {
            @Override
            public void onComplete(TaskResult taskResult) {
                if (taskResult.isSuccess()) {
                    file = (File) taskResult.getResult();
                    loadImage();
                } else {
                    dismissLoadingDialog();//关闭loading效果
                    RequestFailedHandler.handleFailed(getRootView(), taskResult);

                }
            }
        });
    }

    private  void loadImage(){
        Glide.with(getApplicationContext()).load(Uri.fromFile( file ))
                .asBitmap()
                .placeholder(R.drawable.icon_app_default).fitCenter()
                //利用图片图填充ImageView设置的大小，如果ImageView的Height是match_parent则图片就会被拉伸填充
                .fitCenter()
                .skipMemoryCache(true).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                imageView.setImageBitmap(resource);

                dismissLoadingDialog();//关闭loading效果
            }
        });
    }

    /**
     * 判断手机是否有SD卡。
     * @return 有SD卡返回true，没有返回false。
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 根据scale生成一张图片
     *
     * @param bitmap
     * @param scale  等比缩放值
     * @return
     */
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

}
