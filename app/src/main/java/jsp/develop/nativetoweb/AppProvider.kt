package jsp.develop.nativetoweb
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Base64
import android.webkit.JavascriptInterface
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

@Serializable
data class AppInfo(
    val appName: String,
    val icon: String,
    val packageName: String
)

class AppProvider(val context: Context): AppLauncher(context) {

    @SuppressLint("QueryPermissionsNeeded")
    @JavascriptInterface
    fun getAppsListJson(): String {
        val appsList = mutableListOf<AppInfo>()
        val packageManager: PackageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(0)

        for (applicationInfo in packages) {
            if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                val appName = packageManager.getApplicationLabel(applicationInfo).toString()
                val packageName = applicationInfo.packageName
                val icon = getIconBase64(applicationInfo)
                appsList.add(AppInfo(appName, icon, packageName))
            }
        }
        return Json.encodeToString(appsList)
    }

    private fun getIconBase64(applicationInfo: ApplicationInfo): String {
        val packageManager: PackageManager = context.packageManager
        val icon: Drawable = packageManager.getApplicationIcon(applicationInfo)
        val bitmap = drawableToBitmap(icon)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}