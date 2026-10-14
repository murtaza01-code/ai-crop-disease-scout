package com.example.ui.viewmodel

sealed class Screen {
    object Home : Screen()
    object Scanner : Screen()
    object Result : Screen()
    object Dashboard : Screen()
    object KnowledgeBase : Screen()
    object VoiceAgent : Screen()
    object AdminReview : Screen()
    object Profile : Screen()
    object SprayCalculator : Screen()
    object OutbreakAlerts : Screen()
}

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.CropScoutApplication
import com.example.data.local.FarmerProfileEntity
import com.example.data.local.OfflineSyncTaskEntity
import com.example.data.local.ScanEntity
import com.example.data.model.AppLanguage
import com.example.data.model.AppStrings
import com.example.data.model.AreaUnit
import com.example.data.model.CropScanComparison
import com.example.data.model.CropType
import com.example.data.model.DiseaseAnalysisResult
import com.example.data.model.FarmerOutbreakReport
import com.example.data.model.OutbreakAlert
import com.example.data.model.OutbreakAlertRepository
import com.example.data.model.PhotoQualityAssessment
import com.example.data.model.PhotoQualityChecker
import com.example.data.model.RecoveryTracker
import com.example.data.model.SafeSprayCalculator
import com.example.data.model.SprayCalculationResult
import com.example.data.model.VerifiedAgrochemicalLabel
import com.example.data.sample.KnowledgeBaseData
import com.example.data.sample.SampleLeafCase
import com.example.data.sample.SampleLeaves
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CropScoutViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as CropScoutApplication
    private val repository = app.repository
    private val visionAnalyzer = app.visionAnalyzer
    private val voiceService = app.voiceService
    private val voiceAgentService = app.voiceAgentService

    // Voice State
    val isVoiceSpeaking: StateFlow<Boolean> = voiceService.isSpeaking
    private val _voiceAgentAnswer = MutableStateFlow<String?>(null)
    val voiceAgentAnswer: StateFlow<String?> = _voiceAgentAnswer.asStateFlow()
    private val _isVoiceLoading = MutableStateFlow(false)
    val isVoiceLoading: StateFlow<Boolean> = _isVoiceLoading.asStateFlow()
    private val _voiceDialogList = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val voiceDialogList: StateFlow<List<Pair<String, String>>> = _voiceDialogList.asStateFlow()

    // Auto-Analyze and Auto-Voice flags
    private val _autoVoiceEnabled = MutableStateFlow(true)
    val autoVoiceEnabled: StateFlow<Boolean> = _autoVoiceEnabled.asStateFlow()

    private val _autoAnalyzeEnabled = MutableStateFlow(true)
    val autoAnalyzeEnabled: StateFlow<Boolean> = _autoAnalyzeEnabled.asStateFlow()

    // Language state
    private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    // Navigation screen state
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Scans flow from Room
    val allScans: StateFlow<List<ScanEntity>> = repository.allScans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val flaggedScans: StateFlow<List<ScanEntity>> = repository.flaggedScans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendingSyncTasks: StateFlow<List<OfflineSyncTaskEntity>> = repository.pendingSyncTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Farmer Profile
    private val _farmerProfile = MutableStateFlow(
        FarmerProfileEntity(
            id = "primary_farmer",
            name = "Malik Aslam Khan",
            district = "Multan / Sukkur Agri Belt",
            primaryCrop = "Cotton, Wheat & Mango",
            languageCode = "en",
            role = "FARMER"
        )
    )
    val farmerProfile: StateFlow<FarmerProfileEntity> = _farmerProfile.asStateFlow()

    // Scanner state
    private val _selectedBitmap = MutableStateFlow<Bitmap?>(null)
    val selectedBitmap: StateFlow<Bitmap?> = _selectedBitmap.asStateFlow()

    private val _selectedImageUri = MutableStateFlow<String?>(null)
    val selectedImageUri: StateFlow<String?> = _selectedImageUri.asStateFlow()

    private val _selectedCrop = MutableStateFlow(CropType.AUTO_DETECT)
    val selectedCrop: StateFlow<CropType> = _selectedCrop.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _analyzingStepText = MutableStateFlow("")
    val analyzingStepText: StateFlow<String> = _analyzingStepText.asStateFlow()

    private val _lastAnalysisResult = MutableStateFlow<DiseaseAnalysisResult?>(null)
    val lastAnalysisResult: StateFlow<DiseaseAnalysisResult?> = _lastAnalysisResult.asStateFlow()

    private val _isSavedToHistory = MutableStateFlow(false)
    val isSavedToHistory: StateFlow<Boolean> = _isSavedToHistory.asStateFlow()

    private val _lastSavedScanId = MutableStateFlow<Long?>(null)
    val lastSavedScanId: StateFlow<Long?> = _lastSavedScanId.asStateFlow()

    // 1. AI Photo Quality Checker State
    private val _photoQualityAssessment = MutableStateFlow<PhotoQualityAssessment?>(null)
    val photoQualityAssessment: StateFlow<PhotoQualityAssessment?> = _photoQualityAssessment.asStateFlow()

    private val _showQualityWarningDialog = MutableStateFlow(false)
    val showQualityWarningDialog: StateFlow<Boolean> = _showQualityWarningDialog.asStateFlow()

    // 2. Recovery and Severity Tracker State
    private val _scanComparison = MutableStateFlow<CropScanComparison?>(null)
    val scanComparison: StateFlow<CropScanComparison?> = _scanComparison.asStateFlow()

    // 3. Spray Calculator State
    private val _selectedAgrochemical = MutableStateFlow(SafeSprayCalculator.verifiedProducts.first())
    val selectedAgrochemical: StateFlow<VerifiedAgrochemicalLabel> = _selectedAgrochemical.asStateFlow()

    private val _sprayAreaInput = MutableStateFlow("1.0")
    val sprayAreaInput: StateFlow<String> = _sprayAreaInput.asStateFlow()

    private val _sprayAreaUnit = MutableStateFlow(AreaUnit.ACRE)
    val sprayAreaUnit: StateFlow<AreaUnit> = _sprayAreaUnit.asStateFlow()

    private val _sprayCalculationResult = MutableStateFlow<SprayCalculationResult?>(null)
    val sprayCalculationResult: StateFlow<SprayCalculationResult?> = _sprayCalculationResult.asStateFlow()

    // 4. Outbreak Alerts State
    private val _communityAlerts = MutableStateFlow<List<OutbreakAlert>>(OutbreakAlertRepository.getActiveAlerts())
    val communityAlerts: StateFlow<List<OutbreakAlert>> = _communityAlerts.asStateFlow()

    private val _outbreakFilterCrop = MutableStateFlow(CropType.AUTO_DETECT)
    val outbreakFilterCrop: StateFlow<CropType> = _outbreakFilterCrop.asStateFlow()

    // History filter & search
    private val _historySearchQuery = MutableStateFlow("")
    val historySearchQuery: StateFlow<String> = _historySearchQuery.asStateFlow()

    private val _historyCropFilter = MutableStateFlow(CropType.AUTO_DETECT)
    val historyCropFilter: StateFlow<CropType> = _historyCropFilter.asStateFlow()

    // Knowledge Base filter & search
    private val _kbSearchQuery = MutableStateFlow("")
    val kbSearchQuery: StateFlow<String> = _kbSearchQuery.asStateFlow()

    private val _kbCropFilter = MutableStateFlow(CropType.AUTO_DETECT)
    val kbCropFilter: StateFlow<CropType> = _kbCropFilter.asStateFlow()

    // Report Sheet/Dialog
    private val _activeReportScan = MutableStateFlow<ScanEntity?>(null)
    val activeReportScan: StateFlow<ScanEntity?> = _activeReportScan.asStateFlow()

    init {
        // Collect saved profile from DB if exists
        viewModelScope.launch {
            repository.farmerProfile.collect { saved ->
                if (saved != null) {
                    _farmerProfile.value = saved
                    val lang = AppLanguage.entries.find { it.code == saved.languageCode } ?: AppLanguage.ENGLISH
                    _currentLanguage.value = lang
                }
            }
        }
        recalculateSpray()
    }

    // Network check for offline-first indicators
    fun isNetworkAvailable(): Boolean {
        return try {
            val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val net = cm.activeNetwork ?: return false
            val cap = cm.getNetworkCapabilities(net) ?: return false
            cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } catch (e: Exception) {
            false
        }
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
        viewModelScope.launch {
            repository.saveProfile(
                _farmerProfile.value.copy(languageCode = language.code)
            )
        }
    }

    fun setSelectedCrop(crop: CropType) {
        _selectedCrop.value = crop
        // Update spray product recommendation for chosen crop
        val cropProducts = SafeSprayCalculator.getProductsForCrop(crop)
        if (cropProducts.isNotEmpty()) {
            _selectedAgrochemical.value = cropProducts.first()
            recalculateSpray()
        }
    }

    fun setSelectedImageUri(uri: Uri?) {
        if (uri == null) return
        _selectedImageUri.value = uri.toString()
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(app.contentResolver, uri)
            handleNewBitmap(bitmap, uri.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSelectedBitmap(bitmap: Bitmap?, uriString: String? = null) {
        if (bitmap == null) {
            _selectedBitmap.value = null
            _selectedImageUri.value = null
            _photoQualityAssessment.value = null
            _showQualityWarningDialog.value = false
            return
        }
        handleNewBitmap(bitmap, uriString)
    }

    private fun handleNewBitmap(bitmap: Bitmap, uriString: String?) {
        _selectedBitmap.value = bitmap
        _selectedImageUri.value = uriString
        _isSavedToHistory.value = false

        // Feature 1: AI Photo Quality Check
        val quality = PhotoQualityChecker.analyze(bitmap)
        _photoQualityAssessment.value = quality

        if (!quality.isAcceptable) {
            _showQualityWarningDialog.value = true
            // Read out photo retake advice in selected language
            val adviceText = when (_currentLanguage.value) {
                AppLanguage.URDU -> quality.urduGuide
                AppLanguage.SINDHI -> quality.sindhiGuide
                AppLanguage.ENGLISH -> "${quality.guideTitle}. ${quality.guideDetail}"
            }
            voiceService.speak(adviceText, _currentLanguage.value)
        } else {
            _showQualityWarningDialog.value = false
            if (_autoAnalyzeEnabled.value) {
                analyzeCurrentLeaf()
            }
        }
    }

    fun dismissQualityWarningAndProceed() {
        _showQualityWarningDialog.value = false
        analyzeCurrentLeaf()
    }

    fun dismissQualityWarning() {
        _showQualityWarningDialog.value = false
        _selectedBitmap.value = null
        _selectedImageUri.value = null
        _photoQualityAssessment.value = null
    }

    fun loadSampleLeaf(sample: SampleLeafCase) {
        val bitmap = SampleLeaves.generateSampleBitmap(sample)
        _selectedBitmap.value = bitmap
        _selectedImageUri.value = "sample://${sample.id}"
        _selectedCrop.value = sample.cropType
        _isSavedToHistory.value = false

        // Assess quality (synthetic samples pass as sharp)
        val quality = PhotoQualityChecker.analyze(bitmap)
        _photoQualityAssessment.value = quality
        _showQualityWarningDialog.value = false

        if (_autoAnalyzeEnabled.value) {
            analyzeCurrentLeaf()
        }
    }

    fun toggleAutoVoice(enabled: Boolean) {
        _autoVoiceEnabled.value = enabled
    }

    fun toggleAutoAnalyze(enabled: Boolean) {
        _autoAnalyzeEnabled.value = enabled
    }

    fun analyzeCurrentLeaf() {
        val bitmap = _selectedBitmap.value ?: return
        viewModelScope.launch {
            _isAnalyzing.value = true
            _isSavedToHistory.value = false

            // Progressive feedback steps
            _analyzingStepText.value = AppStrings.get("analyzing_step1", _currentLanguage.value)
            delay(500)
            _analyzingStepText.value = AppStrings.get("analyzing_step2", _currentLanguage.value)
            delay(500)
            _analyzingStepText.value = AppStrings.get("analyzing_step3", _currentLanguage.value)

            val result = visionAnalyzer.analyzeCropLeaf(
                bitmap = bitmap,
                hintCropType = _selectedCrop.value,
                language = _currentLanguage.value
            )

            _lastAnalysisResult.value = result
            _isAnalyzing.value = false

            // Compute comparison with previous scan of same crop if available
            val previousScan = allScans.value.firstOrNull { it.cropId.equals(result.cropType.id, ignoreCase = true) }
            if (previousScan != null) {
                val comp = RecoveryTracker.compareScans(
                    previousDateMs = previousScan.timestamp,
                    previousCrop = previousScan.cropName,
                    previousCondition = previousScan.diseaseName,
                    previousIsHealthy = previousScan.isHealthy,
                    previousSymptoms = previousScan.symptoms.split("|||").filter { it.isNotBlank() },
                    latestDateMs = System.currentTimeMillis(),
                    latestCrop = result.cropType.englishName,
                    latestCondition = result.conditionName,
                    latestIsHealthy = result.isHealthy,
                    latestSymptoms = result.symptoms
                )
                _scanComparison.value = comp
            } else {
                _scanComparison.value = null
            }

            // Auto-save scan into local Room DB for record keeping
            val qualityScore = _photoQualityAssessment.value?.score ?: 0.85f
            val scanId = repository.saveScan(
                result = result,
                imageUri = _selectedImageUri.value,
                photoQualityScore = qualityScore,
                syncStatus = if (isNetworkAvailable()) "SYNCED" else "LOCAL_STORED"
            )
            _lastSavedScanId.value = scanId
            _isSavedToHistory.value = true

            _currentScreen.value = Screen.Result

            // Auto Agent Voice Speech: Start explaining automatically!
            if (_autoVoiceEnabled.value) {
                delay(350)
                val speech = voiceAgentService.createSpokenDiagnosisSummary(result, _currentLanguage.value)
                voiceService.speak(speech, _currentLanguage.value)
            }
        }
    }

    fun compareWithPreviousScan(previousScan: ScanEntity) {
        val currentRes = _lastAnalysisResult.value ?: return
        val comp = RecoveryTracker.compareScans(
            previousDateMs = previousScan.timestamp,
            previousCrop = previousScan.cropName,
            previousCondition = previousScan.diseaseName,
            previousIsHealthy = previousScan.isHealthy,
            previousSymptoms = previousScan.symptoms.split("|||").filter { it.isNotBlank() },
            latestDateMs = System.currentTimeMillis(),
            latestCrop = currentRes.cropType.englishName,
            latestCondition = currentRes.conditionName,
            latestIsHealthy = currentRes.isHealthy,
            latestSymptoms = currentRes.symptoms
        )
        _scanComparison.value = comp
    }

    // Safe Spray Calculator functions
    fun setSelectedAgrochemical(product: VerifiedAgrochemicalLabel) {
        _selectedAgrochemical.value = product
        recalculateSpray()
    }

    fun setSprayAreaInput(area: String) {
        _sprayAreaInput.value = area
        recalculateSpray()
    }

    fun setSprayAreaUnit(unit: AreaUnit) {
        _sprayAreaUnit.value = unit
        recalculateSpray()
    }

    fun recalculateSpray() {
        val areaDouble = _sprayAreaInput.value.toDoubleOrNull() ?: 1.0
        val safeArea = if (areaDouble <= 0.0) 1.0 else areaDouble
        val result = SafeSprayCalculator.calculate(
            product = _selectedAgrochemical.value,
            area = safeArea,
            unit = _sprayAreaUnit.value
        )
        _sprayCalculationResult.value = result
    }

    // Outbreak Alerts & Opt-in community reporting
    fun setOutbreakFilterCrop(crop: CropType) {
        _outbreakFilterCrop.value = crop
        _communityAlerts.value = if (crop == CropType.AUTO_DETECT) {
            OutbreakAlertRepository.getActiveAlerts()
        } else {
            OutbreakAlertRepository.getAlertsForCrop(crop)
        }
    }

    fun submitOptInOutbreakReport(
        crop: CropType,
        diseaseName: String,
        notes: String
    ): Boolean {
        val coarseDistrict = _farmerProfile.value.district
        val report = FarmerOutbreakReport(
            cropType = crop,
            suspectedDisease = diseaseName,
            coarseDistrict = coarseDistrict,
            isOptInSharingConfirmed = true,
            anonymizedNotes = notes
        )
        val success = OutbreakAlertRepository.submitCommunityReport(report)
        if (success) {
            _communityAlerts.value = OutbreakAlertRepository.getActiveAlerts()
            // Enqueue task for offline sync tracking
            viewModelScope.launch {
                repository.enqueueOfflineTask(
                    taskType = "COMMUNITY_OUTBREAK_REPORT",
                    payloadJson = """{"crop":"${crop.id}","disease":"$diseaseName","district":"$coarseDistrict"}"""
                )
            }
        }
        return success
    }

    fun saveCurrentResultToHistory() {
        val result = _lastAnalysisResult.value ?: return
        if (_isSavedToHistory.value) return
        viewModelScope.launch {
            val scanId = repository.saveScan(result, _selectedImageUri.value)
            _lastSavedScanId.value = scanId
            _isSavedToHistory.value = true
        }
    }

    fun flagLastScanForReview(feedback: String) {
        val scanId = _lastSavedScanId.value ?: return
        viewModelScope.launch {
            repository.flagScan(scanId, feedback)
        }
    }

    fun flagScanById(id: Long, feedback: String) {
        viewModelScope.launch {
            repository.flagScan(id, feedback)
        }
    }

    fun addAgronomistNote(id: Long, note: String) {
        viewModelScope.launch {
            repository.addAgronomistNote(id, note)
        }
    }

    fun deleteScan(id: Long) {
        viewModelScope.launch {
            repository.deleteScan(id)
        }
    }

    fun setHistorySearchQuery(query: String) {
        _historySearchQuery.value = query
    }

    fun setHistoryCropFilter(crop: CropType) {
        _historyCropFilter.value = crop
    }

    fun setKbSearchQuery(query: String) {
        _kbSearchQuery.value = query
    }

    fun setKbCropFilter(crop: CropType) {
        _kbCropFilter.value = crop
    }

    fun showReportForScan(scan: ScanEntity) {
        _activeReportScan.value = scan
    }

    fun dismissReport() {
        _activeReportScan.value = null
    }

    fun updateProfile(name: String, district: String, primaryCrop: String, role: String) {
        val updated = _farmerProfile.value.copy(
            name = name,
            district = district,
            primaryCrop = primaryCrop,
            role = role
        )
        _farmerProfile.value = updated
        viewModelScope.launch {
            repository.saveProfile(updated)
        }
    }

    fun speakText(text: String) {
        voiceService.speak(text, _currentLanguage.value)
    }

    fun stopVoice() {
        voiceService.stop()
    }

    fun speakCurrentResult() {
        val res = _lastAnalysisResult.value ?: return
        val speech = voiceAgentService.createSpokenDiagnosisSummary(res, _currentLanguage.value)
        voiceService.speak(speech, _currentLanguage.value)
    }

    fun askVoiceAssistant(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _isVoiceLoading.value = true
            val cropContext = if (_selectedCrop.value != CropType.AUTO_DETECT) _selectedCrop.value.englishName else "General Farm"
            val answer = voiceAgentService.answerFarmerQuery(query, _currentLanguage.value, cropContext)
            _voiceAgentAnswer.value = answer
            _voiceDialogList.value = _voiceDialogList.value + (query to answer)
            _isVoiceLoading.value = false
            voiceService.speak(answer, _currentLanguage.value)
        }
    }

    override fun onCleared() {
        super.onCleared()
        voiceService.stop()
    }
}
