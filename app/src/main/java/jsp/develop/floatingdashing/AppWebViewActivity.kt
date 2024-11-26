package jsp.develop.floatingdashing

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import jsp.develop.nativetoweb.nativeToWeb

class AppWebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var nativeBridge: nativeToWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_web_view)
        val webAppContainer: LinearLayout = findViewById(R.id.webAppContainer)
        val params = webAppContainer.layoutParams as ViewGroup.MarginLayoutParams
        params.marginStart = if (BuildConfig.IS_DEV) 600 else 0
        webAppContainer.layoutParams = params
        val url = intent.getStringExtra("url")
        if (url != null) {
            Log.d("OPEN_URL", url)
            webView = findViewById(R.id.appWebView)
            nativeBridge = nativeToWeb(webView)
            nativeBridge.loadUrl(url)
        } else {
            finish()
        }
    }
}