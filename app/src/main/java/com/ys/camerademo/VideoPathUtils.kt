package com.ys.camerademo

import android.media.MediaMetadataRetriever

/**
 * author : liutiantian
 * e-mail : Tareafengye@163.com
 * date   : 2020/4/910:34 AM
 * desc   :
 * version: 1.0
 */
object VideoPathUtils {
    /***
     * @param videoPath 视频文件路径
     * @return 返回视频时长，返回毫秒
     */
    fun getLocalVideoDuration(videoPath: String?): Long {
        val duration: Long
        duration = try {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(videoPath)
            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
        return duration
    }

    /***
     * @param videoPath 文件路径，转换之后分钟秒等
     */
    fun getLocalVideoMinute(videoPath: String?): String {
        val duration: Long
        duration = try {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(videoPath)
            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()
        } catch (e: Exception) {
            e.printStackTrace()
            return "0"
        }
        var time = ""
        val minute = duration / 60000
        val seconds = duration % 60000
        val second = Math.round(seconds.toFloat() / 1000).toLong()
        if (minute < 10) {
            time += "0"
        }
        time += "$minute:"
        if (second < 10) {
            time += "0"
        }
        time += second
        return time
//        return duration
    }


}