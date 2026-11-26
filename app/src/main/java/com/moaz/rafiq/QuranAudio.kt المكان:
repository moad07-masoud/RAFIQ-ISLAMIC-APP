package com.moaz.rafiq

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import java.io.File

@Composable
fun QuranAudioScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    var playingSurah by remember { mutableStateOf("") }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    fun audioUrl(number: Int): String {
        val id = number.toString().padStart(3, '0')
        return "https://cdn.mp3quran.net/audio/maher-muaiqly/r1/$id.mp3"
    }

    fun audioFile(number: Int): File {
        val id = number.toString().padStart(3, '0')
        return File(
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC),
            "surah_$id.mp3"
        )
    }

    fun playSurah(number: Int, name: String) {
        val file = audioFile(number)

        val uri = if (file.exists() && file.length() > 0) {
            Uri.fromFile(file)
        } else {
            Uri.parse(audioUrl(number))
        }

        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()
        player.play()
        playingSurah = name
    }

    fun downloadSurah(number: Int, name: String) {
        val file = audioFile(number)

        if (file.exists() && file.length() > 0) {
            Toast.makeText(
                context,
                "السورة محفوظة بالفعل",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val manager = context.getSystemService(
            Context.DOWNLOAD_SERVICE
        ) as DownloadManager

        val request = DownloadManager.Request(
            Uri.parse(audioUrl(number))
        )
            .setTitle("سورة $name - ماهر المعيقلي")
            .setDescription("جاري تنزيل التلاوة")
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_MUSIC,
                "surah_${number.toString().padStart(3, '0')}.mp3"
            )

        manager.enqueue(request)

        Toast.makeText(
            context,
            "بدأ تنزيل سورة $name",
            Toast.LENGTH_LONG
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "تلاوة القرآن الكريم",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "بصوت الشيخ ماهر المعيقلي",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (playingSurah.isEmpty()) {
                "لم يتم اختيار سورة"
            } else {
                "يتم تشغيل: $playingSurah"
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    player.pause()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("إيقاف مؤقت")
            }

            OutlinedButton(
                onClick = {
                    player.stop()
                    playingSurah = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("إيقاف")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(QuranData.surahs) { surah ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "${surah.number}. سورة ${surah.name}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "عدد الآيات: ${surah.ayahCount}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    playSurah(
                                        surah.number,
                                        surah.name
                                    )
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("استماع")
                            }

                            OutlinedButton(
                                onClick = {
                                    downloadSurah(
                                        surah.number,
                                        surah.name
                                    )
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("تنزيل")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("العودة")
        }
    }
}
