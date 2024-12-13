package jsp.develop.floatingdashing

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import jsp.develop.nativetoweb.AppProvider
import jsp.develop.nativetoweb.LocalServer
import jsp.develop.nativetoweb.nativeToWeb
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.properties.Delegates


@Serializable
data class WindowParams(
    val withDp: Int,
    val heightDp: Int,
    val heightOpenDp: Int,
    val withPx: Int,
    val heightPx: Int,
    val heightOpenPx: Int,
    val posX: Int,
    val posY: Int,
    val isOpen: Boolean,
)

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
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): FloatingService = this@FloatingService
    }

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

    private fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp / density).toInt()
    }

    @JavascriptInterface
    fun setWindowWith(width: Int): String {
        layoutParams.width = width;
        floatingWindowWidth = width;
        windowManager.updateViewLayout(floatingView, layoutParams)
        return getWindowParams()
    }

    @JavascriptInterface
    fun setWindowHeight(height: Int, isOpen: Boolean = false) {
        layoutParams.height = height;
        if (isOpen) {
            floatingWindowOpenHeight = height
        } else {
            floatingWindowHeight = height;
        }
        windowManager.updateViewLayout(floatingView, layoutParams)
    }

    @JavascriptInterface
    fun setWindowPositionX(position: Int) {
        layoutParams.x = position;
        floatingWindowX = position;
        windowManager.updateViewLayout(floatingView, layoutParams)
    }

    @JavascriptInterface
    fun setWindowPositionY(position: Int) {
        layoutParams.y = position;
        floatingWindowY = position;
        windowManager.updateViewLayout(floatingView, layoutParams)
    }

    @JavascriptInterface
    fun getWindowParams(): String {
        val winParams = WindowParams(
            withDp = floatingWindowWidth,
            heightDp = floatingWindowHeight,
            heightOpenDp = floatingWindowOpenHeight,
            withPx = dpToPx(floatingWindowWidth, this),
            heightPx = dpToPx(floatingWindowHeight, this),
            heightOpenPx = dpToPx(floatingWindowOpenHeight, this),
            isOpen = layoutParams.height == floatingWindowOpenHeight,
            posX = floatingWindowX,
            posY = floatingWindowY
        )
        val jsonString = Json.encodeToString(winParams);
        return jsonString
    }

    @JavascriptInterface
    fun saveWindowParams() {
        val editWindowParams = floatingWindowParams.edit()
        editWindowParams.putInt("X", floatingWindowX)
        editWindowParams.putInt("Y", floatingWindowY)
        editWindowParams.putInt("Width", floatingWindowWidth)
        editWindowParams.putInt("Height", floatingWindowHeight)
        editWindowParams.putInt("OpenHeight", floatingWindowOpenHeight)
        editWindowParams.apply()
        Log.d("Save window params", "SAVED")
        Toast.makeText(applicationContext, "Параметры виджета сохранены", Toast.LENGTH_LONG).show()
    }

    @JavascriptInterface
    fun handleOpenWindow(): Boolean {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val targetHeight = if (layoutParams.height == floatingWindowHeight) floatingWindowOpenHeight else floatingWindowHeight
            val animator = ValueAnimator.ofInt(layoutParams.height, targetHeight)
            animator.addUpdateListener { animation ->
                val updatedHeight = animation.animatedValue as Int
                layoutParams.height = updatedHeight
                windowManager.updateViewLayout(floatingView, layoutParams)
            }
            animator.duration = 300
            animator.start()
        }

        return layoutParams.height == floatingWindowHeight
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}