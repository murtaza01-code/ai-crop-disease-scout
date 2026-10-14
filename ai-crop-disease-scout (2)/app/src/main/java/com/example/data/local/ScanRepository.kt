package com.example.data.local

import com.example.data.model.DiseaseAnalysisResult
import kotlinx.coroutines.flow.Flow

class ScanRepository(private val scanDao: ScanDao) {
    val allScans: Flow<List<ScanEntity>> = scanDao.getAllScans()
    val flaggedScans: Flow<List<ScanEntity>> = scanDao.getFlaggedScans()
    val farmerProfile: Flow<FarmerProfileEntity?> = scanDao.getPrimaryProfile()
    val pendingSyncTasks: Flow<List<OfflineSyncTaskEntity>> = scanDao.getPendingSyncTasks()

    fun getScansByCrop(cropId: String): Flow<List<ScanEntity>> = scanDao.getScansByCrop(cropId)

    suspend fun saveScan(
        result: DiseaseAnalysisResult,
        imageUri: String?,
        photoQualityScore: Float = 0.85f,
        syncStatus: String = "LOCAL_STORED"
    ): Long {
        val entity = ScanEntity(
            cropId = result.cropType.id,
            cropName = result.cropType.englishName,
            diseaseName = result.conditionName,
            diseaseLocalName = result.conditionLocalName,
            scientificName = result.scientificName,
            isHealthy = result.isHealthy,
            confidenceScore = result.confidenceScore,
            confidenceLevel = result.confidenceLevel.name,
            symptoms = result.symptoms.joinToString("|||"),
            explanation = result.explanation,
            actions = result.immediateActions.joinToString("|||"),
            prevention = result.preventiveMeasures.joinToString("|||"),
            imageUri = imageUri,
            photoQualityScore = photoQualityScore,
            syncStatus = syncStatus
        )
        return scanDao.insertScan(entity)
    }

    suspend fun deleteScan(id: Long) {
        scanDao.deleteScanById(id)
    }

    suspend fun flagScan(id: Long, feedback: String) {
        scanDao.setFlagged(id, true, feedback)
    }

    suspend fun addAgronomistNote(id: Long, note: String) {
        scanDao.setAgronomistNotes(id, note)
    }

    suspend fun saveProfile(profile: FarmerProfileEntity) {
        scanDao.saveProfile(profile)
    }

    suspend fun enqueueOfflineTask(taskType: String, payloadJson: String): Long {
        val task = OfflineSyncTaskEntity(
            taskType = taskType,
            payloadJson = payloadJson
        )
        return scanDao.insertSyncTask(task)
    }

    suspend fun completeOfflineTask(taskId: Long) {
        scanDao.markTaskCompleted(taskId)
    }

    suspend fun clearCompletedSyncTasks() {
        scanDao.clearCompletedTasks()
    }
}
