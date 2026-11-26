package com.moaz.rafiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.data.DateComponents
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.PrayerTimes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private val Emerald = Color(0xFF064E3B)
private val Gold = Color(0xFFD6B56D)
private val Cream = Color(0xFFF7F4EA)
private val LightGreen = Color(0xFF0B6650)

data class PrayerItem(
    val name: String,
    val time: Date
)

private fun calculateTripoliPrayers(): List<PrayerItem> {
    val zone = TimeZone.getTimeZone("Africa/Tripoli")
    val calendar = Calendar.getInstance(zone)

    val date = DateComponents(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val coordinates = Coordinates(32.8872, 13.1913)

    val parameters =
        CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters()

    parameters.madhab = Madhab.SHAFI

    val prayers = PrayerTimes(coordinates, date, parameters)

    return listOf(
        PrayerItem("الفجر", prayers.fajr),
        PrayerItem("الشروق", prayers.sunrise),
        PrayerItem("الظهر", prayers.dhuhr),
        PrayerItem("العصر", prayers.asr),
        PrayerItem("المغرب", prayers.maghrib),
        PrayerItem("العشاء", prayers.isha)
    )
}

private fun formatPrayerTime(date: Date): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.US)
    formatter.timeZone = TimeZone.getTimeZone("Africa/Tripoli")
    return formatter.format(date)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RafiqHome()
        }
    }
}

@Composable
fun RafiqHome() {
    var page by rememberSaveable { mutableStateOf("الرئيسية") }
    var count by rememberSaveable { mutableIntStateOf(0) }

    val prayers = remember { calculateTripoliPrayers() }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Emerald
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "رفيق",
                    color = Cream,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "RAFIQ",
                    color = Gold,
                    fontSize = 14.sp,
                    letterSpacing = 5.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    when (page) {
                        "الرئيسية" -> {
                            HomePage(
                                prayers = prayers,
                                onOpen = { page = it }
                            )
                        }

                        "القرآن الكريم" -> {
                            QuranScreen(
                                onBack = { page = "الرئيسية" }
                            )
                        }

                        "الأذكار" -> {
                            AzkarScreen(
                                onBack = { page = "الرئيسية" }
                            )
                        }

                        "القبلة" -> {
                            QiblaScreen(
                                onBack = { page = "الرئيسية" }
                            )
                        }

                        "التلاوة" -> {
                            QuranAudioScreen(
                                onBack = { page = "الرئيسية" }
                            )
                        }

                        else -> {
                            SectionPage(
                                page = page,
                                count = count,
                                onBack = { page = "الرئيسية" },
                                onIncrease = { count++ },
                                onReset = { count = 0 }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "تطوير: معاذ محمد مسعود",
                    color = Gold,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun HomePage(
    prayers: List<PrayerItem>,
    onOpen: (String) -> Unit
) {
    Text(
        text = "السلام عليكم ورحمة الله",
        color = Cream,
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(6.dp))

    Text(
        text = "رفيقك إلى الصلاة والذكر والقرآن",
        color = Cream,
        fontSize = 14.sp
    )

    Spacer(modifier = Modifier.height(16.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "مواقيت الصلاة - طرابلس",
                color = Emerald,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "حساب رابطة العالم الإسلامي",
                color = LightGreen,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            prayers.forEach { prayer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = prayer.name,
                        color = Emerald,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = formatPrayerTime(prayer.time),
                        color = LightGreen,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(color = Color.LightGray)
            }
        }
    }

    Spacer(modifier = Modifier.height(18.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "آية وتذكير",
                color = Emerald,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "﴿فَاذْكُرُونِي أَذْكُرْكُمْ﴾",
                color = Emerald,
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "اجعل لسانك رطباً بذكر الله",
                color = Emerald,
                fontSize = 14.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(18.dp))

    Text(
        text = "أقسام التطبيق",
        color = Gold,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    val rows = listOf(
        listOf("مواقيت الصلاة", "القبلة"),
        listOf("القرآن الكريم", "التلاوة"),
        listOf("الأذكار", "المسبحة"),
        listOf("الإعدادات", "الأدعية")
    )

    rows.forEach { row ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            row.forEach { item ->
                FeatureCard(
                    title = item,
                    modifier = Modifier.weight(1f)
                ) {
                    onOpen(item)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun FeatureCard(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = Emerald,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SectionPage(
    page: String,
    count: Int,
    onBack: () -> Unit,
    onIncrease: () -> Unit,
    onReset: () -> Unit
) {
    Text(
        text = page,
        color = Gold,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(16.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (page) {
                "المسبحة" -> {
                    Text(
                        text = "سبحان الله",
                        color = Emerald,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = count.toString(),
                        color = Emerald,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onIncrease,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Emerald
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("تسبيح")
                    }

                    OutlinedButton(
                        onClick = onReset,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("تصفير العداد")
                    }
                }

                "مواقيت الصلاة" -> {
                    Text(
                        text = "مواقيت الصلاة تظهر في الصفحة الرئيسية.",
                        color = Emerald,
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    Text(
                        text = "هذا القسم سنكمل برمجته في الخطوات القادمة.",
                        color = Emerald,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(18.dp))

    Button(
        onClick = onBack,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gold,
            contentColor = Emerald
        )
    ) {
        Text("العودة للرئيسية")
    }
}
