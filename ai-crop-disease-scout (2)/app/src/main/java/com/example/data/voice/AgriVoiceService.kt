package com.example.data.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.data.model.AppLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class AgriVoiceService(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false
    private var pendingSpeech: Pair<String, AppLanguage>? = null

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                }
            })

            pendingSpeech?.let { (text, lang) ->
                pendingSpeech = null
                speak(text, lang)
            }
        }
    }

    fun speak(text: String, language: AppLanguage) {
        if (!isInitialized || tts == null) {
            pendingSpeech = Pair(text, language)
            return
        }

        stop()

        val locale = when (language) {
            AppLanguage.URDU -> {
                // Try Urdu (PK), fallback to generic urdu or hindi TTS phonetics
                val urduLocale = Locale("ur", "PK")
                val avail = tts?.isLanguageAvailable(urduLocale) ?: TextToSpeech.LANG_NOT_SUPPORTED
                if (avail >= TextToSpeech.LANG_AVAILABLE) {
                    urduLocale
                } else {
                    Locale("ur")
                }
            }
            AppLanguage.SINDHI -> {
                val sindhiLocale = Locale("sd", "PK")
                val avail = tts?.isLanguageAvailable(sindhiLocale) ?: TextToSpeech.LANG_NOT_SUPPORTED
                if (avail >= TextToSpeech.LANG_AVAILABLE) {
                    sindhiLocale
                } else {
                    Locale("sd")
                }
            }
            AppLanguage.ENGLISH -> Locale.ENGLISH
        }

        try {
            val result = tts?.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to default or English/regional
                tts?.language = Locale.getDefault()
            }
        } catch (e: Exception) {
            tts?.language = Locale.getDefault()
        }

        // Set friendly speaking rate for rural clarity
        tts?.setSpeechRate(0.88f)
        tts?.setPitch(1.0f)

        val utteranceId = "agri_voice_${System.currentTimeMillis()}"
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        _isSpeaking.value = true
    }

    fun stop() {
        if (tts != null && tts!!.isSpeaking) {
            tts?.stop()
        }
        _isSpeaking.value = false
    }

    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
    }
}
