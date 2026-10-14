package com.example.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

enum class RecoveryTrajectory {
    IMPROVED,      // Clear reduction in severity or resolved to healthy
    WORSENED,      // Increase in severity or new secondary infection
    STABLE,        // Same condition and similar severity
    DIFFERENT_PATHOLOGY, // Changed disease, cannot compare directly
    INSUFFICIENT_DATA // First scan or inconclusive
}

data class CropScanComparison(
    val previousScanDate: String,
    val latestScanDate: String,
    val daysBetween: Long,
    val previousCrop: String,
    val previousCondition: String,
    val latestCondition: String,
    val previousSeverity: String,
    val latestSeverity: String,
    val trajectory: RecoveryTrajectory,
    val summaryEnglish: String,
    val summaryUrdu: String,
    val summarySindhi: String,
    val evidenceExplanation: String,
    val followUpAdvice: String
)

object RecoveryTracker {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    fun compareScans(
        previousDateMs: Long,
        previousCrop: String,
        previousCondition: String,
        previousIsHealthy: Boolean,
        previousSymptoms: List<String>,
        latestDateMs: Long,
        latestCrop: String,
        latestCondition: String,
        latestIsHealthy: Boolean,
        latestSymptoms: List<String>
    ): CropScanComparison {
        val daysBetween = ((latestDateMs - previousDateMs) / (1000 * 60 * 60 * 24)).coerceAtLeast(0)
        val prevDateStr = dateFormat.format(Date(previousDateMs))
        val lateDateStr = dateFormat.format(Date(latestDateMs))

        // Check crop consistency
        if (!previousCrop.equals(latestCrop, ignoreCase = true)) {
            return CropScanComparison(
                previousScanDate = prevDateStr,
                latestScanDate = lateDateStr,
                daysBetween = daysBetween,
                previousCrop = previousCrop,
                previousCondition = previousCondition,
                latestCondition = latestCondition,
                previousSeverity = if (previousIsHealthy) "Healthy" else "Active",
                latestSeverity = if (latestIsHealthy) "Healthy" else "Active",
                trajectory = RecoveryTrajectory.DIFFERENT_PATHOLOGY,
                summaryEnglish = "Different crops scanned ($previousCrop vs $latestCrop). Cannot evaluate recovery progress across different crop varieties.",
                summaryUrdu = "پچھلا اسکین $previousCrop کا تھا جبکہ نیا $latestCrop کا ہے۔ مختلف فصلوں کے درمیان تقابل ممکن نہیں۔",
                summarySindhi = "پوئين چڪاس $previousCrop جي هئي ۽ نئين $latestCrop جي. مختلف فصلن جي وچ ۾ ڀيٽ نٿي ٿي سگهي.",
                evidenceExplanation = "Comparison requires scans of the same crop variety from the same field block.",
                followUpAdvice = "Filter by crop in History to compare matching field blocks."
            )
        }

        // Case 1: Both Healthy
        if (previousIsHealthy && latestIsHealthy) {
            return CropScanComparison(
                previousScanDate = prevDateStr,
                latestScanDate = lateDateStr,
                daysBetween = daysBetween,
                previousCrop = previousCrop,
                previousCondition = previousCondition,
                latestCondition = latestCondition,
                previousSeverity = "Healthy (0%)",
                latestSeverity = "Healthy (0%)",
                trajectory = RecoveryTrajectory.STABLE,
                summaryEnglish = "Crop remains consistently healthy across both scans over $daysBetween days.",
                summaryUrdu = "فصل $daysBetween دنوں کے دوران دونوں معائنوں میں مسلسل صحت مند پائی گئی ہے۔",
                summarySindhi = "فصل $daysBetween ڏينهن دوران ٻنهي چڪاسن ۾ مسلسل صحتمند رهي آهي.",
                evidenceExplanation = "Zero pathological foliar symptoms recorded in both baseline ($prevDateStr) and follow-up ($lateDateStr).",
                followUpAdvice = "Continue standard agronomic monitoring and balanced irrigation."
            )
        }

        // Case 2: Healthy -> Diseased (New Infection)
        if (previousIsHealthy && !latestIsHealthy) {
            return CropScanComparison(
                previousScanDate = prevDateStr,
                latestScanDate = lateDateStr,
                daysBetween = daysBetween,
                previousCrop = previousCrop,
                previousCondition = previousCondition,
                latestCondition = latestCondition,
                previousSeverity = "Healthy (0%)",
                latestSeverity = "Active Infection (${latestSymptoms.size} symptoms)",
                trajectory = RecoveryTrajectory.WORSENED,
                summaryEnglish = "New infection detected: crop was healthy on $prevDateStr but now displays symptoms of $latestCondition.",
                summaryUrdu = "نئی بیماری کی آمد: پودا $prevDateStr کو تندرست تھا لیکن اب اس میں $latestCondition کی علامات ظاہر ہوئی ہیں۔",
                summarySindhi = "نئين بيماري داخل: فصل $prevDateStr تي صحتمند هو، هاڻي $latestCondition جون علامتون ظاهر ٿيون آهن.",
                evidenceExplanation = "Onset of ${latestSymptoms.take(2).joinToString(", ")} observed after $daysBetween days.",
                followUpAdvice = "Isolate affected field rows immediately and implement recommended cultural sanitation."
            )
        }

        // Case 3: Diseased -> Healthy (Recovery)
        if (!previousIsHealthy && latestIsHealthy) {
            return CropScanComparison(
                previousScanDate = prevDateStr,
                latestScanDate = lateDateStr,
                daysBetween = daysBetween,
                previousCrop = previousCrop,
                previousCondition = previousCondition,
                latestCondition = latestCondition,
                previousSeverity = "Infected ($previousCondition)",
                latestSeverity = "Healthy (No visible foliar lesions)",
                trajectory = RecoveryTrajectory.IMPROVED,
                summaryEnglish = "Marked recovery verified: $previousCondition symptoms cleared over $daysBetween days.",
                summaryUrdu = "نمایاں بہتری و صحت یابی: $daysBetween دنوں میں $previousCondition کی علامات ختم ہو چکی ہیں۔",
                summarySindhi = "واضح سڌارو ۽ صحتيابي: $daysBetween ڏينهن اندر $previousCondition جون علامتون ختم ٿي ويون آهن.",
                evidenceExplanation = "Leaf foliage shows absence of necrosis and active chlorosis present in previous scan.",
                followUpAdvice = "Maintain preventive biosecurity to avoid secondary resurgence."
            )
        }

        // Case 4: Both Diseased
        // Check if conditions match
        val sameCondition = previousCondition.equals(latestCondition, ignoreCase = true) ||
                previousCondition.contains(latestCondition, ignoreCase = true) ||
                latestCondition.contains(previousCondition, ignoreCase = true)

        if (!sameCondition) {
            return CropScanComparison(
                previousScanDate = prevDateStr,
                latestScanDate = lateDateStr,
                daysBetween = daysBetween,
                previousCrop = previousCrop,
                previousCondition = previousCondition,
                latestCondition = latestCondition,
                previousSeverity = "Condition A",
                latestSeverity = "Condition B",
                trajectory = RecoveryTrajectory.DIFFERENT_PATHOLOGY,
                summaryEnglish = "Different diagnoses recorded: $previousCondition on $prevDateStr vs $latestCondition on $lateDateStr.",
                summaryUrdu = "مختلف تشخیص: $prevDateStr کو $previousCondition اور $lateDateStr کو $latestCondition ریکارڈ ہوئی ہے۔",
                summarySindhi = "مختلف تشخيص: $prevDateStr تي $previousCondition ۽ $lateDateStr تي $latestCondition رڪارڊ ٿي آهي.",
                evidenceExplanation = "Possible secondary pathogen complex or evolving disease phase.",
                followUpAdvice = "Consult local extension officer for lab leaf tissue culture."
            )
        }

        // Same condition: evaluate symptom changes strictly from evidence
        val prevCount = previousSymptoms.size
        val lateCount = latestSymptoms.size

        return when {
            lateCount < prevCount -> {
                CropScanComparison(
                    previousScanDate = prevDateStr,
                    latestScanDate = lateDateStr,
                    daysBetween = daysBetween,
                    previousCrop = previousCrop,
                    previousCondition = previousCondition,
                    latestCondition = latestCondition,
                    previousSeverity = "$prevCount observed symptom types",
                    latestSeverity = "$lateCount observed symptom types",
                    trajectory = RecoveryTrajectory.IMPROVED,
                    summaryEnglish = "Symptom remission noted for $latestCondition over $daysBetween days.",
                    summaryUrdu = "$latestCondition میں $daysBetween دنوں میں علامات میں کمی دیکھی گئی ہے۔",
                    summarySindhi = "$latestCondition ۾ $daysBetween ڏينهن اندر علامتن ۾ گهٽتائي ڏٺي وئي آهي.",
                    evidenceExplanation = "Foliar symptom footprint narrowed from $prevCount to $lateCount active manifestations.",
                    followUpAdvice = "Continue ongoing cultural management until zero lesions remain."
                )
            }
            lateCount > prevCount -> {
                CropScanComparison(
                    previousScanDate = prevDateStr,
                    latestScanDate = lateDateStr,
                    daysBetween = daysBetween,
                    previousCrop = previousCrop,
                    previousCondition = previousCondition,
                    latestCondition = latestCondition,
                    previousSeverity = "$prevCount observed symptoms",
                    latestSeverity = "$lateCount observed symptoms",
                    trajectory = RecoveryTrajectory.WORSENED,
                    summaryEnglish = "Symptom progression noted for $latestCondition over $daysBetween days.",
                    summaryUrdu = "$latestCondition کی علامات میں $daysBetween دنوں میں اضافہ ہوا ہے۔",
                    summarySindhi = "$latestCondition جي علامتن ۾ $daysBetween ڏينهن اندر واڌ آئي آهي.",
                    evidenceExplanation = "New visual symptoms detected including additional necrotic spots or leaf curl.",
                    followUpAdvice = "Re-evaluate spray schedule, rogue infected plants, and check field drainage."
                )
            }
            else -> {
                CropScanComparison(
                    previousScanDate = prevDateStr,
                    latestScanDate = lateDateStr,
                    daysBetween = daysBetween,
                    previousCrop = previousCrop,
                    previousCondition = previousCondition,
                    latestCondition = latestCondition,
                    previousSeverity = "$prevCount symptoms (Stationary)",
                    latestSeverity = "$lateCount symptoms (Stationary)",
                    trajectory = RecoveryTrajectory.STABLE,
                    summaryEnglish = "Disease status stable: $latestCondition symptom severity unchanged over $daysBetween days.",
                    summaryUrdu = "بیماری کی حالت مستحکم ہے: $daysBetween دنوں میں $latestCondition کی شدت میں کوئی واضح تبدیلی نہیں آئی۔",
                    summarySindhi = "بيماري جي حالت ساڳي آهي: $daysBetween ڏينهن ۾ $latestCondition جي شدت ۾ ڪا تبديلي ناهي آئي.",
                    evidenceExplanation = "Symptom manifestations remain at identical stages without noticeable expansion or remission.",
                    followUpAdvice = "Monitor weather conditions (humidity/dew) closely as damp conditions can accelerate spread."
                )
            }
        }
    }
}
