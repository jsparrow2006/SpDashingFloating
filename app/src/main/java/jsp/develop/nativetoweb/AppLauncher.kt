package jsp.develop.nativetoweb
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast

open class AppLauncher(private val context: Context) {

    @JavascriptInterface
    fun launchApp(packageName: String) {
        val packageManager: PackageManager = context.packageManager
        val intent: Intent? = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent?.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Приложение не найдено", Toast.LENGTH_SHORT).show()
        }
    }

    @JavascriptInterface
    fun launchWebActivity(url: String) {
        val intent = Intent().apply {
            setClassName(context, "jsp.develop.floatingdashing.AppWebViewActivity")
            putExtra("url", url)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        context.startActivity(intent)
    }
}