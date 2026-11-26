package com.moaz.rafiq

import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val QuranGreen = Color(0xFF064E3B)
private val QuranGold = Color(0xFFD6B56D)
private val QuranCream = Color(0xFFF7F4EA)

@Composable
fun QuranScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    val loaded = remember {
        runCatching { QuranReader.load(context) }
    }

    var selectedSurah by rememberSaveable {
        mutableIntStateOf(0)
    }

    var selectedAyah by rememberSaveable {
        mutableIntStateOf(1)
    }

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    val savedPrefs = remember {
        context.getSharedPreferences("rafiq_quran", 0)
    }

    if (selectedSurah == 0) {
        selectedSurah = savedPrefs.getInt("last_surah", 1)
        selectedAyah = savedPrefs.getInt("last_ayah", 1)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(QuranGreen)
            .padding(12.dp)
    ) {

        Text(
            text = "القرآن الكريم",
            color = QuranGold,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            loaded.isFailure -> {
                Text(
                    text = "تعذر فتح المصحف. تأكد من وجود ملف quran-simple.txt داخل assets.",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            else -> {

                val allAyahs = loaded.getOrNull() ?: emptyList()

                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("ابحث عن آية أو كلمة") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = QuranGold,
                        unfocusedLabelColor = QuranCream,
                        cursorColor = QuranGold,
                        focusedBorderColor = QuranGold,
                        unfocusedBorderColor = QuranCream
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (searchText.isNotBlank()) {

                    val results = allAyahs.filter {
                        it.text.contains(searchText.trim())
                    }.take(100)

                    Text(
                        text = "نتائج البحث: ${results.size}",
                        color = QuranGold,
                        fontWeight = FontWeight.Bold
                    )

                    results.forEach { ayah ->

                        val surah = QuranData.surahs.firstOrNull {
                            it.number == ayah.surahNumber
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    selectedSurah = ayah.surahNumber
                                    selectedAyah = ayah.ayahNumber
                                    searchText = ""
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = QuranCream
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "${surah?.name ?: ""} - الآية ${ayah.ayahNumber}",
                                    color = QuranGreen,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = ayah.text,
                                    color = QuranGreen,
                                    fontSize = 19.sp
                                )
                            }
                        }
                    }

                } else if (selectedSurah == 0) {

                    Text(
                        text = "اختر سورة للقراءة",
                        color = QuranCream
                    )

                } else {

                    val surah = QuranData.surahs.firstOrNull {
                        it.number == selectedSurah
                    }

                    val verses = allAyahs.filter {
                        it.surahNumber == selectedSurah
                    }

                    if (surah == null || verses.isEmpty()) {

                        Text(
                            text = "لم يتم العثور على آيات هذه السورة.",
                            color = Color.White
                        )

                    } else {

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = QuranCream
                            ),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = surah.name,
                                    color = QuranGreen,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "${surah.revelation} - ${surah.ayahCount} آية",
                                    color = QuranGreen
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                if (selectedSurah != 1 && selectedSurah != 9) {
                                    Text(
                                        text = "بِسْمِ اللَّهِ الرَّحْمَـٰنِ الرَّحِيمِ",
                                        color = QuranGreen,
                                        fontSize = 21.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                verses.forEach { ayah ->

                                    val isSelected =
                                        ayah.ayahNumber == selectedAyah

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                if (isSelected)
                                                    Color(0xFFE8DFC5)
                                                else
                                                    Color.Transparent,
                                                RoundedCornerShape(10.dp)
                                            )
                                            .clickable {
                                                selectedAyah = ayah.ayahNumber
                                                savedPrefs.edit()
                                                    .putInt("last_surah", selectedSurah)
                                                    .putInt("last_ayah", selectedAyah)
                                                    .apply()
                                            }
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = "${ayah.text} ﴿${ayah.ayahNumber}﴾",
                                            color = QuranGreen,
                                            fontSize = 21.sp,
                                            lineHeight = 38.sp,
                                            textAlign = TextAlign.Right,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    HorizontalDivider(
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                savedPrefs.edit()
                                    .putInt("last_surah", selectedSurah)
                                    .putInt("last_ayah", selectedAyah)
                                    .apply()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = QuranGold,
                                contentColor = QuranGreen
                            )
                        ) {
                            Text("حفظ موضع القراءة")
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    if (selectedSurah > 1) {
                                        selectedSurah--
                                        selectedAyah = 1
                                    }
                                },
                                enabled = selectedSurah > 1
                            ) {
                                Text("السورة السابقة")
                            }

                            Button(
                                onClick = {
                                    if (selectedSurah < 114) {
                                        selectedSurah++
                                        selectedAyah = 1
                                    }
                                },
                                enabled = selectedSurah < 114
                            ) {
                                Text("السورة التالية")
                            }
                        }

                        TextButton(
                            onClick = { selectedSurah = 0 }
                        ) {
                            Text(
                                text = "العودة إلى قائمة السور",
                                color = QuranGold
                            )
                        }
                    }
                }

                if (searchText.isBlank() && selectedSurah == 0) {

                    QuranData.surahs.forEach { surah ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    selectedSurah = surah.number
                                    selectedAyah = 1
                                },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = QuranCream
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(13.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${surah.number}. ${surah.name}",
                                    color = QuranGreen,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "${surah.ayahCount} آية",
                                    color = QuranGreen,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = QuranGold
            )
        ) {
            Text("العودة للرئيسية")
        }
    }
}
