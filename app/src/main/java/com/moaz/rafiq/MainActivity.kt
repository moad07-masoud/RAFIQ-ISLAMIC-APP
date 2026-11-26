package com.moaz.rafiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Emerald = Color(0xFF064E3B)
private val Gold = Color(0xFFD6B56D)
private val Cream = Color(0xFFF7F4EA)
private val LightGreen = Color(0xFF0B6650)

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
    val page = remember { mutableStateOf("الرئيسية") }
    val count = remember { mutableIntStateOf(0) }

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
                    if (page.value == "الرئيسية") {
                        HomePage(
                            onOpen = { page.value = it }
                        )
                    } else {
                        SectionPage(
                            page = page.value,
                            count = count.intValue,
                            onBack = { page.value = "الرئيسية" },
                            onIncrease = { count.intValue++ },
                            onReset = { count.intValue = 0 }
                        )
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
fun HomePage(onOpen: (String) -> Unit) {
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FeatureCard(
            title = "مواقيت الصلاة",
            subtitle = "الصلوات الخمس",
            modifier = Modifier.weight(1f)
        ) { onOpen("مواقيت الصلاة") }

        FeatureCard(
            title = "القبلة",
            subtitle = "اتجاه الكعبة",
            modifier = Modifier.weight(1f)
        ) { onOpen("القبلة") }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FeatureCard(
            title = "القرآن الكريم",
            subtitle = "السور والآيات",
            modifier = Modifier.weight(1f)
        ) { onOpen("القرآن الكريم") }

        FeatureCard(
            title = "الأذكار",
            subtitle = "أذكار المسلم",
            modifier = Modifier.weight(1f)
        ) { onOpen("الأذكار") }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FeatureCard(
            title = "المسبحة",
            subtitle = "عداد التسبيح",
            modifier = Modifier.weight(1f)
        ) { onOpen("المسبحة") }

        FeatureCard(
            title = "الإعدادات",
            subtitle = "تخصيص التطبيق",
            modifier = Modifier.weight(1f)
        ) { onOpen("الإعدادات") }
    }
}

@Composable
fun FeatureCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(112.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Emerald,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = LightGreen,
                fontSize = 12.sp,
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
                    SectionMessage(
                        "مواقيت الصلاة",
                        "سيتم إضافة الصلوات الخمس واختيار المدن الليبية وحساب الوقت المتبقي للصلاة القادمة."
                    )
                }

                "القبلة" -> {
                    SectionMessage(
                        "اتجاه القبلة",
                        "سيتم إضافة تحديد اتجاه الكعبة باستخدام موقع الهاتف والبوصلة."
                    )
                }

                "القرآن الكريم" -> {
                    SectionMessage(
                        "القرآن الكريم",
                        "سيتم إضافة السور والآيات والقراءة والاستماع وحفظ آخر موضع وصلت إليه."
                    )
                }

                "الأذكار" -> {
                    SectionMessage(
                        "الأذكار",
                        "سيتم إضافة أذكار الصباح والمساء وأذكار ما بعد الصلاة."
                    )
                }

                "الإعدادات" -> {
                    SectionMessage(
                        "الإعدادات",
                        "سيتم إضافة اختيار المدينة وطريقة حساب المواقيت وإعدادات الأذان والتنبيهات والمظهر."
                    )
                }

                else -> {
                    SectionMessage(
                        page,
                        "هذا القسم قيد التطوير."
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

@Composable
fun SectionMessage(title: String, message: String) {
    Text(
        text = title,
        color = Emerald,
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = message,
        color = Emerald,
        fontSize = 15.sp,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "هذا القسم لم تكتمل برمجته بعد.",
        color = LightGreen,
        fontSize = 13.sp
    )
}
