package com.jaehyeon.workmanagerpratices

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Created by Jaehyeon on 2022/08/12.
 */
class DownloadWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    private var state = 0
    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val notificationId = Random.nextInt()
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    override suspend fun doWork(): Result {

        val notification = NotificationCompat.Builder(context, "base_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Downloading...")
            .setContentTitle("Download in progress")
            .setProgress(100, state, true)

        notificationManagerCompat.notify(notificationId, notification.build())

        try {
            for (i in 1..100) {
                delay(300L)
                state++
                if (state == 100) {
                    notification.setContentText("Download complete.")
                    notification.setProgress(0,0,false)
                    notificationManagerCompat.notify(notificationId, notification.build())
                }
                else {
                    if (state % 10 == 0) {
                        notification.setProgress(100, state, true)
                        notification.setContentText("$state% complete $state of 100")
                        notificationManagerCompat.notify(notificationId, notification.build())
                    }
                }

            }

            return Result.success()
        } catch (t: Throwable) {
            Log.e(javaClass.simpleName, "doWork: ${t.localizedMessage}", )
            return Result.failure()
        }

    }

}