package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AppStrings
import com.example.data.model.CropType
import com.example.data.model.OutbreakAlert
import com.example.data.model.OutbreakSeverity
import com.example.data.model.VerificationStatus
import com.example.ui.theme.AlertAmber
import com.example.ui.theme.AlertRed
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.HealthGreen
import com.example.ui.viewmodel.CropScoutViewModel

@Composable
fun OutbreakAlertsScreen(
    viewModel: CropScoutViewModel,
    currentLanguage: AppLanguage
) {
    val alerts by viewModel.communityAlerts.collectAsState()
    val filterCrop by viewModel.outbreakFilterCrop.collectAsState()
    val profile by viewModel.farmerProfile.collectAsState()

    var showReportDialog by remember { mutableStateOf(false) }
    var reportCrop by remember { mutableStateOf(CropType.COTTON) }
    var reportDiseaseName by remember { mutableStateOf("") }
    var reportNotes by remember { mutableStateOf("") }
    var reportOptInConfirmed by remember { mutableStateOf(true) }
    var reportSubmittedNotification by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF081C15)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF52B788)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Outbreak Alerts",
                            tint = Color(0xFF081C15),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = if (currentLanguage == AppLanguage.URDU) "علاقائی وبا اور بیماریوں کے الرٹ"
                            else if (currentLanguage == AppLanguage.SINDHI) "علائقي جي بيمارين جا چتاءَ ۽ الرٽ"
                            else "Local Crop Outbreak Alerts",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = if (currentLanguage == AppLanguage.URDU) "رازداری کے ساتھ کسانوں کی برادری کا مشاہدہ اور ماہرین کے جائزے"
                            else if (currentLanguage == AppLanguage.SINDHI) "رازداري سان ڳوٺاڻي چڪاس ۽ ماهرن جي تصديق"
                            else "Privacy-conscious early warnings, distinguishing AI scans from certified lab cases",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFD8F3DC)
                        )
                    }
                }
            }
        }

        // Privacy Guarantee Banner
        item {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFE8F5E9),
                border = androidx.compose.foundation.BorderStroke(1.dp, HealthGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Privacy Shield",
                        tint = HealthGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.URDU) "رازداری کا تحفظ: آپ کا پتہ، فون نمبر یا عین لوکیشن کبھی عام نہیں کی جاتی۔ صرف تحصیل/ضلع کی سطح پر الرٹ جاری ہوتے ہیں۔"
                        else if (currentLanguage == AppLanguage.SINDHI) "رازداري جو تحفظ: اوهان جو پتو يا فون نمبر ڪڏهن به ظاهر نٿو ڪيو وڃي، صرف تعلقي/ضلعي جي بنياد تي الرٽ ايندا آهن."
                        else "Privacy Protection: Precise farmer locations and names are NEVER published. Reports are aggregated strictly at the coarse district level.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1B4332),
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Action Row: Filter and Report button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Active Alerts (${alerts.size})",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Button(
                    onClick = { showReportDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("report_outbreak_btn")
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (currentLanguage == AppLanguage.URDU) "مشاہدہ شیئر کریں" else "Report Outbreak", fontSize = 12.sp)
                }
            }
        }

        // Crop Filter Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf(CropType.AUTO_DETECT) + CropType.entries.filter { it != CropType.AUTO_DETECT }) { crop ->
                    val isSelected = crop == filterCrop
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setOutbreakFilterCrop(crop) },
                        label = {
                            Text(
                                text = if (crop == CropType.AUTO_DETECT) "All Crops" else "${crop.iconEmoji} ${crop.getDisplayName(currentLanguage)}",
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // List of Outbreak Alerts
        items(alerts) { alert ->
            OutbreakAlertCard(alert = alert, currentLanguage = currentLanguage)
        }
    }

    // Opt-In Community Outbreak Report Dialog
    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = {
                Text(
                    text = if (currentLanguage == AppLanguage.URDU) "بیماری کی اطلاع درج کریں" else "Opt-In Community Outbreak Report",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Help neighboring smallholders by notifying extension systems of suspected foliar spread in ${profile.district}. Your personal identity remains anonymous.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )

                    // Crop Selection
                    Text("Select Crop Variety:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(CropType.entries.filter { it != CropType.AUTO_DETECT }) { crop ->
                            FilterChip(
                                selected = crop == reportCrop,
                                onClick = { reportCrop = crop },
                                label = { Text("${crop.iconEmoji} ${crop.englishName}", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    OutlinedTextField(
                        value = reportDiseaseName,
                        onValueChange = { reportDiseaseName = it },
                        label = { Text("Suspected Disease / Symptom") },
                        placeholder = { Text("e.g. Cotton Leaf Curl, Rust, Blast") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = reportNotes,
                        onValueChange = { reportNotes = it },
                        label = { Text("Field Observations (Optional)") },
                        placeholder = { Text("e.g. Yellow lesions appearing on border rows") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    // Privacy Opt-In Checkbox
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Checkbox(
                            checked = reportOptInConfirmed,
                            onCheckedChange = { reportOptInConfirmed = it },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "I consent to share anonymized district-level aggregate data for crop biosecurity.",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 11.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (reportDiseaseName.isNotBlank() && reportOptInConfirmed) {
                            viewModel.submitOptInOutbreakReport(
                                crop = reportCrop,
                                diseaseName = reportDiseaseName,
                                notes = reportNotes
                            )
                            showReportDialog = false
                            reportSubmittedNotification = true
                        }
                    },
                    enabled = reportDiseaseName.isNotBlank() && reportOptInConfirmed
                ) {
                    Text("Submit Report")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReportDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (reportSubmittedNotification) {
        AlertDialog(
            onDismissRequest = { reportSubmittedNotification = false },
            title = { Text("Report Submitted", fontWeight = FontWeight.Bold) },
            text = {
                Text("Thank you! Your anonymized report has been added to the regional crop surveillance queue. It is marked as 'Suspected AI Finding' pending expert field verification.")
            },
            confirmButton = {
                Button(onClick = { reportSubmittedNotification = false }) {
                    Text("Done")
                }
            }
        )
    }
}

@Composable
fun OutbreakAlertCard(alert: OutbreakAlert, currentLanguage: AppLanguage) {
    ElevatedCard(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status badge
                val isExpert = alert.verificationStatus == VerificationStatus.EXPERT_CONFIRMED
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isExpert) Color(0xFF1B4332) else Color(0xFFFFF3CD),
                    border = if (!isExpert) androidx.compose.foundation.BorderStroke(1.dp, AlertAmber) else null
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isExpert) Icons.Default.CheckCircle else Icons.Default.Info,
                            contentDescription = null,
                            tint = if (isExpert) Color.White else Color(0xFF664D03),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isExpert) "EXPERT CONFIRMED CASE" else "SUSPECTED AI FINDING",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isExpert) Color.White else Color(0xFF664D03)
                        )
                    }
                }

                // Severity badge
                val severityColor = when (alert.severity) {
                    OutbreakSeverity.CRITICAL -> AlertRed
                    OutbreakSeverity.MODERATE -> AlertAmber
                    OutbreakSeverity.WATCH -> HealthGreen
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = severityColor
                ) {
                    Text(
                        text = alert.severity.name,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${alert.cropType.iconEmoji} ${alert.diseaseName}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${alert.generalDistrict} • ${alert.dateReported}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Advisory Recommendation:", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(alert.preventiveAdvisory, fontSize = 11.sp, lineHeight = 15.sp)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Source: ${alert.sourceCitation}",
                fontSize = 10.sp,
                color = Color.Gray,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}
