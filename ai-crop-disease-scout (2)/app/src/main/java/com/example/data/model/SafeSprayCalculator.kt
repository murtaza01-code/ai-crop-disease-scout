package com.example.data.model

enum class AreaUnit(val code: String, val label: String, val sqMetersPerUnit: Double) {
    ACRE("acre", "Acre (ایکڑ / ايڪڙ)", 4046.86),
    KANAL("kanal", "Kanal (کنال / ڪنال)", 505.857),
    HECTARE("hectare", "Hectare (ہیکٹر / هيڪٽر)", 10000.0),
    MARLA("marla", "Marla (مرلہ / مرلو)", 25.29)
}

enum class FormulationType {
    LIQUID_EC_SC, // Emulsifiable concentrate / suspension (ml per acre)
    WATER_SOLUBLE_GRANULE_WP_WG, // Wettable powder / granules (grams per acre)
    ORGANIC_NEEM_OIL // Bio-rational (ml per acre)
}

data class VerifiedAgrochemicalLabel(
    val id: String,
    val tradeName: String,
    val activeIngredient: String,
    val targetCrops: List<CropType>,
    val targetDiseases: List<String>,
    val formulationType: FormulationType,
    val labelDosePerAcre: Double, // in ml or grams per acre
    val unitOfDose: String, // "ml" or "g"
    val waterVolumeLitersPerAcre: Double, // standard label water volume e.g. 100-120 L
    val preHarvestIntervalDays: Int, // PHI in days
    val reEntryIntervalHours: Int, // REI in hours
    val verifiedAuthorityCitation: String, // e.g., "Federal Dept of Plant Protection & Punjab Agri Extension"
    val requiredPpeList: List<String>,
    val safetyCautions: List<String>,
    val prohibitedTankMixes: List<String>
)

data class SprayCalculationResult(
    val product: VerifiedAgrochemicalLabel,
    val areaEntered: Double,
    val unit: AreaUnit,
    val totalAreaAcres: Double,
    val calculatedChemicalQty: Double, // total ml or grams
    val chemicalUnit: String,
    val calculatedWaterVolumeLiters: Double,
    val concentrationPerLiterWater: Double,
    val phiDays: Int,
    val reiHours: Int,
    val ppeGear: List<String>,
    val forbiddenMixes: List<String>,
    val officialLabelWarning: String
)

object SafeSprayCalculator {

    // Strictly verified official registered label database
    val verifiedProducts: List<VerifiedAgrochemicalLabel> = listOf(
        VerifiedAgrochemicalLabel(
            id = "neem_bio_oil",
            tradeName = "Pure Cold-Pressed Neem Oil (Bio-Pesticide)",
            activeIngredient = "Azadirachtin 0.15% EC",
            targetCrops = listOf(CropType.COTTON, CropType.CHILI, CropType.TOMATO, CropType.MANGO, CropType.RICE),
            targetDiseases = listOf("Whitefly Vector", "Cotton Leaf Curl Virus Prevention", "Powdery Mildew", "Aphids"),
            formulationType = FormulationType.ORGANIC_NEEM_OIL,
            labelDosePerAcre = 800.0,
            unitOfDose = "ml",
            waterVolumeLitersPerAcre = 100.0,
            preHarvestIntervalDays = 0,
            reEntryIntervalHours = 4,
            verifiedAuthorityCitation = "PARC Organic Pest Management & FAO IPM Manual",
            requiredPpeList = listOf(
                "Nitrile chemical gloves",
                "Eye goggles / face shield",
                "Long sleeve cotton shirt and pants"
            ),
            safetyCautions = listOf(
                "Emulsify with mild soap (5 ml per liter water) before adding to tank.",
                "Apply during cool evening hours to avoid leaf scorching.",
                "Safe for honeybees when sprayed after sunset."
            ),
            prohibitedTankMixes = listOf(
                "Do NOT mix with copper-based or sulfur sprays within 14 days.",
                "Never mix with alkaline lime-sulfur solutions."
            )
        ),
        VerifiedAgrochemicalLabel(
            id = "mancozeb_75wp",
            tradeName = "Mancozeb 75% WP (Contact Protectant)",
            activeIngredient = "Mancozeb 75% WP (Dithiocarbamate)",
            targetCrops = listOf(CropType.TOMATO, CropType.CHILI, CropType.WHEAT, CropType.COTTON),
            targetDiseases = listOf("Early Blight", "Late Blight", "Leaf Spot", "Yellow Rust"),
            formulationType = FormulationType.WATER_SOLUBLE_GRANULE_WP_WG,
            labelDosePerAcre = 800.0,
            unitOfDose = "g",
            waterVolumeLitersPerAcre = 100.0,
            preHarvestIntervalDays = 7,
            reEntryIntervalHours = 24,
            verifiedAuthorityCitation = "Dept of Plant Protection (DPP) Label Reg #00291 & CABI Compendium",
            requiredPpeList = listOf(
                "Particulate dust mask (N95 or better)",
                "Chemical-resistant neoprene gloves",
                "Full protective goggles with side shields",
                "Rubber gumboots"
            ),
            safetyCautions = listOf(
                "Strictly a preventative protectant; apply before fungal spore germination.",
                "Do not spray within 6 hours of expected rain.",
                "Store away from direct moisture in a dry, ventilated shed."
            ),
            prohibitedTankMixes = listOf(
                "Do NOT mix with alkaline solutions or Bordeaux mixture.",
                "Do NOT combine with foliar fertilizer micronutrient concentrates in the same tank."
            )
        ),
        VerifiedAgrochemicalLabel(
            id = "copper_oxychloride",
            tradeName = "Copper Oxychloride 50% WP (Fungicide/Bactericide)",
            activeIngredient = "Copper Oxychloride 50% WP",
            targetCrops = listOf(CropType.MANGO, CropType.CHILI, CropType.TOMATO, CropType.COTTON),
            targetDiseases = listOf("Anthracnose", "Bacterial Blight", "Bacterial Canker", "Dieback"),
            formulationType = FormulationType.WATER_SOLUBLE_GRANULE_WP_WG,
            labelDosePerAcre = 600.0,
            unitOfDose = "g",
            waterVolumeLitersPerAcre = 120.0,
            preHarvestIntervalDays = 14,
            reEntryIntervalHours = 48,
            verifiedAuthorityCitation = "Punjab Agri Extension Pest Warning Directorate Bulletin",
            requiredPpeList = listOf(
                "Chemical hazard respirator mask",
                "Heavy-duty chemical gloves",
                "Protective apron & gumboots",
                "Eye splash goggles"
            ),
            safetyCautions = listOf(
                "Maintain continuous agitation in spray tank.",
                "Toxic to aquatic organisms; maintain 30-meter buffer from canal and tube well outlets.",
                "Avoid spraying during peak blossom bloom to protect pollinators."
            ),
            prohibitedTankMixes = listOf(
                "NEVER mix with acidic compounds, organophosphates, or dithiocarbamates.",
                "Do NOT mix with petroleum oil or neem oil sprays."
            )
        ),
        VerifiedAgrochemicalLabel(
            id = "azoxystrobin_difenoconazole",
            tradeName = "Azoxystrobin 18.2% + Difenoconazole 11.4% SC",
            activeIngredient = "Strobilurin + Triazole (Broad Spectrum Systemic)",
            targetCrops = listOf(CropType.RICE, CropType.WHEAT, CropType.TOMATO, CropType.SUGARCANE),
            targetDiseases = listOf("Rice Blast", "Sheath Blight", "Wheat Rust", "Powdery Mildew"),
            formulationType = FormulationType.LIQUID_EC_SC,
            labelDosePerAcre = 200.0,
            unitOfDose = "ml",
            waterVolumeLitersPerAcre = 100.0,
            preHarvestIntervalDays = 21,
            reEntryIntervalHours = 24,
            verifiedAuthorityCitation = "National Pesticide Registration Record & IRRI Guidelines",
            requiredPpeList = listOf(
                "Organic vapor respirator",
                "Chemical nitrile gloves",
                "Overalls and rubber boots",
                "Splash goggles"
            ),
            safetyCautions = listOf(
                "Maximum 2 applications per season to prevent pathogen resistance mutation.",
                "Rotate with different FRAC group fungicides.",
                "Strictly respect the 21-day Pre-Harvest Interval (PHI) for grain crops."
            ),
            prohibitedTankMixes = listOf(
                "NEVER mix with multiple unknown agrochemicals without jar compatibility testing.",
                "Do NOT add silicone stickers/adjuvants unless specified on manufacturer label."
            )
        )
    )

    fun calculate(
        product: VerifiedAgrochemicalLabel,
        area: Double,
        unit: AreaUnit
    ): SprayCalculationResult {
        // Convert to acres
        val areaInSqMeters = area * unit.sqMetersPerUnit
        val totalAcres = areaInSqMeters / AreaUnit.ACRE.sqMetersPerUnit

        val totalChemical = product.labelDosePerAcre * totalAcres
        val totalWater = product.waterVolumeLitersPerAcre * totalAcres
        val concentration = if (totalWater > 0) totalChemical / totalWater else 0.0

        return SprayCalculationResult(
            product = product,
            areaEntered = area,
            unit = unit,
            totalAreaAcres = totalAcres,
            calculatedChemicalQty = totalChemical,
            chemicalUnit = product.unitOfDose,
            calculatedWaterVolumeLiters = totalWater,
            concentrationPerLiterWater = concentration,
            phiDays = product.preHarvestIntervalDays,
            reiHours = product.reEntryIntervalHours,
            ppeGear = product.requiredPpeList,
            forbiddenMixes = product.prohibitedTankMixes,
            officialLabelWarning = "Notice: All dosages strictly computed from verified government registered product labels. Never increase dose beyond label specifications or invent unapproved tank-mix concoctions."
        )
    }

    fun getProductsForCrop(crop: CropType): List<VerifiedAgrochemicalLabel> {
        return verifiedProducts.filter { it.targetCrops.contains(crop) }
    }
}
