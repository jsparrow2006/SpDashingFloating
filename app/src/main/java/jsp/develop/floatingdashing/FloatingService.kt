package jsp.develop.floatingdashing

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import jsp.develop.nativetoweb.AppProvider
import jsp.develop.nativetoweb.LocalServer
//import jsp.develop.nativetoweb.LocalServer
import jsp.develop.nativetoweb.nativeToWeb


class FloatingService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private lateinit var nativeBridge: nativeToWeb

    @SuppressLint("ClickableViewAccessibility")
    val layoutParams = WindowManager.LayoutParams(
        512,
        205, //215  //720
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )

    private lateinit var webView: WebView

    class WebAppInterface(private val context: Context) {
        @JavascriptInterface
        fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Ваша логика сервиса
        return START_STICKY // Или другой необходимый вам флаг
    }

    @SuppressLint("ClickableViewAccessibility", "SetJavaScriptEnabled")
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val localServer = LocalServer()
        localServer.start()

        // Указываем позицию окна
        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.x = 32 // координата X
        layoutParams.y = 10 // координата Y

        // Создаем View плавающего окна
        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_view, null)
        windowManager.addView(floatingView, layoutParams)

        // Обработка касаний для перетаскивания окна
        floatingView.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_MOVE -> {
//                    layoutParams.x = (event.rawX - v.width / 2).toInt()
//                    layoutParams.y = (event.rawY - v.height / 2).toInt()
//                    windowManager.updateViewLayout(floatingView, layoutParams)
//                }
//            }
            true
        }

        webView = floatingView.findViewById(R.id.floatingWebView)
        nativeBridge = nativeToWeb(webView)
//        nativeBridge.loadUrl("http://10.0.2.2:8080/floating")
//        nativeBridge.loadUrl("http://localhost:8080")
//        nativeBridge.loadUrl("http://localhost:8080/files")
        nativeBridge.registerModuleInWeb(WebAppInterface(this), "Android")
        nativeBridge.registerModuleInWeb(this, "FloatingWindow")
        nativeBridge.registerModuleInWeb(AppProvider(this), "AppProvider")
//        nativeBridge.loadUrl("http://localhost:8080/floating")
        nativeBridge.loadUrl("http://10.0.2.2:8080/floating")
    }

//    if (BuildConfig.DEBUG) {
//        // Код для дебаг-сборки
//        println("Это дебаг-сборка")
//    } else {
//        // Код для релиз-сборки
//        println("Это релиз-сборка")
//    }

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
    }

    @get:JavascriptInterface
    val currentWidth = layoutParams.width
    @get:JavascriptInterface
    val currentHeight = layoutParams.height

    @JavascriptInterface
    fun setWindowSize(width: Int, heigth: Int) {
        layoutParams.width = width;
        layoutParams.height = heigth;
        windowManager.updateViewLayout(floatingView, layoutParams)
    }

    @JavascriptInterface
    fun handleOpenWindow() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val targetHeight: Number

            if (layoutParams.height == 205) {
                targetHeight = 730
                nativeBridge.sendEventToWeb("floatingWindow:isOpen", true)
            } else {
                targetHeight = 205
                nativeBridge.sendEventToWeb("floatingWindow:isOpen", false)
            }
            val animator = ValueAnimator.ofInt(layoutParams.height, targetHeight)
            animator.addUpdateListener { animation ->
                val updatedHeight = animation.animatedValue as Int
                layoutParams.height = updatedHeight
                windowManager.updateViewLayout(floatingView, layoutParams)
            }
            animator.duration = 300
            animator.start()
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}