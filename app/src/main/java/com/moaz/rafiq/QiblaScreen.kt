package com.moaz.rafiq

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.GeomagneticField
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.DisposableEffect
import java.util.Date
import kotlin.math.roundToInt

private val QiblaGreen = Color(0xFF064E3B)
private val QiblaGold = Color(0xFFD6B56D)
private val QiblaCream = Color(0xFFF7F4EA)

private const val TRIPOLI_LAT = 32.8872
private const val TRIPOLI_LON = 13.1913

private const val KAABA_LAT = 21.4225
private const val KAABA_LON = 39.8262

private fun calculateQiblaBearing(): Float {
    val lat1 = Math.toRadians(TRIPOLI_LAT)
    val lat2 = Math.toRadians(KAABA_LAT)
    val deltaLon = Math.toRadians(KAABA_LON - TRIPOLI_LON)

    val y = kotlin.math.sin(deltaLon) * kotlin.math.cos(lat2)

    val x = kotlin.math.cos(lat1) * kotlin.math.sin(lat2) -
            kotlin.math.sin(lat1) * kotlin.math.cos(lat2) *
            kotlin.math.cos(deltaLon)

    return ((Math.toDegrees(kotlin.math.atan2(y, x)) + 360.0) % 360.0).toFloat()
}

@Composable
fun QiblaScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    var heading by remember { mutableFloatStateOf(0f) }
    var sensorAvailable by remember { mutableStateOf(false) }

    val bearing = remember { calculateQiblaBearing() }

    DisposableEffect(context) {

        val sensorManager =
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val rotationSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        sensorAvailable = rotationSensor != null

        val listener = object : SensorEventListener {

            private val rotationMatrix = FloatArray(9)
            private val orientation = FloatArray(3)

            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return

                SensorManager.getRotationMatrixFromVector(
                    rotationMatrix,
                    event.values
                )

                SensorManager.getOrientation(
                    rotationMatrix,
                    orientation
                )

                val magneticHeading =
                    Math.toDegrees(orientation[0].toDouble()).toFloat()

                val field = GeomagneticField(
                    TRIPOLI_LAT.toFloat(),
                    TRIPOLI_LON.toFloat(),
                    0f,
                    Date().time
                )

                val trueHeading =
                    (magneticHeading + field.declination + 360f) % 360f

                heading = trueHeading
            }

            override fun onAccuracyChanged(
                sensor: Sensor?,
                accuracy: Int
            ) {
            }
        }

        if (rotationSensor != null) {
            sensorManager.registerListener(
                listener,
                rotationSensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    val arrowRotation = (bearing - heading + 360f) % 360f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "اتجاه القبلة",
            color = QiblaGold,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "حدد اتجاه الكعبة من موقع طرابلس",
            color = QiblaCream,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = QiblaCream
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "مكة المكرمة 🕋",
                    color = QiblaGreen,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .size(270.dp)
                        .border(
                            width = 4.dp,
                            color = QiblaGold,
                            shape = CircleShape
                        )
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        drawCircle(
                            color = QiblaGreen,
                            style = Stroke(width = 3.dp.toPx())
                        )

                        drawLine(
                            color = QiblaGold,
                            start = Offset(size.width / 2, 12.dp.toPx()),
                            end = Offset(size.width / 2, 35.dp.toPx()),
                            strokeWidth = 4.dp.toPx()
                        )

                        drawLine(
                            color = QiblaGold,
                            start = Offset(size.width / 2, size.height - 12.dp.toPx()),
                            end = Offset(size.width / 2, size.height - 35.dp.toPx()),
                            strokeWidth = 4.dp.toPx()
                        )

                        drawLine(
                            color = QiblaGold,
                            start = Offset(12.dp.toPx(), size.height / 2),
                            end = Offset(35.dp.toPx(), size.height / 2),
                            strokeWidth = 4.dp.toPx()
                        )

                        drawLine(
                            color = QiblaGold,
                            start = Offset(size.width - 12.dp.toPx(), size.height / 2),
                            end = Offset(size.width - 35.dp.toPx(), size.height / 2),
                            strokeWidth = 4.dp.toPx()
                        )
                    }

                    Text(
                        text = "N",
                        color = QiblaGreen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp)
                    )

                    Text(
                        text = "▲",
                        color = Color(0xFFB3261E),
                        fontSize = 70.sp,
                        modifier = Modifier.rotate(arrowRotation)
                    )

                    Box(
                        modifier = Modifier
                            .size(17.dp)
                            .background(QiblaGreen, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "اتجاه القبلة: ${bearing.roundToInt()}° من الشمال",
                    color = QiblaGreen,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (sensorAvailable)
                        "حرّك الهاتف ببطء حتى يستقر السهم باتجاه القبلة"
                    else
                        "مستشعر البوصلة غير متوفر في هذا الهاتف",
                    color = if (sensorAvailable) QiblaGreen else Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = QiblaGold,
                contentColor = QiblaGreen
            )
        ) {
            Text(
                text = "العودة للرئيسية",
                fontSize = 17.sp
            )
        }
    }
}
