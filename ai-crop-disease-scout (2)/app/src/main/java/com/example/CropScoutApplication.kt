package com.example

import android.app.Application
import com.example.data.local.AppDatabase
import com.example.data.local.ScanRepository
import com.example.data.remote.CropVisionAnalyzer
import com.example.data.remote.CropVoiceAgentService
import com.example.data.voice.AgriVoiceService

class CropScoutApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ScanRepository(database.scanDao()) }
    val visionAnalyzer by lazy { CropVisionAnalyzer() }
    val voiceService by lazy { AgriVoiceService(this) }
    val voiceAgentService by lazy { CropVoiceAgentService() }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        voiceService.shutdown()
    }
}
