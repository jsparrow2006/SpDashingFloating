package jsp.develop.nativetoweb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


//apk zip     https://drive.google.com/uc?export=download&id=1Z5PTcfqyvoalbNN-BRCB24McO4SCimbj
//version      https://drive.google.com/uc?export=download&id=1ihgz8ytEaww8MyD3znrg63_t6JlYlz-r

class UpdateManager(private val context: Context, private val JSPromise: JSPromise, private val sendEvent: (event: String, data: Any) -> Unit) {

    private val eventUploadingTag = "LOADING_UPDATE"
    private val eventTag = "UPDATE_MANAGER"

    @JavascriptInterface
    fun getCurrentVersion(): String {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return "{\"versionCode\": \"${packageInfo.versionCode}\", \"versionName\": \"${packageInfo.versionName}\"}"
        } catch (e: PackageManager.NameNotFoundException) {
            return "{\"versionCode\": \"None\", \"versionName\": \"None\"}"
        }
    }

    private fun installUpdate(apkFile: File) {
        Log.d(eventTag, "\"installUpdate (${apkFile})\"")
        val intent = Intent(Intent.ACTION_VIEW)

        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        intent.setDataAndType(FileProvider.getUriForFile(context, "jsp.develop.floatingdashing.fileprovider", apkFile), "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        context.startActivity(intent)
    }

    @JavascriptInterface
    fun asyncUpdateApplication(id: String) {
        Thread {
            var input: InputStream? = null
            var output: FileOutputStream? = null
            val unzipDirectory = File(context.getExternalFilesDir(null), "UpdateFolder")

            try {
                val url = URL("https://drive.google.com/uc?export=download&id=1Z5PTcfqyvoalbNN-BRCB24McO4SCimbj")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(eventTag, "\"Response code (${connection.responseCode})\"")
                    input = connection.inputStream
                    Log.d(eventTag, "\"Response code (${input})\"")
                    val zipFile = File(context.getExternalFilesDir(null), "SPFloatingDashing.apk.zip")
                    output = FileOutputStream(zipFile)
                    val totalSize = connection.contentLength
                    var downloadedSize = 0
                    var prevProgress = 0;

                    val buffer = ByteArray(1024)
                    var byteCount: Int
                    while (input.read(buffer).also { byteCount = it } > 0) {
                        downloadedSize += byteCount
                        val progress = (downloadedSize * 100) / totalSize
                        if (progress > prevProgress) {
                            prevProgress = progress
                            sendEvent(eventUploadingTag, "$progress")
                        }
                        output.write(buffer, 0, byteCount)
                    }

                    Log.d(eventTag, "ZIP $zipFile")
                    unzip(zipFile, unzipDirectory)
                    val apkFile = File(unzipDirectory, "SPFloatingDashing.apk")
                    installUpdate(apkFile)


                    JSPromise.resolve(id, "Обновление загруженно")
                } else {
                    Log.d(eventTag, "\"Ошибка сети [${connection.responseCode}]\"")
                    JSPromise.reject(id, "Ошибка сети [${connection.responseCode}]")
                }
            } catch (e: Exception) {
                Log.d("$eventTag ERROR", e.toString())
                JSPromise.reject(id, "Не удалось загрузить файл обновления [${e}]")
            } finally {
                input?.close()
                output?.close()
            }
        }.start()
    }

    @JavascriptInterface
    fun asyncCheckUpdates(id: String) {
        requestPermissions()
        Thread {
            var input: BufferedReader? = null
            try {
                val url = URL("https://drive.google.com/uc?export=download&id=1ihgz8ytEaww8MyD3znrg63_t6JlYlz-r")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    input = BufferedReader(InputStreamReader(connection.inputStream))
                    val jsonString = StringBuilder()
                    var line: String?

                    while (input.readLine().also { line = it } != null) {
                        jsonString.append(line)
                    }
                    Log.d(eventTag, jsonString.toString())
                    JSPromise.resolve(id, jsonString.toString())
                } else {
                    JSPromise.reject(id, "Ощибка сети [${connection.responseCode}]")
                }
            } catch (e: Exception) {
                JSPromise.reject(id, "Не удалось прочитать данные о доступой версии")
            } finally {
                input?.close()
            }
        }.start()
    }

    private fun unzip(zipFile: File, unzipLocation: File) {
        val buffer = ByteArray(1024)
        val zipInput = ZipInputStream(FileInputStream(zipFile))
        var zipEntry: ZipEntry? = zipInput.nextEntry

        while (zipEntry != null) {
            val newFile = File(unzipLocation, zipEntry.name)
            if (zipEntry.isDirectory) {
                newFile.mkdirs()
            } else {
                newFile.parentFile?.mkdirs()
                val fos = FileOutputStream(newFile)
                var len: Int
                while (zipInput.read(buffer).also { len = it } > 0) {
                    fos.write(buffer, 0, len)
                }
                fos.close()
            }
            zipInput.closeEntry()
            zipEntry = zipInput.nextEntry
        }

        zipInput.close()
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_MEDIA_IMAGES") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf("android.permission.READ_MEDIA_IMAGES"), REQUEST_CODE_READ_MEDIA_IMAGES)
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf("android.permission.WRITE_EXTERNAL_STORAGE"), REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
            }
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf("android.permission.READ_EXTERNAL_STORAGE"), REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_READ_MEDIA_IMAGES = 1001
        private const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1002
    }


}