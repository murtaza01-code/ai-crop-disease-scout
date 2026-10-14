package com.example

import com.example.data.model.AppLanguage
import com.example.data.model.AppStrings
import com.example.data.model.CropType
import com.example.data.sample.KnowledgeBaseData
import com.example.data.sample.SampleLeaves
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CropScoutUnitTest {

    @Test
    fun knowledgeBase_containsDocumentedDiseasesForAllCrops() {
        val diseases = KnowledgeBaseData.diseases
        assertTrue("Knowledge base should have multiple entries", diseases.size >= 7)

        val supportedCrops = listOf(
            CropType.COTTON,
            CropType.WHEAT,
            CropType.RICE,
            CropType.TOMATO,
            CropType.CHILI,
            CropType.MANGO,
            CropType.SUGARCANE
        )

        for (crop in supportedCrops) {
            val cropDiseases = KnowledgeBaseData.getByCrop(crop)
            assertTrue("Crop ${crop.englishName} must have documented diseases", cropDiseases.isNotEmpty())
            val first = cropDiseases.first()
            assertTrue("Symptoms should not be empty", first.symptomsEnglish.isNotEmpty())
            assertTrue("Source reference must be cited", first.sourceReference.isNotBlank())
        }
    }

    @Test
    fun knowledgeBase_searchFunctionWorksAcrossLanguages() {
        // Search in English
        val rustResults = KnowledgeBaseData.search("Rust", AppLanguage.ENGLISH)
        assertTrue(rustResults.any { it.diseaseName.contains("Rust", ignoreCase = true) })

        // Search in Urdu
        val urduResults = KnowledgeBaseData.search("کپاس", AppLanguage.URDU)
        assertTrue(urduResults.isNotEmpty())

        // Search in Sindhi
        val sindhiResults = KnowledgeBaseData.search("ڪڻڪ", AppLanguage.SINDHI)
        assertTrue(sindhiResults.isNotEmpty())
    }

    @Test
    fun languageModel_rtlHandledCorrectly() {
        assertFalse("English is LTR", AppLanguage.ENGLISH.isRtl)
        assertTrue("Urdu is RTL", AppLanguage.URDU.isRtl)
        assertTrue("Sindhi is RTL", AppLanguage.SINDHI.isRtl)

        val englishTitle = AppStrings.get("app_title", AppLanguage.ENGLISH)
        val urduTitle = AppStrings.get("app_title", AppLanguage.URDU)
        val sindhiTitle = AppStrings.get("app_title", AppLanguage.SINDHI)

        assertEquals("AI Crop Disease Scout", englishTitle)
        assertTrue(urduTitle.contains("اسکاؤٹ"))
        assertTrue(sindhiTitle.contains("اسڪائوٽ"))
    }

    @Test
    fun sampleLeaves_generateValidBitmaps() {
        val sample = SampleLeaves.sampleCases.first()
        val bitmap = SampleLeaves.generateSampleBitmap(sample)
        assertNotNull(bitmap)
        assertEquals(480, bitmap.width)
        assertEquals(480, bitmap.height)
    }
}
