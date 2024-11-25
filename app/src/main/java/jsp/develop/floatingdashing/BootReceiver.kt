package jsp.develop.floatingdashing
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Запускаем MainActivity при загрузке системы
//            val i = Intent(context, MainActivity::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(i)

            // Запуск сервиса при завершении загрузки устройства
            val serviceIntent = Intent(context, FloatingService::class.java)
            context.startService(serviceIntent)
        }
    }
}