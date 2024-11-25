package jsp.develop.floatingdashing

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import jsp.develop.nativetoweb.nativeToWeb

class AppWebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var nativeBridge: nativeToWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_web_view)

        val url = intent.getStringExtra("url")
        if (url != null) {
            Log.d("OPEN_URL", url)
        }

        webView = findViewById(R.id.appWebView)
        nativeBridge = nativeToWeb(webView)
        nativeBridge.loadUrl("http://10.0.2.2:8080${url}")
    }
}