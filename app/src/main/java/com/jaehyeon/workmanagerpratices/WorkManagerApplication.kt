package com.jaehyeon.workmanagerpratices

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/**
 * Created by Jaehyeon on 2022/08/12.
 */
class WorkManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            "base_channel_id",
            "base_channel_name",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

    }
}