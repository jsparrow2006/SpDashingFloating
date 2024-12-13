package jsp.develop.nativetoweb

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import jsp.develop.floatingdashing.BuildConfig
import jsp.develop.floatingdashing.FloatingService

class JSPromise(private val webView: WebView) {

    fun resolve(id: String, data: Any) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            webView.evaluateJavascript("window._AndroidSpNative.Promises.resolve('$id', '$data')", null)
        }
    }

    fun reject(id: String, error: Any) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            webView.evaluateJavascript("window._AndroidSpNative.Promises.reject('$id', '$error')", null)
        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
class nativeToWeb(private val webView: WebView) {
    private val webSettings: WebSettings = webView.getSettings()
    val JSPromise: JSPromise = JSPromise(webView)

    @get:JavascriptInterface
    val registeredModules  = mutableListOf<String>();

    init {
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webSettings.loadWithOverviewMode = false
        webSettings.allowFileAccess = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        WebView.setWebContentsDebuggingEnabled(true);
        webView.addJavascriptInterface(this, "_AndroidSpNative")
        webView.setBackgroundColor(0x00000000)

    }

    fun loadUrl(url: String) {
        val baseUrl = BuildConfig.URL ?: "http://localhost:8080"
        webView.loadUrl("${baseUrl}${url}")
    }

    fun sendEventToWeb(event: String, data: Any) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            webView.evaluateJavascript("window._AndroidPubSub.publish('$event', $data)", null)
        }
    }

    @SuppressLint("JavascriptInterface")
    fun registerModuleInWeb(module: Any, name: String) {
        if (name in registeredModules) {
            Log.w("[REGISTER NATIVE MODULES]", "$name module already registered")
        } else {
            Log.i("[REGISTER NATIVE MODULES]", "$name module registered")
            registeredModules.add(name)
            webView.addJavascriptInterface(module, name)
            webView.addJavascriptInterface(this, "_AndroidSpNative")
            webView.evaluateJavascript("window.registeredModules=${registeredModules.toString()}", null)
        }
    }

    @JavascriptInterface
    fun getRegisteredModules(): String {
        return registeredModules.toString()
    }
}
