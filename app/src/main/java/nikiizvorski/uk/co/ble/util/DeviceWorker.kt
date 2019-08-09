package nikiizvorski.uk.co.ble.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import nikiizvorski.uk.co.ble.R
import timber.log.Timber

/**
 *
 * @constructor test the device worker after you have connection
 */
class DeviceWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        val context = applicationContext

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1001", "demo", importance).apply {
                description = "demo channel"
            }

            // Register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, "1001")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return try {
            Timber.d("Will see the notification")
            with(NotificationManagerCompat.from(context)) {
                notify(1001, builder.build())
                Result.success()
            }
        } catch (e: Exception) {
            Timber.d("Testing the workers on Kotlin")
            Result.failure()
        }
    }

}