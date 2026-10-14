package com.example.data.model

enum class ConfidenceLevel {
    HIGH,
    MODERATE,
    UNCERTAIN;

    fun getLabel(language: AppLanguage): String {
        return when (this) {
            HIGH -> AppStrings.get("confidence_high", language)
            MODERATE -> AppStrings.get("confidence_med", language)
            UNCERTAIN -> AppStrings.get("confidence_low", language)
        }
    }
}

data class DiseaseAnalysisResult(
    val cropType: CropType,
    val conditionName: String,
    val conditionLocalName: String,
    val scientificName: String? = null,
    val isHealthy: Boolean,
    val confidenceScore: Float, // 0.0 to 1.0
    val confidenceLevel: ConfidenceLevel,
    val symptoms: List<String>,
    val explanation: String,
    val immediateActions: List<String>,
    val preventiveMeasures: List<String>,
    val safetyWarning: String = "Always consult a local agricultural extension officer before using chemical sprays.",
    val isLeafDetected: Boolean = true,
    val sourceCitation: String = "CABI PlantwisePlus & FAO Crop Protection Compendium",
    val aiModelSource: String = "Gemini Multimodal Vision API"
)

data class KnowledgeBaseDisease(
    val id: String,
    val cropType: CropType,
    val diseaseName: String,
    val urduName: String,
    val sindhiName: String,
    val scientificName: String,
    val severityLevel: String, // "High", "Moderate", "Watch"
    val symptomsEnglish: List<String>,
    val symptomsUrdu: List<String>,
    val symptomsSindhi: List<String>,
    val preventionEnglish: List<String>,
    val preventionUrdu: List<String>,
    val preventionSindhi: List<String>,
    val sourceReference: String
) {
    fun getLocalizedName(language: AppLanguage): String {
        return when (language) {
            AppLanguage.ENGLISH -> diseaseName
            AppLanguage.URDU -> urduName
            AppLanguage.SINDHI -> sindhiName
        }
    }

    fun getLocalizedSymptoms(language: AppLanguage): List<String> {
        return when (language) {
            AppLanguage.ENGLISH -> symptomsEnglish
            AppLanguage.URDU -> symptomsUrdu
            AppLanguage.SINDHI -> symptomsSindhi
        }
    }

    fun getLocalizedPrevention(language: AppLanguage): List<String> {
        return when (language) {
            AppLanguage.ENGLISH -> preventionEnglish
            AppLanguage.URDU -> preventionUrdu
            AppLanguage.SINDHI -> preventionSindhi
        }
    }
}
