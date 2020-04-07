package com.ys.camera.view;

import android.graphics.Bitmap;
/**
 * author : liutiantian
 * e-mail : Tareafengye@163.com
 * version: 1.0
 */
public interface CameraView {
    void resetState(int type);

    void confirmState(int type);

    void showPicture(Bitmap bitmap, boolean isVertical);

    void playVideo(Bitmap firstFrame, String url);

    void stopVideo();

    void setTip(String tip);

    void startPreviewCallback();

    boolean handlerFoucs(float x, float y);
}
