package jsp.develop.floatingdashing

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mengbo.mbCan.MBCanEngine
import com.mengbo.mbCan.defines.MBCanDataType
import com.mengbo.mbCan.entity.MBCanDvrStatus
import com.mengbo.mbCan.entity.MBCanVehicleBcmStatus
import com.mengbo.mbCan.entity.MBCanVehicleEngine
import com.mengbo.mbCan.entity.MBCanWpcStatus
import com.mengbo.mbCan.interfaces.IMBVehicleListener
import com.mengbo.mbCan.interfaces.IMbCanVehicleAccStatusCallback
import jsp.develop.nativetoweb.UpdateManager
import jsp.develop.nativetoweb.nativeToWeb

class CanVehicleAccStatusCallback(private val sendEvent: (event: String, data: Any) -> Unit) : IMbCanVehicleAccStatusCallback {
    private val canTag = "CAN_BUS"
    override fun onDoorChange(i: Int, i2: Int) {
        TODO("Not yet implemented")
    }

    override fun onMcuUpdate(i: Int) {
        TODO("Not yet implemented")
    }

    override fun onTotalOdometerChange(d: Double) {
        sendEvent(canTag, "[odo]#${d}")
    }

    override fun onVehicleAccStatusChange(b: Byte, b2: Byte, b3: Byte) {
        TODO("Not yet implemented")
    }

    override fun onVehicleBcmStatusChange(mBCanVehicleBcmStatus: MBCanVehicleBcmStatus?) {
        TODO("Not yet implemented")
    }

    override fun onVehicleDVRStatusChange(mBCanDvrStatus: MBCanDvrStatus?) {
        TODO("Not yet implemented")
    }

    override fun onVehicleEngineStatusChange(mBCanVehicleEngine: MBCanVehicleEngine?) {
        val speed = mBCanVehicleEngine?.getfSpeed()
        val gear = mBCanVehicleEngine?.gear
        val fuelRollingCounter = mBCanVehicleEngine?.fuelRollingCounter
        sendEvent(canTag, "[speed]#${speed}")
        sendEvent(canTag, "[gear]#${gear}")
        sendEvent(canTag, "[fuelRollingCounter]#${fuelRollingCounter}")
    }

    override fun onVehicleGearStatusChange(b: Byte) {
        TODO("Not yet implemented")
    }

    override fun onVehicleSeatStatusChange(i: Int) {
        TODO("Not yet implemented")
    }

    override fun onVehicleSystemModeChange(b: Byte) {
        TODO("Not yet implemented")
    }

    override fun onWpcStatusChange(mBCanWpcStatus: MBCanWpcStatus?) {
        TODO("Not yet implemented")
    }

}

class AppWebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var nativeBridge: nativeToWeb
    private lateinit var serviceHelper: FloatingServiceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_web_view)

        serviceHelper = FloatingServiceHelper(this)
        val canBus = MBCanEngine.getInstance();

        val webAppContainer: LinearLayout = findViewById(R.id.webAppContainer)
        val params = webAppContainer.layoutParams as ViewGroup.MarginLayoutParams
        params.marginStart = if (BuildConfig.IS_DEV) 600 else 0
        webAppContainer.layoutParams = params
        val url = intent.getStringExtra("url")
        if (url != null) {
            Log.d("OPEN_URL", url)
            webView = findViewById(R.id.appWebView)
            nativeBridge = nativeToWeb(webView)

            val vehicleAccStatusCallback = CanVehicleAccStatusCallback(nativeBridge::sendEventToWeb)
            canBus.registACCListener(vehicleAccStatusCallback)

            val updateManager = UpdateManager(this, nativeBridge.JSPromise, nativeBridge::sendEventToWeb)
            nativeBridge.registerModuleInWeb(serviceHelper, "FloatingWindow")
            nativeBridge.registerModuleInWeb(updateManager, "UpdateManager")
            nativeBridge.loadUrl(url)
        } else {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        serviceHelper.bindService(this)
    }

    override fun onStop() {
        super.onStop()
        serviceHelper.unbindService(this)
    }

}