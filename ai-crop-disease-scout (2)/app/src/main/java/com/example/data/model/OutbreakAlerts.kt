package com.example.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class OutbreakSeverity {
    WATCH,       // Low density / early season monitoring
    MODERATE,    // Scattered field reports
    CRITICAL     // Spreading cluster / epidemic warning
}

enum class VerificationStatus {
    SUSPECTED_AI_FINDING,   // Automated farmer scan report (unconfirmed)
    EXPERT_CONFIRMED        // Verified by local extension agronomist / lab
}

data class OutbreakAlert(
    val id: String,
    val diseaseName: String,
    val cropType: CropType,
    val generalDistrict: String, // Coarse district level ONLY, never precise GPS or farmer address!
    val verificationStatus: VerificationStatus,
    val severity: OutbreakSeverity,
    val reportCount: Int,
    val dateReported: String,
    val preventiveAdvisory: String,
    val sourceCitation: String
)

data class FarmerOutbreakReport(
    val cropType: CropType,
    val suspectedDisease: String,
    val coarseDistrict: String,
    val isOptInSharingConfirmed: Boolean,
    val anonymizedNotes: String
)

object OutbreakAlertRepository {

    private val sampleAlerts = mutableListOf(
        OutbreakAlert(
            id = "alert_clcuv_01",
            diseaseName = "Cotton Leaf Curl Virus (CLCuV)",
            cropType = CropType.COTTON,
            generalDistrict = "Multan / Khanewal Cotton Belt",
            verificationStatus = VerificationStatus.EXPERT_CONFIRMED,
            severity = OutbreakSeverity.CRITICAL,
            reportCount = 42,
            dateReported = "Recent 7 Days",
            preventiveAdvisory = "Whitefly population pressure rising. Spray yellow sticky traps, rogue early curled plants, and apply bio-pesticides in cooler hours.",
            sourceCitation = "Directorate General Pest Warning & Quality Control of Pesticides"
        ),
        OutbreakAlert(
            id = "alert_yellow_rust_02",
            diseaseName = "Wheat Stripe / Yellow Rust (Puccinia striiformis)",
            cropType = CropType.WHEAT,
            generalDistrict = "Faisalabad & Sialkot Division",
            verificationStatus = VerificationStatus.EXPERT_CONFIRMED,
            severity = OutbreakSeverity.MODERATE,
            reportCount = 18,
            dateReported = "Recent 5 Days",
            preventiveAdvisory = "High night humidity and morning fog accelerating stripe rust pustules. Scout susceptible varieties and maintain clean bunds.",
            sourceCitation = "PARC Wheat Programme & Ayub Agricultural Research Institute"
        ),
        OutbreakAlert(
            id = "alert_rice_blast_03",
            diseaseName = "Rice Blast (Pyricularia oryzae)",
            cropType = CropType.RICE,
            generalDistrict = "Gujranwala / Sheikhupura Kalar Belt",
            verificationStatus = VerificationStatus.SUSPECTED_AI_FINDING,
            severity = OutbreakSeverity.WATCH,
            reportCount = 6,
            dateReported = "Recent 48 Hours",
            preventiveAdvisory = "Early leaf spindle lesions flagged by farmer community scans. Awaiting extension officer field sampling.",
            sourceCitation = "Community Farmer Scouting Network (Opt-In Aggregation)"
        ),
        OutbreakAlert(
            id = "alert_mango_anthracnose_04",
            diseaseName = "Mango Blossom Blight & Anthracnose",
            cropType = CropType.MANGO,
            generalDistrict = "Mirpur Khas & Hyderabad Agri Belt",
            verificationStatus = VerificationStatus.EXPERT_CONFIRMED,
            severity = OutbreakSeverity.MODERATE,
            reportCount = 12,
            dateReported = "Recent 3 Days",
            preventiveAdvisory = "Prune dead twigs, burn fallen infected leaves, and avoid overhead sprinkler watering in orchards.",
            sourceCitation = "Sindh Agriculture Extension & Horticulture Research Institute"
        )
    )

    fun getActiveAlerts(): List<OutbreakAlert> = sampleAlerts.toList()

    fun getAlertsForCrop(crop: CropType): List<OutbreakAlert> =
        sampleAlerts.filter { it.cropType == crop }

    fun submitCommunityReport(report: FarmerOutbreakReport): Boolean {
        if (!report.isOptInSharingConfirmed) return false

        // In production this pushes to backend anonymized queue.
        // For local applet, we synthesize an opt-in privacy-conscious alert entry with coarse location.
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val newAlert = OutbreakAlert(
            id = "alert_user_${System.currentTimeMillis()}",
            diseaseName = report.suspectedDisease,
            cropType = report.cropType,
            generalDistrict = report.coarseDistrict.ifBlank { "Regional Basin" },
            verificationStatus = VerificationStatus.SUSPECTED_AI_FINDING,
            severity = OutbreakSeverity.WATCH,
            reportCount = 1,
            dateReported = dateFormat.format(Date()),
            preventiveAdvisory = "Farmer community observation recorded: ${report.anonymizedNotes.ifBlank { "Early foliar symptoms reported." }}",
            sourceCitation = "Opt-In Farmer Scouting (Strictly Anonymized Coarse District)"
        )
        sampleAlerts.add(0, newAlert)
        return true
    }
}
