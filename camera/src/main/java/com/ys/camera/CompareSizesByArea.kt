package com.hbzhou.open.flowcamera

import android.os.Build
import android.util.Size
import java.lang.Long.signum

/**
 * author : liutiantian
 * e-mail : Tareafengye@163.com
 * version: 1.0
 */
class CompareSizesByArea : Comparator<Size> {

    // We cast here to ensure the multiplications won't overflow
    override fun compare(lhs: Size, rhs: Size) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
}