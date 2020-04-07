# WeChatPhoto
仿微信拍照长按录制

[原文地址:https://github.com/xionger0520/flowcamera](https://github.com/xionger0520/flowcamera)

#使用方法，kotlin MainActivity

#回调工具参数
```
object Config {
    const val REQUEST_CODE_REQUEST = 100

    const val RESULT_CODE_REQUEST_CAMERA = 200 //相机

    const val REQUEST_CODE_REQUEST_VIDEO = 300 //视频

}
```

```

class MainActivity : AppCompatActivity() {
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this


        val flowCamera = findViewById<CustomCameraView>(R.id.flowCamera)
        // 绑定生命周期  不绑定无法预览
        flowCamera.setBindToLifecycle(this)
        // 设置白平衡模式
        flowCamera.setWhiteBalance(WhiteBalance.AUTO)
        // 设置只支持单独拍照拍视频还是都支持
        // BUTTON_STATE_ONLY_CAPTURE  BUTTON_STATE_ONLY_RECORDER  BUTTON_STATE_BOTH
        flowCamera.setCaptureMode(BUTTON_STATE_BOTH)
        // 开启HDR
        flowCamera.setHdrEnable(Hdr.ON)
        // 设置最大可拍摄小视频时长 S
        flowCamera.setRecordVideoMaxTime(10)
        // 设置拍照或拍视频回调监听
        flowCamera.setFlowCameraListener(object : FlowCameraListener {
            // 录制完成视频文件返回
            override fun recordSuccess(file: File) {
//                showToast(file.absolutePath)
                val intent = Intent()
                intent.putExtra("data", file.absolutePath)
                setResult(Config.REQUEST_CODE_REQUEST_VIDEO, intent)
                finish()
            }

            // 操作拍照或录视频出错
            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                LogUtil.e(
                        videoCaptureError.toString().plus("----").plus(message).plus("---").plus(
                                cause.toString()
                        )
                )
            }

            // 拍照返回
            override fun captureSuccess(file: File) {
                showToast(file.absolutePath)
                val intent = Intent()
                intent.putExtra("data", file.absolutePath)
                setResult(Config.RESULT_CODE_REQUEST_CAMERA, intent)
                finish()
            }
        })
        //左边按钮点击事件
        flowCamera.setLeftClickListener {
            finish()
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

```
WelcomeActivity.kt

```

class WelcomeActivity : AppCompatActivity() {
    private val TAG = WelcomeActivity::class.java.simpleName
    private val mBtnStartCamerax: Button by lazy { findViewById<Button>(R.id.btn_start_camerax) }
    private val mIvFiles: ImageView by lazy { findViewById<ImageView>(R.id.iv_files) }
    private val  mVideoView:VideoView by lazy {findViewById <VideoView>(R.id.video) }
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
        if (requestCode==Config.REQUEST_CODE_REQUEST && resultCode==Config.REQUEST_CODE_REQUEST_VIDEO){
            //返回视频
            var string=data!!.getStringExtra("data")
            LogUtils.d(TAG+"==================================视频")
            LogUtils.d(TAG+"==============="+string+"===================")
            LogUtils.d(TAG+"==================================")
            Glide.with(this@WelcomeActivity).load(Uri.fromFile(File(string))).into(mIvFiles);

        }else if (requestCode==Config.REQUEST_CODE_REQUEST && resultCode==Config.RESULT_CODE_REQUEST_CAMERA){
            var string=data!!.getStringExtra("data")
            LogUtils.d(TAG+"==================================图片")
            LogUtils.d(TAG+"==============="+string+"===================")
            LogUtils.d(TAG+"==================================")
        Glide.with(this@WelcomeActivity).load(Uri.fromFile(File(string))).into(mIvFiles);
        }


    }

```
#布局文件 activity_main
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:keepScreenOn="true"
    tools:context=".MainActivity">

    <com.ys.camera.CustomCameraView
        android:id="@+id/flowCamera"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:keepScreenOn="true"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

    </com.ys.camera.CustomCameraView>

</LinearLayout>
```
#布局文件Activity_welcome

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:orientation="vertical"
    android:layout_height="match_parent"
    tools:ignore="MissingDefaultResource">
    <Button
        android:id="@+id/btn_start_camerax"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="StartCameraX" />

    <ImageView
        android:id="@+id/iv_images"
        android:layout_width="match_parent"
        android:layout_height="200dp" />

</LinearLayout>

```
