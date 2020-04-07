package com.ys.camera.listener;

/**
 * author : liutiantian
 * e-mail : Tareafengye@163.com
 * version: 1.0
 */

public interface CaptureListener {
    void takePictures();

    void recordShort(long time);

    void recordStart();

    void recordEnd(long time);

    void recordZoom(float zoom);

    void recordError();
}
