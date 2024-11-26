package jsp.develop.floatingdashing
import android.app.Service.MODE_PRIVATE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val floatingWindowParams = context.getSharedPreferences("FloatingWindow", MODE_PRIVATE)
            val floatingWindowDealerMode = floatingWindowParams.getBoolean("DealerMod", false)
            if (!floatingWindowDealerMode) {
                val i = Intent(context, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)
            }
        }
    }
}