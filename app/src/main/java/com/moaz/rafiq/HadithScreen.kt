package com.moaz.rafiq

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

private val HadithGreen = Color(0xFF064E3B)
private val HadithGold = Color(0xFFD6B56D)
private val HadithCream = Color(0xFFF7F4EA)

private data class DailyHadith(
    val text: String,
    val source: String
)

private val dailyHadithList = listOf(
    DailyHadith(
        "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى",
        "صحيح البخاري وصحيح مسلم"
    ),
    DailyHadith(
        "مَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الْآخِرِ فَلْيَقُلْ خَيْرًا أَوْ لِيَصْمُتْ",
        "صحيح البخاري وصحيح مسلم"
    ),
    DailyHadith(
        "لَا يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لِأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ",
        "صحيح البخاري وصحيح مسلم"
    ),
    DailyHadith(
        "الدِّينُ النَّصِيحَةُ",
        "صحيح مسلم"
    ),
    DailyHadith(
        "الْكَلِمَةُ الطَّيِّبَةُ صَدَقَةٌ",
        "صحيح البخاري وصحيح مسلم"
    ),
    DailyHadith(
        "يَسِّرُوا وَلَا تُعَسِّرُوا، وَبَشِّرُوا وَلَا تُنَفِّرُوا",
        "صحيح البخاري وصحيح مسلم"
    ),
    DailyHadith(
        "مَنْ سَلَكَ طَرِيقًا يَلْتَمِسُ فِيهِ عِلْمًا، سَهَّلَ اللَّهُ لَهُ بِهِ طَرِيقًا إِلَى الْجَنَّةِ",
        "صحيح مسلم"
    ),
    DailyHadith(
        "إِنَّ اللَّهَ لَا يَنْظُرُ إِلَى صُوَرِكُمْ وَأَمْوَالِكُمْ، وَلَكِنْ يَنْظُرُ إِلَى قُلُوبِكُمْ وَأَعْمَالِكُمْ",
        "صحيح مسلم"
    )
)

@Composable
fun HadithScreen(onBack: () -> Unit) {

    val dayOfYear = Calendar.getInstance()
        .get(Calendar.DAY_OF_YEAR)

    val hadith = dailyHadithList[
        dayOfYear % dailyHadithList.size
    ]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Text(
            text = "الحديث النبوي اليومي",
            color = HadithGold,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = HadithCream
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "حديث اليوم",
                    color = HadithGreen,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = hadith.text,
                    color = HadithGreen,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(color = HadithGold)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "المصدر: ${hadith.source}",
                    color = HadithGreen,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "احرص على العمل بما تعلمته من سنة النبي ﷺ",
                    color = HadithGreen,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = HadithGold,
                contentColor = HadithGreen
            )
        ) {
            Text(
                text = "العودة للرئيسية",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
