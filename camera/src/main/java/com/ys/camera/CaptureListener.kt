package com.hbzhou.open.flowcamera


/**
 * author : liutiantian
 * e-mail : Tareafengye@163.com
 * version: 1.0
 */
interface CaptureListener {

    fun takePictures()

    fun recordShort(time: Long)

    fun recordStart()

    fun recordEnd(time: Long)

    fun recordZoom(zoom: Float)

    fun recordError()
}