package com.ys.camerademo

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.FullCallback
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest
import com.bumptech.glide.Glide
import java.io.File


class WelcomeActivity : AppCompatActivity() {
    private val TAG = WelcomeActivity::class.java.simpleName
    private val mediaPlayer: MediaPlayer? = null
    private val mBtnStartCamerax: Button by lazy { findViewById<Button>(R.id.btn_start_camerax) }
    private val mIvFiles: ImageView by lazy { findViewById<ImageView>(R.id.iv_files) }
    private val mVideoView: VideoView by lazy { findViewById<VideoView>(R.id.video) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)
        findViewById<View>(R.id.btn_start_camerax).setOnClickListener { initPermission() }

    }

    private fun initPermission() {
        PermissionUtils.permission(
                PermissionConstants.STORAGE,
                PermissionConstants.CAMERA,
                PermissionConstants.MICROPHONE
        )
                .rationale { shouldRequest: ShouldRequest ->
                    shouldRequest.again(
                            true
                    )
                }
                .callback(object : FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        startRequest()
                    }

                    override fun onDenied(
                            permissionsDeniedForever: List<String>,
                            permissionsDenied: List<String>
                    ) {
                        if (permissionsDeniedForever.isNotEmpty()) {
                            PermissionUtils.launchAppDetailsSettings()
                        }
                    }
                }).request()
    }

    fun startRequest() {
        val intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, Config.REQUEST_CODE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Config.REQUEST_CODE_REQUEST && resultCode == Config.REQUEST_CODE_REQUEST_VIDEO) {
            //返回视频
            var videoPath = data!!.getStringExtra("data")
            LogUtils.d(TAG + "===============获取视频总时长:" + VideoPathUtils.getLocalVideoDuration(videoPath) + "===================")
            LogUtils.d(TAG + "===============获取视频转换:" + VideoPathUtils.getLocalVideoMinute(videoPath) + "===================")




        } else if (requestCode == Config.REQUEST_CODE_REQUEST && resultCode == Config.RESULT_CODE_REQUEST_CAMERA) {
            var string = data!!.getStringExtra("data")
            Glide.with(this@WelcomeActivity).load(Uri.fromFile(File(string))).into(mIvFiles);
        }


    }

}