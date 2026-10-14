package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AppStrings
import com.example.ui.viewmodel.Screen

@Composable
fun AgriBottomNav(
    currentScreen: Screen,
    currentLanguage: AppLanguage,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        // Home
        NavigationBarItem(
            selected = currentScreen is Screen.Home,
            onClick = { onNavigate(Screen.Home) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = AppStrings.get("home", currentLanguage)
                )
            },
            label = {
                Text(
                    text = AppStrings.get("home", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen is Screen.Home) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_home")
        )

        // Scanner
        NavigationBarItem(
            selected = currentScreen is Screen.Scanner || currentScreen is Screen.Result,
            onClick = { onNavigate(Screen.Scanner) },
            icon = {
                Icon(
                    imageVector = Icons.Default.CenterFocusStrong,
                    contentDescription = AppStrings.get("scanner", currentLanguage)
                )
            },
            label = {
                Text(
                    text = AppStrings.get("scanner", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen is Screen.Scanner || currentScreen is Screen.Result) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_scanner")
        )

        // History
        NavigationBarItem(
            selected = currentScreen is Screen.Dashboard,
            onClick = { onNavigate(Screen.Dashboard) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Assessment,
                    contentDescription = AppStrings.get("history", currentLanguage)
                )
            },
            label = {
                Text(
                    text = AppStrings.get("history", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen is Screen.Dashboard) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_history")
        )

        // Knowledge Base
        NavigationBarItem(
            selected = currentScreen is Screen.KnowledgeBase,
            onClick = { onNavigate(Screen.KnowledgeBase) },
            icon = {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = AppStrings.get("knowledge_base", currentLanguage)
                )
            },
            label = {
                Text(
                    text = AppStrings.get("knowledge_base", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen is Screen.KnowledgeBase) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_knowledge")
        )

        // Profile
        NavigationBarItem(
            selected = currentScreen is Screen.Profile,
            onClick = { onNavigate(Screen.Profile) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = AppStrings.get("profile", currentLanguage)
                )
            },
            label = {
                Text(
                    text = AppStrings.get("profile", currentLanguage),
                    fontSize = 11.sp,
                    fontWeight = if (currentScreen is Screen.Profile) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_profile")
        )
    }
}
