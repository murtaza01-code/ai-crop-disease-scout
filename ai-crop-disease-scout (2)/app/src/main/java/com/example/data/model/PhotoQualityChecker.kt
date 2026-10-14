package com.example.data.model

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.abs

enum class PhotoQualityIssue {
    TOO_DARK,
    TOO_BRIGHT,
    BLURRY_OR_LOW_CONTRAST,
    TOO_DISTANT_OR_NO_LEAF,
    ACCEPTABLE
}

data class PhotoQualityAssessment(
    val isAcceptable: Boolean,
    val primaryIssue: PhotoQualityIssue,
    val score: Float, // 0.0 to 1.0
    val meanLuminance: Float, // 0.0 to 255.0
    val contrastScore: Float, // 0.0 to 1.0
    val greenPixelRatio: Float, // 0.0 to 1.0 (leaf coverage proxy)
    val guideTitle: String,
    val guideDetail: String,
    val urduGuide: String,
    val sindhiGuide: String
)

object PhotoQualityChecker {

    fun analyze(bitmap: Bitmap): PhotoQualityAssessment {
        val width = bitmap.width
        val height = bitmap.height

        // Downsample sampling step to keep check fast (<20ms) even on high-res camera outputs
        val stepX = (width / 60).coerceAtLeast(1)
        val stepY = (height / 60).coerceAtLeast(1)

        var totalLum = 0.0
        var totalLumSq = 0.0
        var sampleCount = 0
        var greenCount = 0
        var laplacianContrastSum = 0.0

        val rowSamples = mutableListOf<FloatArray>()
        for (y in 0 until height step stepY) {
            val row = FloatArray((width / stepX) + 1)
            var colIdx = 0
            for (x in 0 until width step stepX) {
                val pixel = bitmap.getPixel(x, y)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                // Perceived luminance (ITU-R BT.601)
                val lum = (0.299f * r + 0.587f * g + 0.114f * b)
                row[colIdx++] = lum
                totalLum += lum
                totalLumSq += (lum * lum)
                sampleCount++

                // Green/foliage pixel check
                if (g > 45 && g > (r * 0.95f) && g > (b * 1.05f)) {
                    greenCount++
                } else if (r > 60 && g > 55 && b < 70) {
                    // Yellow/brown necrotic spots on foliage also count towards leaf coverage
                    greenCount++
                }
            }
            rowSamples.add(row)
        }

        if (sampleCount == 0) {
            return PhotoQualityAssessment(
                isAcceptable = false,
                primaryIssue = PhotoQualityIssue.BLURRY_OR_LOW_CONTRAST,
                score = 0.1f,
                meanLuminance = 0f,
                contrastScore = 0f,
                greenPixelRatio = 0f,
                guideTitle = "Invalid Photo",
                guideDetail = "The image could not be sampled. Please capture again.",
                urduGuide = "تصویر صحیح نہیں ہے، براہ کرم دوبارہ کھینچیں۔",
                sindhiGuide = "تصوير صحيح ناهي، مهرباني ڪري ٻيهر ڪڍو."
            )
        }

        val meanLum = (totalLum / sampleCount).toFloat()
        val varianceLum = ((totalLumSq / sampleCount) - (meanLum * meanLum)).coerceAtLeast(0.0)
        val stdDevLum = Math.sqrt(varianceLum).toFloat()

        // Approximate contrast / edge gradient (Laplacian neighbor difference)
        var edgeDiffCount = 0
        for (i in 0 until rowSamples.size - 1) {
            val r1 = rowSamples[i]
            val r2 = rowSamples[i + 1]
            val minLen = minOf(r1.size, r2.size)
            for (j in 0 until minLen - 1) {
                val dx = abs(r1[j + 1] - r1[j])
                val dy = abs(r2[j] - r1[j])
                laplacianContrastSum += (dx + dy)
                edgeDiffCount++
            }
        }
        val avgGradient = if (edgeDiffCount > 0) (laplacianContrastSum / edgeDiffCount).toFloat() else 0f
        val foliageRatio = greenCount.toFloat() / sampleCount

        // 1. Dark check: mean luminance < 42
        if (meanLum < 42f) {
            return PhotoQualityAssessment(
                isAcceptable = false,
                primaryIssue = PhotoQualityIssue.TOO_DARK,
                score = (meanLum / 42f) * 0.5f,
                meanLuminance = meanLum,
                contrastScore = (avgGradient / 30f).coerceIn(0f, 1f),
                greenPixelRatio = foliageRatio,
                guideTitle = "Photo Too Dark / Shadows",
                guideDetail = "The leaf is in heavy shadow or dark surroundings. Turn on the flash or step into open daylight for accurate disease identification.",
                urduGuide = "تصویر بہت تاریک یا سائے میں ہے۔ براہ کرم روشنی میں آئیں یا فلیش آن کر کے دوبارہ تصویر بنائیں۔",
                sindhiGuide = "تصوير تمام اونداهي يا ڇانوَ ۾ آهي. مهرباني ڪري روشني ۾ اچو يا فليش کولي ٻيهر تصوير ڪڍو."
            )
        }

        // 2. Overexposed check: mean luminance > 220 or stdDev < 15 with high lum
        if (meanLum > 222f) {
            return PhotoQualityAssessment(
                isAcceptable = false,
                primaryIssue = PhotoQualityIssue.TOO_BRIGHT,
                score = 0.4f,
                meanLuminance = meanLum,
                contrastScore = (avgGradient / 30f).coerceIn(0f, 1f),
                greenPixelRatio = foliageRatio,
                guideTitle = "Excessive Glare / Overexposed",
                guideDetail = "Direct sunlight glare is washing out the leaf color and lesions. Shade the leaf with your hand or angle the camera away from sun reflection.",
                urduGuide = "سورج کی تیز چمک پتے کے رنگ اور دھبوں کو چھپا رہی ہے۔ پتے پر ہلکا سایہ کر کے دوبارہ فوٹو لیں۔",
                sindhiGuide = "سج جي تيز روشني پن جي رنگ ۽ چٽن کي لڪائي رهي آهي. پن تي هٿ جي ڇانوَ ڪري ٻيهر فوٽو ڪڍو."
            )
        }

        // 3. Foliage coverage check: leaf too distant or framing unrelated background
        if (foliageRatio < 0.16f) {
            return PhotoQualityAssessment(
                isAcceptable = false,
                primaryIssue = PhotoQualityIssue.TOO_DISTANT_OR_NO_LEAF,
                score = (foliageRatio / 0.20f) * 0.5f,
                meanLuminance = meanLum,
                contrastScore = (avgGradient / 30f).coerceIn(0f, 1f),
                greenPixelRatio = foliageRatio,
                guideTitle = "Leaf Too Distant or Incomplete",
                guideDetail = "The leaf is too small or far away. Move 15-20 cm closer so the leaf fills most of the frame.",
                urduGuide = "پتا بہت دور ہے یا فریم میں پورا نہیں آیا۔ کیمرہ 15 سے 20 سینٹی میٹر قریب لا کر تصویر لیں۔",
                sindhiGuide = "پن تمام پري آهي يا مڪمل فريم ۾ ناهي. ڪيمرا 15 کان 20 سينٽي ميٽر ويجهو آڻي تصوير ڪڍو."
            )
        }

        // 4. Blur / low contrast check: standard deviation & avg gradient both very flat
        if (avgGradient < 4.2f || (stdDevLum < 12f && avgGradient < 6f)) {
            return PhotoQualityAssessment(
                isAcceptable = false,
                primaryIssue = PhotoQualityIssue.BLURRY_OR_LOW_CONTRAST,
                score = 0.45f,
                meanLuminance = meanLum,
                contrastScore = (avgGradient / 30f).coerceIn(0f, 1f),
                greenPixelRatio = foliageRatio,
                guideTitle = "Blurry or Out-of-Focus",
                guideDetail = "Camera motion or focus blur detected. Hold your phone steady and tap the screen on the leaf to refocus before capturing.",
                urduGuide = "تصویر دھندلی ہے یا کیمرہ ہل گیا ہے۔ فون کو مضبوطی سے پکڑیں اور پتے پر فوکس کر کے تصویر بنائیں۔",
                sindhiGuide = "تصوير ڌنڌلي آهي يا ڪيمرا چري ويو آهي. فون کي جهلي پن تي فوڪس ڪري تصوير ڪڍو."
            )
        }

        // Acceptable photo
        val combinedScore = (
            (meanLum / 128f).coerceAtMost(1f) * 0.3f +
            (foliageRatio.coerceAtMost(0.7f) / 0.7f) * 0.4f +
            (avgGradient / 25f).coerceAtMost(1f) * 0.3f
        ).coerceIn(0.6f, 1.0f)

        return PhotoQualityAssessment(
            isAcceptable = true,
            primaryIssue = PhotoQualityIssue.ACCEPTABLE,
            score = combinedScore,
            meanLuminance = meanLum,
            contrastScore = (avgGradient / 30f).coerceIn(0f, 1f),
            greenPixelRatio = foliageRatio,
            guideTitle = "Clear Leaf Photo",
            guideDetail = "Sharp focus, balanced lighting, and clear leaf surface detected.",
            urduGuide = "تصویر کا معیار بہترین ہے، معائنہ کے لیے موزوں ہے۔",
            sindhiGuide = "تصوير جو معيار سٺو آهي، چڪاس لاءِ تيار آهي."
        )
    }
}
