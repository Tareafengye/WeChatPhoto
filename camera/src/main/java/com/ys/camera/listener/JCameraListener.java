package com.ys.camera.listener;

import android.graphics.Bitmap;

/**
 * author : liutiantian
 * e-mail : Tareafengye@163.com
 * version: 1.0
 */
public interface JCameraListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame);

}
