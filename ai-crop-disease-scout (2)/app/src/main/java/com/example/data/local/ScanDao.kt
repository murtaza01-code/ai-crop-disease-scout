package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {
    @Query("SELECT * FROM scans ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<ScanEntity>>

    @Query("SELECT * FROM scans WHERE id = :id")
    suspend fun getScanById(id: Long): ScanEntity?

    @Query("SELECT * FROM scans WHERE cropId = :cropId ORDER BY timestamp DESC")
    fun getScansByCrop(cropId: String): Flow<List<ScanEntity>>

    @Query("SELECT * FROM scans WHERE isFlaggedForReview = 1 ORDER BY timestamp DESC")
    fun getFlaggedScans(): Flow<List<ScanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: ScanEntity): Long

    @Update
    suspend fun updateScan(scan: ScanEntity)

    @Delete
    suspend fun deleteScan(scan: ScanEntity)

    @Query("DELETE FROM scans WHERE id = :id")
    suspend fun deleteScanById(id: Long)

    @Query("UPDATE scans SET isFlaggedForReview = :flagged, farmerFeedback = :feedback WHERE id = :id")
    suspend fun setFlagged(id: Long, flagged: Boolean, feedback: String?)

    @Query("UPDATE scans SET agronomistNotes = :notes WHERE id = :id")
    suspend fun setAgronomistNotes(id: Long, notes: String)

    @Query("SELECT * FROM farmer_profiles WHERE id = 'primary_farmer' LIMIT 1")
    fun getPrimaryProfile(): Flow<FarmerProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: FarmerProfileEntity)

    // Offline Queue DAO operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncTask(task: OfflineSyncTaskEntity): Long

    @Query("SELECT * FROM offline_sync_queue WHERE isCompleted = 0 ORDER BY createdAt ASC")
    fun getPendingSyncTasks(): Flow<List<OfflineSyncTaskEntity>>

    @Query("UPDATE offline_sync_queue SET isCompleted = 1 WHERE taskId = :taskId")
    suspend fun markTaskCompleted(taskId: Long)

    @Query("DELETE FROM offline_sync_queue WHERE isCompleted = 1")
    suspend fun clearCompletedTasks()
}
