package jsp.develop.floatingdashing

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.webkit.JavascriptInterface

class FloatingServiceHelper(context: Context) {
    private var floatingService: FloatingService? = null
    private var isBound = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as FloatingService.LocalBinder
            floatingService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    private val intent = Intent(context, FloatingService::class.java)

    fun bindService(activity: Activity) {
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(activity: Activity) {
        if (isBound) {
            activity.unbindService(connection)
            isBound = false
        }
    }

    fun isServiceBound(): Boolean {
        return isBound
    }

    @JavascriptInterface
    fun setWindowWith(width: Int) {
        if (isBound) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                floatingService?.setWindowWith(width)
            }
        }
    }

    @JavascriptInterface
    fun setWindowHeight(height: Int, isOpen: Boolean = false) {
        if (isBound) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                floatingService?.setWindowHeight(height, isOpen)
            }
        }
    }

    @JavascriptInterface
    fun setWindowPositionX(position: Int) {
        if (isBound) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                floatingService?.setWindowPositionX(position)
            }
        }
    }

    @JavascriptInterface
    fun setWindowPositionY(position: Int) {
        if (isBound) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                floatingService?.setWindowPositionY(position)
            }
        }
    }

    @JavascriptInterface
    fun getWindowParams(): String {
        if (isBound) {
            return floatingService?.getWindowParams().toString()
        }

        return ""
    }

    @JavascriptInterface
    fun saveWindowParams() {
        if (isBound) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                floatingService?.saveWindowParams()
            }
        }
    }

    @JavascriptInterface
    fun handleOpenWindow(): Boolean? {
        if (isBound) {
            return floatingService?.handleOpenWindow()
        }

        return false
    }
}