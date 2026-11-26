package com.moaz.rafiq

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

private val SettingsGreen = Color(0xFF064E3B)
private val SettingsGold = Color(0xFFD6B56D)
private val SettingsCream = Color(0xFFF7F4EA)

private const val PRAYER_CHANNEL_ID = "rafiq_prayer_notifications"

private fun createPrayerNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            PRAYER_CHANNEL_ID,
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

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    val preferences = remember {
        context.getSharedPreferences(
            "rafiq_settings",
            Context.MODE_PRIVATE
        )
    }

    var adhanEnabled by remember {
        mutableStateOf(
            preferences.getBoolean("adhan_enabled", true)
        )
    }

    var notificationsEnabled by remember {
        mutableStateOf(
            preferences.getBoolean("notifications_enabled", false)
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        notificationsEnabled = granted

        preferences.edit()
            .putBoolean("notifications_enabled", granted)
            .apply()

        if (granted) {
            createPrayerNotificationChannel(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "الإعدادات",
            color = SettingsGold,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "تحكم في تفضيلات تطبيق رفيق",
            color = SettingsCream,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = SettingsCream
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "تشغيل الأذان",
                            color = SettingsGreen,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "تفعيل أو إيقاف خيار الأذان",
                            color = SettingsGreen,
                            fontSize = 12.sp
                        )
                    }

                    Switch(
                        checked = adhanEnabled,
                        onCheckedChange = {
                            adhanEnabled = it
                            preferences.edit()
                                .putBoolean("adhan_enabled", it)
                                .apply()
                        }
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 14.dp),
                    color = Color.LightGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "إشعارات الصلاة",
                            color = SettingsGreen,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "السماح للتطبيق بإرسال الإشعارات",
                            color = SettingsGreen,
                            fontSize = 12.sp
                        )
                    }

                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { enabled ->
                            if (!enabled) {
                                notificationsEnabled = false

                                preferences.edit()
                                    .putBoolean(
                                        "notifications_enabled",
                                        false
                                    )
                                    .apply()
                            } else {
                                if (Build.VERSION.SDK_INT >= 33) {
                                    val permissionGranted =
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.POST_NOTIFICATIONS
                                        ) == PackageManager.PERMISSION_GRANTED

                                    if (permissionGranted) {
                                        notificationsEnabled = true

                                        preferences.edit()
                                            .putBoolean(
                                                "notifications_enabled",
                                                true
                                            )
                                            .apply()

                                        createPrayerNotificationChannel(context)
                                    } else {
                                        permissionLauncher.launch(
                                            Manifest.permission.POST_NOTIFICATIONS
                                        )
                                    }
                                } else {
                                    notificationsEnabled = true

                                    preferences.edit()
                                        .putBoolean(
                                            "notifications_enabled",
                                            true
                                        )
                                        .apply()

                                    createPrayerNotificationChannel(context)
                                }
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = SettingsCream
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "معلومات",
                    color = SettingsGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "رفيق | RAFIQ",
                    color = SettingsGreen,
                    fontSize = 16.sp
                )

                Text(
                    text = "الإصدار 1.0",
                    color = SettingsGreen,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = SettingsGold,
                contentColor = SettingsGreen
            )
        ) {
            Text(
                text = "العودة للرئيسية",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
