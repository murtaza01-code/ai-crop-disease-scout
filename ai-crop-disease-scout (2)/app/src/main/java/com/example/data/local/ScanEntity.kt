package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scans")
data class ScanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val cropId: String,
    val cropName: String,
    val diseaseName: String,
    val diseaseLocalName: String,
    val scientificName: String? = null,
    val isHealthy: Boolean,
    val confidenceScore: Float,
    val confidenceLevel: String, // "HIGH", "MODERATE", "UNCERTAIN"
    val symptoms: String, // Delimited by |||
    val explanation: String,
    val actions: String, // Delimited by |||
    val prevention: String, // Delimited by |||
    val imageUri: String? = null,
    val isFlaggedForReview: Boolean = false,
    val agronomistNotes: String? = null,
    val farmerFeedback: String? = null,
    val farmerDistrict: String? = "Punjab / Sindh Basin",
    // Schema version 2 additions:
    val syncStatus: String = "LOCAL_STORED", // "LOCAL_STORED", "QUEUED_FOR_ONLINE", "SYNCED"
    val photoQualityScore: Float = 0.85f,
    val severityStage: String = "MODERATE" // "MILD", "MODERATE", "SEVERE"
)

@Entity(tableName = "farmer_profiles")
data class FarmerProfileEntity(
    @PrimaryKey
    val id: String = "primary_farmer",
    val name: String = "Kisan Dost",
    val district: String = "Multan / Sukkur Agri Belt",
    val primaryCrop: String = "Cotton & Wheat",
    val languageCode: String = "en",
    val role: String = "FARMER" // "FARMER" or "AGRONOMIST_ADMIN"
)

@Entity(tableName = "offline_sync_queue")
data class OfflineSyncTaskEntity(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long = 0,
    val taskType: String, // "COMMUNITY_OUTBREAK_REPORT", "CLOUD_DIAGNOSTIC_VERIFY", "FLAG_REVIEW"
    val payloadJson: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)
