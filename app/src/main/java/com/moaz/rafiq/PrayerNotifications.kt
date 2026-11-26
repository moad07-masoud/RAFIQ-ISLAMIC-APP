package com.moaz.rafiq

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

private const val CHANNEL_ID = "rafiq_prayer_notifications"
private const val ALARM_REQUEST_CODE = 451
private const val NOTIFICATION_ID = 451

private data class PrayerAlarm(
    val name: String,
    val time: Date
)

object PrayerNotificationScheduler {

    fun scheduleNext(context: Context) {
        val preferences = context.getSharedPreferences(
            "rafiq_settings",
            Context.MODE_PRIVATE
        )

        val enabled = preferences.getBoolean(
            "notifications_enabled",
            false
        )

        if (!enabled) {
            cancel(context)
            return
        }

        if (
            Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val nextPrayer = findNextPrayer() ?: return

        val intent = Intent(
            context,
            PrayerAlarmReceiver::class.java
        ).apply {
            putExtra("prayer_name", nextPrayer.name)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextPrayer.time.time,
            pendingIntent
        )
    }

    fun cancel(context: Context) {
        val intent = Intent(
            context,
            PrayerAlarmReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun findNextPrayer(): PrayerAlarm? {
        val zone = TimeZone.getTimeZone("Africa/Tripoli")
        val now = Date()

        for (dayOffset in 0..1) {
            val calendar = Calendar.getInstance(zone)
            calendar.add(Calendar.DAY_OF_YEAR, dayOffset)

            val date = DateComponents(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            val coordinates = Coordinates(32.8872, 13.1913)

            val parameters =
                CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters()

            parameters.madhab = Madhab.SHAFI

            val prayers = PrayerTimes(
                coordinates,
                date,
                parameters
            )

            val prayerList = listOf(
                PrayerAlarm("الفجر", prayers.fajr),
                PrayerAlarm("الظهر", prayers.dhuhr),
                PrayerAlarm("العصر", prayers.asr),
                PrayerAlarm("المغرب", prayers.maghrib),
                PrayerAlarm("العشاء", prayers.isha)
            )

            val next = prayerList
                .filter { it.time.after(now) }
                .minByOrNull { it.time.time }

            if (next != null) {
                return next
            }
        }

        return null
    }
}

class PrayerAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val preferences = context.getSharedPreferences(
            "rafiq_settings",
            Context.MODE_PRIVATE
        )

        val enabled = preferences.getBoolean(
            "notifications_enabled",
            false
        )

        if (!enabled) return

        if (
            Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        createNotificationChannel(context)

        val prayerName = intent.getStringExtra("prayer_name")
            ?: "الصلاة"

        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("حان وقت صلاة $prayerName")
            .setContentText("حيّ على الصلاة، حيّ على الفلاح")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.notify(NOTIFICATION_ID, notification)

        PrayerNotificationScheduler.scheduleNext(context)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "إشعارات الصلاة",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "تنبيهات مواقيت الصلاة من تطبيق رفيق"
            }

            val manager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }
}

class PrayerBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            PrayerNotificationScheduler.scheduleNext(context)
        }
    }
}
