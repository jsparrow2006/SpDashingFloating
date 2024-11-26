package jsp.develop.nativetoweb

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import jsp.develop.floatingdashing.BuildConfig


@SuppressLint("SetJavaScriptEnabled")
class nativeToWeb(val webView: WebView) {
    val webSettings: WebSettings = webView.getSettings()

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
        webView.evaluateJavascript("window._AndroidPubSub.publish('$event', $data)", null)
    }

    @SuppressLint("JavascriptInterface")
    fun registerModuleInWeb(module: Any, name: String) {
        if (name in registeredModules) {
            Log.w("[REGISTER NATIVE MODULES]", "$name module already registered")
        } else {
            Log.i("[REGISTER NATIVE MODULES]", "$name module registered")
            webView.addJavascriptInterface(module, name)
            registeredModules.add(name)
        }
    }

    @JavascriptInterface
    fun getRegisteredModules(): String {
        return registeredModules.toString()
    }
}
