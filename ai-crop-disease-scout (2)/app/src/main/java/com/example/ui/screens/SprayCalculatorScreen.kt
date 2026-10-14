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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.AppStrings
import com.example.data.model.AreaUnit
import com.example.data.model.CropType
import com.example.data.model.SafeSprayCalculator
import com.example.ui.theme.AlertAmber
import com.example.ui.theme.AlertRed
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.HealthGreen
import com.example.ui.viewmodel.CropScoutViewModel
import java.util.Locale

@Composable
fun SprayCalculatorScreen(
    viewModel: CropScoutViewModel,
    currentLanguage: AppLanguage
) {
    val selectedProduct by viewModel.selectedAgrochemical.collectAsState()
    val areaInput by viewModel.sprayAreaInput.collectAsState()
    val areaUnit by viewModel.sprayAreaUnit.collectAsState()
    val calculationResult by viewModel.sprayCalculationResult.collectAsState()
    val selectedCrop by viewModel.selectedCrop.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B4332)),
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
                            .background(Color(0xFF2D6A4F)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = "Safe Spray Calculator",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = if (currentLanguage == AppLanguage.URDU) "محفوظ اسپرے و زرعی دوا کیلکولیٹر"
                            else if (currentLanguage == AppLanguage.SINDHI) "محفوظ اسپري ۽ زرعي دوائن جو ڳڻپيندڙ"
                            else "Safe Pesticide & Spray Calculator",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = if (currentLanguage == AppLanguage.URDU) "مستند لیبل کے مطابق محفوظ مقدار اور حفاظتی اقدامات"
                            else if (currentLanguage == AppLanguage.SINDHI) "ليبل مطابق محفوظ مقدار ۽ بچاءَ جا اپاءَ"
                            else "Verified label dosages, PPE requirements & PHI/REI safety guardrails",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFB7E4C7)
                        )
                    }
                }
            }
        }

        // Warning Callout
        item {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFF3CD),
                border = androidx.compose.foundation.BorderStroke(1.dp, AlertAmber),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Safety Caution",
                        tint = AlertAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.URDU) "انتباہ: یہ حساب صرف سرکاری طور پر رجسٹرڈ پروڈکٹ لیبل کے مطابق ہے۔ کبھی بھی کیمیائی خوراک اندازے سے نہ بڑھائیں اور غیر تصدیق شدہ مرکبات مکس نہ کریں۔"
                        else if (currentLanguage == AppLanguage.SINDHI) "چتاءُ: هيءَ ڳڻپ صرف سرڪاري رجسٽرڊ ليبل مطابق آهي. ڪڏهن به دوائن جي مقدار پنهنجي مرضيءَ سان نه وڌايو."
                        else "Zero Invention Safety Policy: Dosages calculated strictly from verified registered product labels. Never increase doses or mix unverified chemicals.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF664D03),
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Step 1: Area Input Section
        item {
            ElevatedCard(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (currentLanguage == AppLanguage.URDU) "1. رقبہ اور اکائی منتخب کریں"
                        else if (currentLanguage == AppLanguage.SINDHI) "1. ايراضي ۽ ماپ چونڊيو"
                        else "1. Enter Field Area & Measurement Unit",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = areaInput,
                        onValueChange = { viewModel.setSprayAreaInput(it) },
                        label = { Text("Field Area") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("spray_area_input"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(AreaUnit.entries) { unit ->
                            val isSelected = unit == areaUnit
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.setSprayAreaUnit(unit) },
                                label = { Text(unit.label, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }

        // Step 2: Choose Verified Agrochemical Label
        item {
            ElevatedCard(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (currentLanguage == AppLanguage.URDU) "2. مستند زرعی دوا منتخب کریں"
                        else if (currentLanguage == AppLanguage.SINDHI) "2. تصديق ٿيل زرعي دوا چونڊيو"
                        else "2. Select Verified Registered Product Label",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(SafeSprayCalculator.verifiedProducts) { prod ->
                            val isSelected = prod.id == selectedProduct.id
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.setSelectedAgrochemical(prod) },
                                label = {
                                    Column {
                                        Text(prod.tradeName, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp)
                                        Text(prod.activeIngredient, fontSize = 10.sp, color = if (isSelected) Color.White.copy(0.85f) else Color.Gray)
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }

        // Step 3: Computed Doses & Precautions Card
        item {
            calculationResult?.let { res ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CALCULATED SPRAY DOSAGE",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = HealthGreen
                            ) {
                                Text(
                                    text = "VERIFIED LABEL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Quantity callout
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Product Required", fontSize = 11.sp, color = Color.Gray)
                                    Text(
                                        text = "${String.format(Locale.US, "%.1f", res.calculatedChemicalQty)} ${res.chemicalUnit}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text("for ${String.format(Locale.US, "%.2f", res.totalAreaAcres)} acres", fontSize = 10.sp, color = Color.Gray)
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Water Volume", fontSize = 11.sp, color = Color.Gray)
                                    Text(
                                        text = "${String.format(Locale.US, "%.0f", res.calculatedWaterVolumeLiters)} Liters",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF1B4332)
                                    )
                                    Text("clean spray tank water", fontSize = 10.sp, color = Color.Gray)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // PHI and REI Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFE9F5ED),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("Pre-Harvest Interval (PHI)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B4332))
                                    Text("${res.phiDays} Days Wait", fontSize = 14.sp, fontWeight = FontWeight.Black, color = AlertRed)
                                    Text("Do not harvest or consume crop before this period.", fontSize = 9.sp, color = Color.DarkGray)
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFE9F5ED),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text("Re-Entry Interval (REI)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B4332))
                                    Text("${res.reiHours} Hours", fontSize = 14.sp, fontWeight = FontWeight.Black, color = AlertAmber)
                                    Text("Keep humans & cattle away from field.", fontSize = 9.sp, color = Color.DarkGray)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Required PPE Checklist
                        Text("Mandatory Personal Protective Equipment (PPE):", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        res.ppeGear.forEach { ppe ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = HealthGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(ppe, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Forbidden Tank Mixes
                        Text("PROHIBITED TANK MIXES (Danger):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = AlertRed)
                        Spacer(modifier = Modifier.height(4.dp))
                        res.forbiddenMixes.forEach { mix ->
                            Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(vertical = 2.dp)) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = AlertRed, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(mix, fontSize = 11.sp, color = Color(0xFF842029))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Authority Citation: ${res.product.verifiedAuthorityCitation}",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}
