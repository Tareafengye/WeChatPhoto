package com.ys.camera.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

/**
 * author : liutiantian
 * e-mail : Tareafengye@163.com
 * version: 1.0
 */
public interface FlowCameraListener {

    void captureSuccess(@NonNull File file);

    void recordSuccess(@NonNull File file);

    void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause);
}
