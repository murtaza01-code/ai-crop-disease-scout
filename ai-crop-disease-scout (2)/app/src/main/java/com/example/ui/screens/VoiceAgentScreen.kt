package com.example.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AppStrings
import com.example.ui.theme.AlertRed
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.HealthGreen
import com.example.ui.viewmodel.CropScoutViewModel
import java.util.Locale

@Composable
fun VoiceAgentScreen(
    viewModel: CropScoutViewModel,
    currentLanguage: AppLanguage
) {
    val isSpeaking by viewModel.isVoiceSpeaking.collectAsState()
    val isLoading by viewModel.isVoiceLoading.collectAsState()
    val dialogHistory by viewModel.voiceDialogList.collectAsState()

    var inputQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Speech-to-text recognizer launcher
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
            if (!spokenText.isNullOrBlank()) {
                inputQuery = spokenText
                viewModel.askVoiceAssistant(spokenText)
            }
        }
    }

    // Voice pulsating wave animation
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_agent")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSpeaking) 1.25f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Pre-curated realistic farmer questions in Urdu & Sindhi
    val quickQuestions = when (currentLanguage) {
        AppLanguage.URDU -> listOf(
            "کپاس کے پتے مڑ رہے ہیں، کیا کروں؟",
            "گندم کی پیلی کنگی کا علاج بتائیں",
            "دھان پر آنکھ نما داغ کا حل کیا ہے؟",
            "ٹماٹر کے نچلے پتے سوکھ رہے ہیں",
            "سفید مکھی سے فصل کو کیسے بچائیں؟"
        )
        AppLanguage.SINDHI -> listOf(
            "ڪپھ جا پن مڙي ويا آهن، ڇا ڪريان؟",
            "ڪڻڪ جي پيلي رتي جو علاج ٻڌايو",
            "سارين جي پنن تي باهه جهڙا داغ آهن",
            "ٽماٽي جا پن ڇو سڙن پيا؟",
            "اڇي مکي جي روڪٿام ڪيئن ڪجي؟"
        )
        AppLanguage.ENGLISH -> listOf(
            "Cotton leaf margins are curling upwards",
            "Yellow powdery spots on wheat leaves",
            "Spindle shaped brown lesions on rice",
            "Lower tomato leaves drying out",
            "How to manage whitefly without harsh chemicals?"
        )
    }

    // Auto-scroll to bottom of conversation
    LaunchedEffect(dialogHistory.size) {
        if (dialogHistory.isNotEmpty()) {
            listState.animateScrollToItem(dialogHistory.size - 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Header Card with Visual Voice Agent Avatar
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Pulsating Agent Icon
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(
                                if (isSpeaking) {
                                    Brush.radialGradient(listOf(Color(0xFF52B788), Color(0xFF1B4332)))
                                } else {
                                    Brush.radialGradient(listOf(Color(0xFFD8F3DC), Color(0xFF2D6A4F)))
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.RecordVoiceOver else Icons.Default.Mic,
                            contentDescription = "Voice Agent Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(38.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = AppStrings.get("voice_agent", currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = if (isSpeaking) AppStrings.get("voice_talking", currentLanguage) else AppStrings.get("voice_subtitle", currentLanguage),
                        fontSize = 12.sp,
                        fontWeight = if (isSpeaking) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSpeaking) HealthGreen else Color.Gray
                    )

                    // Audio Stop button if speaking
                    AnimatedVisibility(visible = isSpeaking) {
                        Button(
                            onClick = { viewModel.stopVoice() },
                            colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Stop, contentDescription = "Stop", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(AppStrings.get("voice_stop", currentLanguage), fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Questions Title & Chips
            Text(
                text = AppStrings.get("voice_quick_questions", currentLanguage),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(quickQuestions) { question ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                inputQuery = question
                                viewModel.askVoiceAssistant(question)
                            }
                            .testTag("quick_voice_query")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Ask",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = question,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Conversation Log
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (dialogHistory.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = if (currentLanguage == AppLanguage.URDU) {
                                        "کسان بھائی! آپ مائیک کا بٹن دبا کر اپنی زبان میں سوال بول سکتے ہیں، یا اوپر دیئے گئے سوالات پر ٹیپ کر سکتے ہیں۔ وائس ایجنٹ آپ کو بول کر آسان حل بتائے گا۔"
                                    } else if (currentLanguage == AppLanguage.SINDHI) {
                                        "هاري ڀاءُ! توهان مائيڪ جو بٽڻ دٻائي سنڌيءَ ۾ پنهنجو سوال ڳالهائي سگهو ٿا، يا مٿي ڏنل سوال چونڊيو. وائس ايجنٽ توهان کي آواز ۾ سولو حل ٻڌائيندو."
                                    } else {
                                        "Tap the microphone to speak your question in Urdu, Sindhi, or English. The Voice Agent will speak back practical advice aloud!"
                                    },
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(dialogHistory) { (query, answer) ->
                        // Farmer Question Bubble
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.fillMaxWidth(0.85f)
                            ) {
                                Text(
                                    text = query,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }

                        // Voice Agent Spoken Answer Bubble
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp),
                                color = MaterialTheme.colorScheme.surface,
                                shadowElevation = 2.dp,
                                modifier = Modifier.fillMaxWidth(0.92f)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.RecordVoiceOver,
                                                contentDescription = "Voice",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "کسان دوست جواب",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }

                                        // Replay Voice button
                                        IconButton(
                                            onClick = { viewModel.speakText(answer) },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.VolumeUp,
                                                contentDescription = "Listen",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = answer,
                                        fontSize = 13.sp,
                                        lineHeight = 20.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                if (isLoading) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ایجنٹ جواب تیار کر رہا ہے...",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Input Bar with Mic button & Send button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Speech Input Mic Button
                FloatingActionButton(
                    onClick = {
                        val targetLangCode = when (currentLanguage) {
                            AppLanguage.URDU -> "ur-PK"
                            AppLanguage.SINDHI -> "sd-PK"
                            AppLanguage.ENGLISH -> "en-US"
                        }
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, targetLangCode)
                            putExtra(RecognizerIntent.EXTRA_PROMPT, AppStrings.get("voice_ask_prompt", currentLanguage))
                        }
                        try {
                            speechRecognizerLauncher.launch(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier
                        .size(52.dp)
                        .testTag("voice_agent_mic_button")
                ) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = "Voice Input", modifier = Modifier.size(26.dp))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = inputQuery,
                    onValueChange = { inputQuery = it },
                    placeholder = { Text(AppStrings.get("voice_ask_prompt", currentLanguage), fontSize = 12.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("voice_agent_text_input"),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (inputQuery.isNotBlank()) {
                            viewModel.askVoiceAssistant(inputQuery)
                            inputQuery = ""
                        }
                    },
                    modifier = Modifier.testTag("voice_agent_send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
