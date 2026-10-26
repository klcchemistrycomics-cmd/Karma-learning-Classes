package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FormulaCard
import com.example.ui.theme.*
import com.example.util.PdfExporter
import com.example.viewmodel.KarmaViewModel

@Composable
fun FormulaScreen(
    viewModel: KarmaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val filteredFormulas = viewModel.getFilteredFormulas()

    val formulaUnits = listOf(
        "All Units",
        "Physical Chemistry",
        "Inorganic Chemistry",
        "Organic Chemistry",
        "Spectroscopy"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = uiState.formulaSearchQuery,
            onValueChange = { viewModel.setFormulaSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("formula_search_input"),
            placeholder = { Text("Search formulas (Arrhenius, CFSE, Beer-Lambert)...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (uiState.formulaSearchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setFormulaSearchQuery("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Unit Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            formulaUnits.forEach { unit ->
                val isSelected = uiState.selectedFormulaUnit.equals(unit, ignoreCase = true)
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setFormulaUnitFilter(unit) },
                    label = { Text(unit) },
                    modifier = Modifier.testTag("formula_filter_$unit")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Export Handbook Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = NavyLight),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "CSIR-NET Formula Handbook",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        "Download all high-yield formulas as a printable PDF.",
                        color = Color(0xFFBFDBFE),
                        fontSize = 11.5.sp
                    )
                }

                Button(
                    onClick = {
                        val allFormulasText = buildString {
                            appendLine("KARMA LEARNING CLASSES — CSIR-NET FORMULA HANDBOOK")
                            appendLine("Chemical Science High-Yield Formulas & Relationships\n")
                            filteredFormulas.forEachIndexed { i, f ->
                                appendLine("${i + 1}. ${f.title} (${f.unit})")
                                appendLine("Formula: ${f.formula}")
                                appendLine("Description: ${f.description}")
                                appendLine("Applicability: ${f.applicability}\n")
                            }
                        }
                        val result = PdfExporter.exportFormulasToPdf(context, allFormulasText)
                        result.fold(
                            onSuccess = { msg -> Toast.makeText(context, msg, Toast.LENGTH_LONG).show() },
                            onFailure = { err -> Toast.makeText(context, "Error: ${err.message}", Toast.LENGTH_SHORT).show() }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ScholarGold),
                    modifier = Modifier.testTag("export_formula_pdf_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PDF", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredFormulas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No formulas matching \"${uiState.formulaSearchQuery}\"", color = Slate500)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filteredFormulas, key = { it.id }) { item ->
                    FormulaCardItem(formula = item)
                }
            }
        }
    }
}

@Composable
fun FormulaCardItem(formula: FormulaCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("formula_card_${formula.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formula.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    color = AzureLight,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = formula.unit,
                        color = NavyPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Prominent Formula Highlight Box
            Surface(
                color = Slate100,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = formula.formula,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.5.sp,
                    color = NavyPrimary,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formula.description,
                fontSize = 13.sp,
                color = Slate700
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Check, contentDescription = null, tint = AccentTeal, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = formula.applicability,
                    fontSize = 11.5.sp,
                    color = Slate500,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
