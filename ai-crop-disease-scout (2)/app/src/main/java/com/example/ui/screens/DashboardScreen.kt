package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ScanEntity
import com.example.data.model.AppLanguage
import com.example.data.model.AppStrings
import com.example.data.model.CropType
import com.example.ui.theme.AlertRed
import com.example.ui.theme.HealthGreen
import com.example.ui.viewmodel.CropScoutViewModel
import com.example.ui.viewmodel.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: CropScoutViewModel,
    currentLanguage: AppLanguage
) {
    val scans by viewModel.allScans.collectAsState()
    val searchQuery by viewModel.historySearchQuery.collectAsState()
    val cropFilter by viewModel.historyCropFilter.collectAsState()

    // Calculate real stats from stored records
    val totalScans = scans.size
    val healthyCount = scans.count { it.isHealthy }
    val diseaseCount = scans.count { !it.isHealthy }
    val flaggedCount = scans.count { it.isFlaggedForReview }

    // Filter scans
    val filteredScans = scans.filter { scan ->
        val matchesCrop = if (cropFilter == CropType.AUTO_DETECT) true else scan.cropId.equals(cropFilter.id, ignoreCase = true)
        val matchesQuery = if (searchQuery.isBlank()) true else {
            scan.cropName.contains(searchQuery, ignoreCase = true) ||
            scan.diseaseName.contains(searchQuery, ignoreCase = true) ||
            scan.diseaseLocalName.contains(searchQuery, ignoreCase = true) ||
            scan.symptoms.contains(searchQuery, ignoreCase = true)
        }
        matchesCrop && matchesQuery
    }

    val dateFormat = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // Summary Cards Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Total
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = AppStrings.get("total_scans", currentLanguage),
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = totalScans.toString(),
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Healthy
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD8F3DC))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = AppStrings.get("healthy_count", currentLanguage),
                        fontSize = 11.sp,
                        color = Color(0xFF1B4332)
                    )
                    Text(
                        text = healthyCount.toString(),
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = HealthGreen
                    )
                }
            }

            // Disease Alerts
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = AppStrings.get("disease_alerts", currentLanguage),
                        fontSize = 11.sp,
                        color = Color(0xFF991B1B)
                    )
                    Text(
                        text = diseaseCount.toString(),
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = AlertRed
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setHistorySearchQuery(it) },
            placeholder = { Text(AppStrings.get("search_history", currentLanguage), fontSize = 13.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setHistorySearchQuery("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("history_search_input"),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter chips row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(CropType.entries) { crop ->
                val isSelected = crop == cropFilter
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setHistoryCropFilter(crop) },
                    label = {
                        Text(
                            text = if (crop == CropType.AUTO_DETECT) AppStrings.get("filter_all", currentLanguage) else "${crop.iconEmoji} ${crop.getDisplayName(currentLanguage)}",
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Scans list or empty state
        if (filteredScans.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Assessment,
                        contentDescription = "Empty",
                        tint = Color.Gray,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (scans.isEmpty()) AppStrings.get("no_scans", currentLanguage) else "No scans match your current filter.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.navigateTo(Screen.Scanner) },
                        modifier = Modifier.testTag("empty_state_scan_button")
                    ) {
                        Icon(imageVector = Icons.Default.CenterFocusStrong, contentDescription = "Scan")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(AppStrings.get("scan_crop", currentLanguage))
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredScans, key = { it.id }) { scan ->
                    ScanHistoryItem(
                        scan = scan,
                        formattedDate = dateFormat.format(Date(scan.timestamp)),
                        onViewReport = { viewModel.showReportForScan(scan) },
                        onDelete = { viewModel.deleteScan(scan.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScanHistoryItem(
    scan: ScanEntity,
    formattedDate: String,
    onViewReport: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onViewReport() }
            .testTag("scan_item_${scan.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status Icon Indicator
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(if (scan.isHealthy) Color(0xFFD8F3DC) else Color(0xFFFEE2E2)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = CropType.fromId(scan.cropId).iconEmoji,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = scan.cropName,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = formattedDate,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = scan.diseaseName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (scan.isHealthy) HealthGreen else Color(0xFFB91C1C)
                )

                if (scan.diseaseLocalName != scan.diseaseName) {
                    Text(
                        text = scan.diseaseLocalName,
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${(scan.confidenceScore * 100).toInt()}% Confidence",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    if (scan.isFlaggedForReview) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFEF3C7)
                        ) {
                            Text(
                                text = "Flagged for Expert",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Quick Actions
            IconButton(
                onClick = onViewReport,
                modifier = Modifier.testTag("view_report_${scan.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "View Report",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_scan_${scan.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
        }
    }
}
