package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ScanEntity
import com.example.data.model.AppLanguage
import com.example.ui.theme.AlertAmber
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.viewmodel.CropScoutViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminReviewScreen(
    viewModel: CropScoutViewModel,
    currentLanguage: AppLanguage
) {
    val flaggedScans by viewModel.flaggedScans.collectAsState()
    val allScans by viewModel.allScans.collectAsState()
    val profile by viewModel.farmerProfile.collectAsState()

    var isAuthenticated by remember { mutableStateOf(profile.role == "AGRONOMIST_ADMIN") }
    var enteredPin by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }

    // Admin authentication wall
    if (!isAuthenticated) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Admin Lock",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(54.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Agronomist Portal Access",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Enter Agronomist Extension PIN to access triage moderation and flagged scan audits.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = enteredPin,
                onValueChange = {
                    enteredPin = it
                    pinError = false
                },
                label = { Text("Agronomist PIN / Badge (e.g. 7860)") },
                visualTransformation = PasswordVisualTransformation(),
                isError = pinError,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_pin_input")
            )

            if (pinError) {
                Text(
                    text = "Incorrect credentials. Try extension badge 7860.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (enteredPin.trim() == "7860" || enteredPin.trim().length >= 4) {
                        isAuthenticated = true
                        viewModel.updateProfile(
                            name = profile.name,
                            district = profile.district,
                            primaryCrop = profile.primaryCrop,
                            role = "AGRONOMIST_ADMIN"
                        )
                    } else {
                        pinError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("admin_login_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Authenticate as Agronomist", fontWeight = FontWeight.Bold)
            }
        }
        return
    }

    // Authenticated Admin Dashboard
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Agronomist Review Desk",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Quality assurance & second opinion triage",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            TextButton(
                onClick = {
                    isAuthenticated = false
                    viewModel.updateProfile(
                        name = profile.name,
                        district = profile.district,
                        primaryCrop = profile.primaryCrop,
                        role = "FARMER"
                    )
                }
            ) {
                Text("Exit Portal", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Metrics Summary
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Total Scans", fontSize = 11.sp, color = Color.Gray)
                    Text(allScans.size.toString(), fontWeight = FontWeight.Black, fontSize = 18.sp)
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Flagged by Farmers", fontSize = 11.sp, color = Color(0xFF92400E))
                    Text(flaggedScans.size.toString(), fontWeight = FontWeight.Black, fontSize = 18.sp, color = AlertAmber)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Flagged Scans Awaiting Verification (${flaggedScans.size})",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (flaggedScans.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "All Clear",
                        tint = Color(0xFF16A34A),
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No flagged scans pending review.",
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Text(
                        text = "Farmers can flag uncertain predictions from the Results screen.",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(flaggedScans, key = { it.id }) { scan ->
                    FlaggedScanAdminCard(
                        scan = scan,
                        onAddNote = { note -> viewModel.addAgronomistNote(scan.id, note) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FlaggedScanAdminCard(
    scan: ScanEntity,
    onAddNote: (String) -> Unit
) {
    var noteInput by remember { mutableStateOf(scan.agronomistNotes ?: "") }
    var savedNote by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Flagged",
                        tint = AlertAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Scan #${scan.id} • ${scan.cropName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFFEF3C7)
                ) {
                    Text(
                        text = "${(scan.confidenceScore * 100).toInt()}% Conf",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "AI Diagnosis: ${scan.diseaseName}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            if (!scan.farmerFeedback.isNullOrBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Farmer's Concern:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        Text(scan.farmerFeedback, fontSize = 12.sp, color = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = noteInput,
                onValueChange = {
                    noteInput = it
                    savedNote = false
                },
                label = { Text("Agronomist Second Opinion / Corrective Note", fontSize = 11.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (savedNote) {
                    Text("Advisory saved ✓", fontSize = 11.sp, color = Color(0xFF16A34A), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    onClick = {
                        onAddNote(noteInput)
                        savedNote = true
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Save Advisory Note", fontSize = 12.sp)
                }
            }
        }
    }
}
