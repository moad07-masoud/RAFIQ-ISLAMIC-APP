package com.moaz.rafiq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RafiqHome() }
    }
}

@Composable
fun RafiqHome() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Emerald
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(26.dp))
                Text(
                    text = "رفيق",
                    color = Cream,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "RAFIQ",
                    color = Gold,
                    fontSize = 14.sp,
                    letterSpacing = 5.sp
                )
                Spacer(Modifier.height(28.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Cream)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("السلام عليكم", color = Emerald, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "رفيقك إلى الصلاة والذكر والقرآن",
                            color = Emerald,
                            fontSize = 15.sp
                        )
                        Spacer(Modifier.height(18.dp))
                        Text("المدينة: طرابلس", color = Emerald, fontSize = 14.sp)
                        Text("مواقيت الصلاة ستُضاف في المرحلة القادمة", color = Emerald, fontSize = 13.sp)
                    }
                }
                Spacer(Modifier.height(18.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0B6650))
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("آية وتذكير", color = Gold, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "﴿فَاذْكُرُونِي أَذْكُرْكُمْ﴾",
                            color = Cream,
                            fontSize = 23.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            "تذكير: اجعل لسانك رطباً بذكر الله",
                            color = Cream,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "تطوير: معاذ محمد مسعود",
                    color = Gold,
                    fontSize = 12.sp
                )
                Text(
                    "نسخة تأسيسية قيد البناء",
                    color = Cream.copy(alpha = 0.7f),
                    fontSize = 11.sp
                )
            }
        }
    }
}
