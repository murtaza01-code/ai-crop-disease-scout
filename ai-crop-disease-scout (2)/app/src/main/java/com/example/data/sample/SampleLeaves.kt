package com.example.data.sample

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.example.data.model.CropType

data class SampleLeafCase(
    val id: String,
    val title: String,
    val cropType: CropType,
    val description: String,
    val isDiseased: Boolean
)

object SampleLeaves {
    val sampleCases = listOf(
        SampleLeafCase(
            id = "cotton_curled",
            title = "Cotton - Upward Leaf Curl",
            cropType = CropType.COTTON,
            description = "Showing curled leaf margin and thick veins",
            isDiseased = true
        ),
        SampleLeafCase(
            id = "cotton_healthy",
            title = "Cotton - Healthy Leaf",
            cropType = CropType.COTTON,
            description = "Vibrant green intact lamina without spots",
            isDiseased = false
        ),
        SampleLeafCase(
            id = "wheat_stripe_rust",
            title = "Wheat - Stripe Rust",
            cropType = CropType.WHEAT,
            description = "Yellow-orange powdery linear spore pustules",
            isDiseased = true
        ),
        SampleLeafCase(
            id = "tomato_early_blight",
            title = "Tomato - Early Blight",
            cropType = CropType.TOMATO,
            description = "Concentric rings and chlorotic margins",
            isDiseased = true
        ),
        SampleLeafCase(
            id = "rice_blast",
            title = "Rice - Blast Lesion",
            cropType = CropType.RICE,
            description = "Eye-shaped spindle lesion with gray center",
            isDiseased = true
        ),
        SampleLeafCase(
            id = "mango_anthracnose",
            title = "Mango - Anthracnose Spots",
            cropType = CropType.MANGO,
            description = "Dark necrotic lesions on leaf edge",
            isDiseased = true
        )
    )

    fun generateSampleBitmap(case: SampleLeafCase): Bitmap {
        val width = 480
        val height = 480
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Background neutral soil/backdrop
        canvas.drawColor(Color.rgb(240, 245, 240))

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw leaf body
        val leafPath = Path().apply {
            moveTo(width / 2f, 40f)
            cubicTo(width * 0.9f, height * 0.35f, width * 0.85f, height * 0.75f, width / 2f, height * 0.92f)
            cubicTo(width * 0.15f, height * 0.75f, width * 0.1f, height * 0.35f, width / 2f, 40f)
            close()
        }

        if (case.isDiseased) {
            paint.color = Color.rgb(76, 130, 68) // Stressed olive green
        } else {
            paint.color = Color.rgb(46, 160, 67) // Vibrant healthy green
        }
        paint.style = Paint.Style.FILL
        canvas.drawPath(leafPath, paint)

        // Main stem / vein
        paint.color = Color.rgb(180, 215, 140)
        paint.strokeWidth = 6f
        paint.style = Paint.Style.STROKE
        canvas.drawLine(width / 2f, 40f, width / 2f, height * 0.95f, paint)

        // Side veins
        paint.strokeWidth = 3f
        for (i in 1..6) {
            val y = 70f + i * 50f
            val spread = 70f + i * 15f
            canvas.drawLine(width / 2f, y, width / 2f + spread, y - 25f, paint)
            canvas.drawLine(width / 2f, y, width / 2f - spread, y - 25f, paint)
        }

        // Draw specific disease spots if diseased
        if (case.isDiseased) {
            paint.style = Paint.Style.FILL
            when (case.cropType) {
                CropType.WHEAT -> {
                    // Yellow rust stripes
                    paint.color = Color.rgb(235, 175, 30)
                    for (x in listOf(width / 2f - 40, width / 2f - 15, width / 2f + 25, width / 2f + 60)) {
                        for (y in 120..320 step 25) {
                            canvas.drawCircle(x, y.toFloat(), 6f, paint)
                        }
                    }
                }
                CropType.COTTON -> {
                    // Leaf curl edge deformation and yellowing
                    paint.color = Color.rgb(215, 200, 70)
                    canvas.drawCircle(width * 0.75f, height * 0.45f, 32f, paint)
                    canvas.drawCircle(width * 0.28f, height * 0.55f, 28f, paint)
                    paint.color = Color.rgb(90, 60, 30)
                    canvas.drawCircle(width * 0.73f, height * 0.47f, 12f, paint)
                }
                CropType.TOMATO -> {
                    // Target board early blight concentric rings
                    paint.color = Color.rgb(185, 160, 45) // Yellow halo
                    canvas.drawCircle(width * 0.4f, height * 0.45f, 36f, paint)
                    paint.color = Color.rgb(85, 50, 30)
                    canvas.drawCircle(width * 0.4f, height * 0.45f, 22f, paint)
                    paint.color = Color.rgb(45, 25, 15)
                    canvas.drawCircle(width * 0.4f, height * 0.45f, 10f, paint)
                }
                else -> {
                    // General necrotic spots
                    paint.color = Color.rgb(195, 150, 40)
                    canvas.drawCircle(width * 0.65f, height * 0.5f, 24f, paint)
                    paint.color = Color.rgb(70, 40, 20)
                    canvas.drawCircle(width * 0.65f, height * 0.5f, 14f, paint)
                }
            }
        }

        return bitmap
    }
}
