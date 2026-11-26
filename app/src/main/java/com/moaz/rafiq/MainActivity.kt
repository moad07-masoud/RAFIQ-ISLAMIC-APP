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

private val NamesGreen = Color(0xFF064E3B)
private val NamesGold = Color(0xFFD6B56D)
private val NamesCream = Color(0xFFF7F4EA)

private val allahNamesList = listOf(
    "الرَّحْمَن", "الرَّحِيم", "الْمَلِك", "الْقُدُّوس",
    "السَّلَام", "الْمُؤْمِن", "الْمُهَيْمِن", "الْعَزِيز",
    "الْجَبَّار", "الْمُتَكَبِّر", "الْخَالِق", "الْبَارِئ",
    "الْمُصَوِّر", "الْغَفَّار", "الْقَهَّار", "الْوَهَّاب",
    "الرَّزَّاق", "الْفَتَّاح", "الْعَلِيم", "الْقَابِض",
    "الْبَاسِط", "الْخَافِض", "الرَّافِع", "الْمُعِزّ",
    "الْمُذِلّ", "السَّمِيع", "الْبَصِير", "الْحَكَم",
    "الْعَدْل", "اللَّطِيف", "الْخَبِير", "الْحَلِيم",
    "الْعَظِيم", "الْغَفُور", "الشَّكُور", "الْعَلِيّ",
    "الْكَبِير", "الْحَفِيظ", "الْمُقِيت", "الْحَسِيب",
    "الْجَلِيل", "الْكَرِيم", "الرَّقِيب", "الْمُجِيب",
    "الْوَاسِع", "الْحَكِيم", "الْوَدُود", "الْمَجِيد",
    "الْبَاعِث", "الشَّهِيد", "الْحَقّ", "الْوَكِيل",
    "الْقَوِيّ", "الْمَتِين", "الْوَلِيّ", "الْحَمِيد",
    "الْمُحْصِي", "الْمُبْدِئ", "الْمُعِيد", "الْمُحْيِي",
    "الْمُمِيت", "الْحَيّ", "الْقَيُّوم", "الْوَاجِد",
    "الْمَاجِد", "الْوَاحِد", "الْأَحَد", "الصَّمَد",
    "الْقَادِر", "الْمُقْتَدِر", "الْمُقَدِّم", "الْمُؤَخِّر",
    "الْأَوَّل", "الْآخِر", "الظَّاهِر", "الْبَاطِن",
    "الْوَالِي", "الْمُتَعَالِي", "الْبَرّ", "التَّوَّاب",
    "الْمُنْتَقِم", "الْعَفُوّ", "الرَّؤُوف", "مَالِكُ الْمُلْك",
    "ذُو الْجَلَالِ وَالْإِكْرَام", "الْمُقْسِط", "الْجَامِع",
    "الْغَنِيّ", "الْمُغْنِي", "الْمَانِع", "الضَّارّ",
    "النَّافِع", "النُّور", "الْهَادِي", "الْبَدِيع",
    "الْبَاقِي", "الْوَارِث", "الرَّشِيد", "الصَّبُور"
)

@Composable
fun AllahNamesScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "أسماء الله الحسنى",
            color = NamesGold,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "ولله الأسماء الحسنى فادعوه بها",
            color = NamesCream,
            fontSize = 17.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "99 اسماً",
            color = NamesGold,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            allahNamesList.forEachIndexed { index, name ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = NamesCream
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            color = NamesGold,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = name,
                            color = NamesGreen,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = NamesGold,
                contentColor = NamesGreen
            )
        ) {
            Text("العودة للرئيسية")
        }
    }
}
