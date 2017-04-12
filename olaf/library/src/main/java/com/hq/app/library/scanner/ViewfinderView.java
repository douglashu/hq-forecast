/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hq.app.library.scanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.hq.app.library.R;
import com.google.zxing.ResultPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class ViewfinderView extends View {
    protected static final String TAG = ViewfinderView.class.getSimpleName();

    protected static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    protected static final long ANIMATION_DELAY = 10L;
    protected static final int CURRENT_POINT_OPACITY = 0xA0;
    protected static final int MAX_RESULT_POINTS = 20;
    protected static final int POINT_SIZE = 6;

    protected final Paint paint;
    protected Bitmap resultBitmap;
    protected final int maskColor;
    protected final int resultColor;
    protected final int laserColor;
    protected final int resultPointColor;
    protected int scannerAlpha;
    protected List<ResultPoint> possibleResultPoints;
    protected List<ResultPoint> lastPossibleResultPoints;
    protected CameraPreview cameraPreview;

    // Cache the framingRect and previewFramingRect, so that we can still draw it after the preview
    // stopped.
    protected Rect framingRect;
    protected Rect previewFramingRect;
    private int i = 0;// 添加的
    private Rect mRect;// 扫描线填充边界
    private GradientDrawable mDrawable;// 采用渐变图作为扫描线
    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Resources resources = getResources();

        // Get setted attributes on view
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.zxing_finder);

        this.maskColor = attributes.getColor(R.styleable.zxing_finder_zxing_viewfinder_mask,
                resources.getColor(R.color.color_dark_676769));
        this.resultColor = attributes.getColor(R.styleable.zxing_finder_zxing_result_view,
                resources.getColor(R.color.color_dark_676769));
        this.laserColor = attributes.getColor(R.styleable.zxing_finder_zxing_viewfinder_laser,
                resources.getColor(R.color.color_blue_00A0EA));
        this.resultPointColor = attributes.getColor(R.styleable.zxing_finder_zxing_possible_result_points,
                resources.getColor(R.color.zxing_possible_result_points));

        attributes.recycle();
        // GradientDrawable、lineDrawable
        mRect = new Rect();
        int left = getResources().getColor(R.color.color_blue_1196D3);
        int center = getResources().getColor(R.color.color_blue_0C99DB);
        int right = getResources().getColor(R.color.color_blue_1196D3);
        mDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, new int[] { left,
                left, center, right, right });
        scannerAlpha = 0;
        possibleResultPoints = new ArrayList<>(5);
        lastPossibleResultPoints = null;
    }

    public void setCameraPreview(CameraPreview view) {
        this.cameraPreview = view;
        view.addStateListener(new CameraPreview.StateListener() {
            @Override
            public void previewSized() {
                refreshSizes();
                invalidate();
            }

            @Override
            public void previewStarted() {

            }

            @Override
            public void previewStopped() {

            }

            @Override
            public void cameraError(Exception error) {

            }
        });
    }

    protected void refreshSizes() {
        if(cameraPreview == null) {
            return;
        }
        Rect framingRect = cameraPreview.getFramingRect();
        Rect previewFramingRect = cameraPreview.getPreviewFramingRect();
        if(framingRect != null && previewFramingRect != null) {
            this.framingRect = framingRect;
            this.previewFramingRect = previewFramingRect;
        }
    }


    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(255);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {

//            // Draw a red "laser scanner" line through the middle to show decoding is active
//            paint.setColor(laserColor);
//            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//            int middle = frame.height() / 2 + frame.top;
//            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
            // 将扫描线修改为上下走的线
            if ((i += 5) < frame.bottom - frame.top) {
				/* 以下为用渐变线条作为扫描线 */
                // 渐变图为矩形
                 mDrawable.setShape(GradientDrawable.RECTANGLE);
                // 渐变图为线型
                 mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                // 线型矩形的四个圆角半径
                 mDrawable
                 .setCornerRadii(new float[] { 8, 8, 8, 8, 8, 8, 8, 8 });
                // 位置边界
                 mRect.set(frame.left + 10, frame.top + i, frame.right - 10,
                 frame.top + 5 + i);
                // 设置渐变图填充边界
                mDrawable.setBounds(mRect);
                // 画出渐变线条
                mDrawable.draw(canvas);

                // 刷新
                //invalidate();
            } else {
                i = 0;
            }
            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }
            paint.setColor(getResources().getColor(R.color.color_gray_A3A3A3));
            canvas.drawRect(frame.left,frame.top-2,frame.right,frame.top+2,paint);
            canvas.drawRect(frame.left,frame.bottom-2,frame.right,frame.bottom+2,paint);
            canvas.drawRect(frame.left-2,frame.top,frame.left+2,frame.bottom,paint);
            canvas.drawRect(frame.right-2,frame.top,frame.right+2,frame.bottom,paint);
            //画出四个角
            paint.setColor(getResources().getColor(R.color.color_blue_00A0EA));
            //左上角
            canvas.drawRect(frame.left-5,frame.top-5, frame.left + 25,frame.top + 5, paint);
            canvas.drawRect(frame.left-5,frame.top, frame.left + 5,frame.top + 25, paint);
            //右上角
            canvas.drawRect(frame.right - 25,frame.top-5, frame.right+5,frame.top + 5, paint);
            canvas.drawRect(frame.right - 5,frame.top, frame.right+5,frame.top + 25, paint);
            //左下角
            canvas.drawRect(frame.left-5,frame.bottom - 5,frame.left + 25,frame.bottom+5,paint);

            canvas.drawRect(frame.left-5,frame.bottom - 25,frame.left + 5,frame.bottom, paint);
            //右下角
            canvas.drawRect(frame.right - 25,frame.bottom - 5,frame.right+5,frame.bottom+5, paint);

            canvas.drawRect(frame.right - 5,frame.bottom - 25,frame.right+5,frame.bottom, paint);
            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live scanning display.
     *
     * @param result An image of the result.
     */
    public void drawResultBitmap(Bitmap result) {
        resultBitmap = result;
        invalidate();
    }

    /**
     * Only call from the UI thread.
     *
     * @param point a point to draw, relative to the preview frame
     */
    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        points.add(point);
        int size = points.size();
        if (size > MAX_RESULT_POINTS) {
            // trim it
            points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
        }
    }

}
