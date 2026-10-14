package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AgriBottomNav
import com.example.ui.components.AgriTopBar
import com.example.ui.components.PrintableReportDialog
import com.example.ui.screens.AdminReviewScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.KnowledgeBaseScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.screens.ScannerScreen
import com.example.ui.screens.VoiceAgentScreen
import com.example.ui.theme.CropScoutTheme
import com.example.ui.viewmodel.CropScoutViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CropScoutViewModel = viewModel()
            val currentLanguage by viewModel.currentLanguage.collectAsState()
            val currentScreen by viewModel.currentScreen.collectAsState()
            val activeReportScan by viewModel.activeReportScan.collectAsState()

            // RTL support for Urdu and Sindhi
            val layoutDirection = if (currentLanguage.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                CropScoutTheme {
                    // Back button handling
                    BackHandler(enabled = currentScreen !is Screen.Home) {
                        if (currentScreen is Screen.Result) {
                            viewModel.navigateTo(Screen.Scanner)
                        } else {
                            viewModel.navigateTo(Screen.Home)
                        }
                    }

                    Scaffold(
                        topBar = {
                            AgriTopBar(
                                currentLanguage = currentLanguage,
                                onLanguageChange = { viewModel.setLanguage(it) },
                                onVoiceAgentClick = { viewModel.navigateTo(Screen.VoiceAgent) },
                                onProfileClick = { viewModel.navigateTo(Screen.Profile) },
                                onAdminClick = { viewModel.navigateTo(Screen.AdminReview) },
                                currentScreen = currentScreen
                            )
                        },
                        bottomBar = {
                            AgriBottomNav(
                                currentScreen = currentScreen,
                                currentLanguage = currentLanguage,
                                onNavigate = { viewModel.navigateTo(it) }
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (currentScreen) {
                                is Screen.Home -> HomeScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                                is Screen.Scanner -> ScannerScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                                is Screen.Result -> ResultScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                                is Screen.Dashboard -> DashboardScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                                is Screen.KnowledgeBase -> KnowledgeBaseScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                                is Screen.VoiceAgent -> VoiceAgentScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                                is Screen.AdminReview -> AdminReviewScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                                is Screen.Profile -> ProfileScreen(
                                    viewModel = viewModel,
                                    currentLanguage = currentLanguage
                                )
                            }

                            // Printable Report Modal
                            activeReportScan?.let { scan ->
                                PrintableReportDialog(
                                    scan = scan,
                                    currentLanguage = currentLanguage,
                                    onDismiss = { viewModel.dismissReport() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
