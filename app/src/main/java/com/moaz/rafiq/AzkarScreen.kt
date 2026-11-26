package com.moaz.rafiq

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext

private val AzkarGreen = Color(0xFF064E3B)
private val AzkarGold = Color(0xFFD6B56D)
private val AzkarCream = Color(0xFFF7F4EA)

@Composable
fun AzkarScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    val categories = listOf(
        "الصباح" to RafiqData.morningAdhkar,
        "المساء" to RafiqData.eveningAdhkar,
        "النوم" to RafiqData.sleepAdhkar,
        "بعد الصلاة" to RafiqData.prayerAdhkar
    )

    val preferences = remember {
        context.getSharedPreferences(
            "rafiq_azkar_progress",
            Context.MODE_PRIVATE
        )
    }

    var categoryIndex by remember {
        mutableIntStateOf(0)
    }

    var dhikrIndex by remember {
        mutableIntStateOf(0)
    }

    var count by remember {
        mutableIntStateOf(0)
    }

    val selectedCategory = categories[categoryIndex]
    val dhikrList = selectedCategory.second
    val currentDhikr = dhikrList[dhikrIndex]

    val progressKey =
        "progress_${categoryIndex}_${dhikrIndex}"

    LaunchedEffect(categoryIndex, dhikrIndex) {
        count = preferences.getInt(progressKey, 0)
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "الأذكار",
                color = AzkarGold,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "اذكر الله واطمئن قلبك",
                color = AzkarCream,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "اختر قسم الأذكار",
                color = AzkarGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            categories.chunked(2).forEachIndexed { rowIndex, rowItems ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEachIndexed { itemIndex, item ->

                        val actualIndex = rowIndex * 2 + itemIndex

                        FilterChip(
                            selected = categoryIndex == actualIndex,
                            onClick = {
                                categoryIndex = actualIndex
                                dhikrIndex = 0
                            },
                            label = {
                                Text(item.first)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AzkarCream
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "ذكر ${dhikrIndex + 1} من ${dhikrList.size}",
                        color = AzkarGreen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Text(
                        text = currentDhikr.text,
                        color = AzkarGreen,
                        fontSize = 25.sp,
                        lineHeight = 42.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "عدد مرات التكرار المطلوبة",
                        color = AzkarGreen,
                        fontSize = 14.sp
                    )

                    Text(
                        text = currentDhikr.repeat.toString(),
                        color = AzkarGreen,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$count / ${currentDhikr.repeat}",
                        color = AzkarGreen,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            if (count < currentDhikr.repeat) {
                                count++

                                preferences.edit()
                                    .putInt(progressKey, count)
                                    .apply()
                            }
                        },
                        enabled = count < currentDhikr.repeat,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzkarGreen
                        )
                    ) {
                        Text(
                            text = if (count < currentDhikr.repeat)
                                "تسبيح"
                            else
                                "اكتمل الذكر ✓",
                            fontSize = 19.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {
                            count = 0

                            preferences.edit()
                                .putInt(progressKey, 0)
                                .apply()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "إعادة العداد",
                            color = AzkarGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            if (dhikrIndex < dhikrList.lastIndex) {
                                dhikrIndex++
                            } else {
                                dhikrIndex = 0
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzkarGold,
                            contentColor = AzkarGreen
                        )
                    ) {
                        Text(
                            text = if (dhikrIndex < dhikrList.lastIndex)
                                "الذكر التالي"
                            else
                                "العودة لأول ذكر"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzkarGold,
                    contentColor = AzkarGreen
                )
            ) {
                Text(
                    text = "العودة للرئيسية",
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
