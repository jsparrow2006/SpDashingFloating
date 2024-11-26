package jsp.develop.floatingdashing

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.core.content.ContentProviderCompat.requireContext
import jsp.develop.nativetoweb.AppProvider
import jsp.develop.nativetoweb.LocalServer
import jsp.develop.nativetoweb.nativeToWeb
import kotlin.properties.Delegates


class FloatingService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private lateinit var nativeBridge: nativeToWeb
    private lateinit var floatingWindowParams: SharedPreferences
    private var floatingWindowX by Delegates.notNull<Int>()
    private var floatingWindowY: Int = 0
    private var floatingWindowWidth: Int = 0
    private var floatingWindowHeight: Int = 0
    private var floatingWindowOpenHeight: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    private var layoutParams = WindowManager.LayoutParams(
        0,
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )

    private lateinit var webView: WebView

    @SuppressLint("ClickableViewAccessibility", "SetJavaScriptEnabled")
    override fun onCreate() {
        super.onCreate()
        val localServer = LocalServer()
        localServer.start()

        floatingWindowParams = getSharedPreferences("FloatingWindow", MODE_PRIVATE)
        floatingWindowX = floatingWindowParams.getInt("X", 30)
        floatingWindowY = floatingWindowParams.getInt("Y", 16)
        floatingWindowWidth = floatingWindowParams.getInt("Width", 512)
        floatingWindowHeight = floatingWindowParams.getInt("Height", 205)
        floatingWindowOpenHeight = floatingWindowParams.getInt("OpenHeight", 724)

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.width = floatingWindowWidth
        layoutParams.height = floatingWindowHeight
        layoutParams.x = floatingWindowX
        layoutParams.y = floatingWindowY
        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_view, null)
        windowManager.addView(floatingView, layoutParams)

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
        nativeBridge.registerModuleInWeb(this, "FloatingWindow")
        nativeBridge.registerModuleInWeb(AppProvider(this), "AppProvider")
        nativeBridge.loadUrl("/floating")
    }

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

            if (layoutParams.height == floatingWindowHeight) {
                targetHeight = floatingWindowOpenHeight
                nativeBridge.sendEventToWeb("floatingWindow:isOpen", true)
            } else {
                targetHeight = floatingWindowHeight
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