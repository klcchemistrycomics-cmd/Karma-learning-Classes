package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.util.PdfExporter
import com.example.viewmodel.KarmaViewModel

@Composable
fun AiPlannerScreen(
    viewModel: KarmaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val quickTopics = listOf(
        "Aromaticity & Hückel Rule",
        "Crystal Field Splitting",
        "Chemical Kinetics",
        "1H & 13C NMR",
        "18-Electron Rule",
        "Woodward-Hoffmann Rules",
        "Gibbs Energy & Maxwell"
    )

    val modes = listOf("Comprehensive Notes", "5-Question Drill", "30-Day Study Plan")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = NavyDark),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ScholarGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "AI Study Generator & Planner",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Generate custom revision guides, 5-question conceptual practice drills, or 30-day exam strategies for any CSIR-NET chemistry topic.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            "Enter Topic / विषय दर्ज करें:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Slate700
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = uiState.aiTopic,
            onValueChange = { viewModel.setAiTopic(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("ai_topic_input"),
            placeholder = { Text("e.g. Asymmetric Synthesis, Orgel Diagrams...") },
            trailingIcon = {
                if (uiState.aiTopic.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setAiTopic("") }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Quick suggestions
        Text("Quick Suggestion / सुझाई गई अवधारणाएँ:", fontSize = 12.sp, color = Slate500)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            quickTopics.forEach { topic ->
                SuggestionChip(
                    onClick = { viewModel.setAiTopic(topic) },
                    label = { Text(topic, fontSize = 11.5.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mode Selector
        Text("Generation Format:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Slate700)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            modes.forEach { mode ->
                val isSelected = uiState.aiMode == mode
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setAiMode(mode) },
                    label = { Text(mode, fontSize = 12.sp) },
                    modifier = Modifier.testTag("ai_mode_${mode.replace(" ", "_")}")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Generate Button
        Button(
            onClick = { viewModel.generateAiContent() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("generate_study_guide_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Bolt, contentDescription = null, tint = ScholarGold)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Generate Study Framework", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Generated Output Card
        AnimatedVisibility(visible = uiState.aiGeneratedContent.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Generated ${uiState.aiMode}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = NavyPrimary
                        )

                        Row {
                            IconButton(onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Study Guide", uiState.aiGeneratedContent)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                            }) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Slate500, modifier = Modifier.size(20.dp))
                            }

                            IconButton(onClick = {
                                val result = PdfExporter.exportFormulasToPdf(context, uiState.aiGeneratedContent)
                                result.fold(
                                    onSuccess = { msg -> Toast.makeText(context, msg, Toast.LENGTH_LONG).show() },
                                    onFailure = { err -> Toast.makeText(context, "Error: ${err.message}", Toast.LENGTH_SHORT).show() }
                                )
                            }) {
                                Icon(Icons.Default.Download, contentDescription = "PDF", tint = AzureSecondary, modifier = Modifier.size(20.dp))
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = uiState.aiGeneratedContent,
                        fontSize = 13.5.sp,
                        lineHeight = 21.sp,
                        color = Slate900
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate100),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    "Free Educational Framework Notice / छात्र परामर्श:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Slate700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "All generated notes and drills use structured conceptual frameworks. Always cross-verify reaction mechanisms with Clayden, Huheey, and official CSIR-NET syllabus keys before examination.",
                    fontSize = 11.5.sp,
                    color = Slate500,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
