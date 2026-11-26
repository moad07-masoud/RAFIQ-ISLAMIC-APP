package com.moaz.rafiq

import android.content.Context
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

private val SettingsGreen = Color(0xFF064E3B)
private val SettingsGold = Color(0xFFD6B56D)
private val SettingsCream = Color(0xFFF7F4EA)

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
            preferences.getBoolean("notifications_enabled", true)
        )
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
                            text = "تفعيل أو إيقاف خيار الإشعارات",
                            color = SettingsGreen,
                            fontSize = 12.sp
                        )
                    }

                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = {
                            notificationsEnabled = it
                            preferences.edit()
                                .putBoolean("notifications_enabled", it)
                                .apply()
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
