package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ScanEntity::class, FarmerProfileEntity::class, OfflineSyncTaskEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Add new columns to scans table
                db.execSQL("ALTER TABLE scans ADD COLUMN syncStatus TEXT NOT NULL DEFAULT 'LOCAL_STORED'")
                db.execSQL("ALTER TABLE scans ADD COLUMN photoQualityScore REAL NOT NULL DEFAULT 0.85")
                db.execSQL("ALTER TABLE scans ADD COLUMN severityStage TEXT NOT NULL DEFAULT 'MODERATE'")

                // Create new offline sync queue table
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS offline_sync_queue (
                        taskId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        taskType TEXT NOT NULL,
                        payloadJson TEXT NOT NULL,
                        createdAt INTEGER NOT NULL,
                        isCompleted INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "crop_scout_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
