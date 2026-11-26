package com.moaz.rafiq

import android.content.Context

data class QuranAyah(
    val surahNumber: Int,
    val ayahNumber: Int,
    val text: String
)

object QuranReader {

    private var cachedAyahs: List<QuranAyah>? = null

    fun load(context: Context): List<QuranAyah> {
        cachedAyahs?.let { return it }

        val ayahs = context.assets
            .open("quran-simple.txt")
            .bufferedReader(Charsets.UTF_8)
            .useLines { lines ->
                lines.mapNotNull { line ->
                    val parts = line.split("|", limit = 3)

                    if (parts.size != 3) {
                        null
                    } else {
                        val surah = parts[0].toIntOrNull()
                        val ayah = parts[1].toIntOrNull()

                        if (surah == null || ayah == null) {
                            null
                        } else {
                            QuranAyah(
                                surahNumber = surah,
                                ayahNumber = ayah,
                                text = parts[2]
                            )
                        }
                    }
                }.toList()
            }

        require(ayahs.isNotEmpty()) {
            "ملف القرآن فارغ أو تنسيقه غير صحيح"
        }

        require(ayahs.all { it.surahNumber in 1..114 }) {
            "يوجد رقم سورة غير صحيح في ملف القرآن"
        }

        cachedAyahs = ayahs
        return ayahs
    }

    fun getSurah(
        context: Context,
        surahNumber: Int
    ): List<QuranAyah> {
        require(surahNumber in 1..114)

        return load(context).filter {
            it.surahNumber == surahNumber
        }
    }

    fun search(
        context: Context,
        query: String
    ): List<QuranAyah> {
        if (query.isBlank()) return emptyList()

        return load(context).filter {
            it.text.contains(query.trim(), ignoreCase = false)
        }
    }
}
