package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.KarmaViewModel

@Composable
fun McqScreen(
    viewModel: KarmaViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val filteredMcqs = viewModel.getFilteredMcqs()

    val mcqUnits = listOf(
        "All Units",
        "Organic Chemistry",
        "Inorganic Chemistry",
        "Physical Chemistry",
        "Spectroscopy",
        "Pericyclic Reactions",
        "Coordination Chemistry"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Unit Filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            mcqUnits.forEach { unit ->
                val isSelected = uiState.selectedMcqUnit.equals(unit, ignoreCase = true)
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setMcqUnitFilter(unit) },
                    label = { Text(unit) },
                    modifier = Modifier.testTag("mcq_filter_$unit")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredMcqs.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = null, tint = Slate500, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No questions found for this unit filter.", fontWeight = FontWeight.Bold)
                    Button(
                        onClick = { viewModel.setMcqUnitFilter("All Units") },
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text("Show All Questions")
                    }
                }
            }
            return
        }

        val safeIndex = uiState.currentMcqIndex.coerceIn(0, filteredMcqs.lastIndex)
        val question = filteredMcqs[safeIndex]
        val progress = (safeIndex + 1).toFloat() / filteredMcqs.size

        // Header with Progress and Score
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = NavyDark),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Question ${safeIndex + 1} of ${filteredMcqs.size}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = question.unit,
                            color = AzureSecondary,
                            fontSize = 12.sp
                        )
                    }

                    // Score pill
                    Surface(
                        color = Color(0x33FFFFFF),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4ADE80), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${uiState.mcqCorrectCount}/${uiState.mcqAttemptedCount} Correct",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = ScholarGold,
                    trackColor = Color(0x33FFFFFF),
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Question Statement Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = ScholarGoldLight,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = question.difficulty,
                            color = Color(0xFF92400E),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Text(
                        text = "CSIR-NET Practice",
                        color = Slate500,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = question.question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 23.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Options List
        question.options.forEachIndexed { index, optionText ->
            val isSelected = uiState.selectedOptionIndex == index
            val isAnswerChecked = uiState.isMcqAnswerChecked
            val isCorrectAnswer = index == question.answerIndex

            val backgroundColor = when {
                isAnswerChecked && isCorrectAnswer -> CorrectGreenBg
                isAnswerChecked && isSelected && !isCorrectAnswer -> WrongRedBg
                isSelected -> AzureLight
                else -> MaterialTheme.colorScheme.surface
            }

            val borderColor = when {
                isAnswerChecked && isCorrectAnswer -> CorrectGreen
                isAnswerChecked && isSelected && !isCorrectAnswer -> WrongRed
                isSelected -> AzureSecondary
                else -> Slate200
            }

            val letter = when (index) {
                0 -> "A"
                1 -> "B"
                2 -> "C"
                else -> "D"
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable(enabled = !isAnswerChecked) {
                        viewModel.selectMcqOption(index)
                    }
                    .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
                    .testTag("option_${letter}"),
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isAnswerChecked && isCorrectAnswer -> CorrectGreen
                                    isAnswerChecked && isSelected && !isCorrectAnswer -> WrongRed
                                    isSelected -> AzureSecondary
                                    else -> Slate100
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = letter,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected || (isAnswerChecked && isCorrectAnswer)) Color.White else Slate700,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = optionText,
                        fontSize = 14.5.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    if (isAnswerChecked) {
                        if (isCorrectAnswer) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = CorrectGreen)
                        } else if (isSelected) {
                            Icon(Icons.Default.Cancel, contentDescription = "Wrong", tint = WrongRed)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Explanation Box (Revealed when checked)
        AnimatedVisibility(
            visible = uiState.isMcqAnswerChecked,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            val isUserCorrect = uiState.selectedOptionIndex == question.answerIndex

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isUserCorrect) Color(0xFFF0FDF4) else Color(0xFFFEF2F2)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (isUserCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = null,
                            tint = if (isUserCorrect) CorrectGreen else WrongRed
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isUserCorrect) "Correct! +${if (question.difficulty.contains("4M")) "4" else "2"} Marks" else "Incorrect! Correct is Option ${when (question.answerIndex) { 0 -> "A"; 1 -> "B"; 2 -> "C"; else -> "D" }}",
                            fontWeight = FontWeight.Bold,
                            color = if (isUserCorrect) CorrectGreen else WrongRed,
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Concept Breakdown / विस्तृत व्याख्या:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = question.explanation,
                        fontSize = 13.5.sp,
                        color = Slate700,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "हिंदी में समझिए:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = AzureSecondary
                    )
                    Text(
                        text = question.hindiExplanation,
                        fontSize = 13.sp,
                        color = Slate700,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.previousMcqQuestion() },
                modifier = Modifier
                    .weight(0.8f)
                    .testTag("prev_mcq_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Prev")
            }

            if (!uiState.isMcqAnswerChecked) {
                Button(
                    onClick = { viewModel.checkMcqAnswer() },
                    enabled = uiState.selectedOptionIndex >= 0,
                    modifier = Modifier
                        .weight(1.4f)
                        .testTag("check_answer_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Check Answer", fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = { viewModel.nextMcqQuestion() },
                    modifier = Modifier
                        .weight(1.4f)
                        .testTag("next_mcq_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = AzureSecondary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Next Question", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { viewModel.resetMcqQuiz() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .testTag("reset_quiz_btn")
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Reset Session Score", color = Slate500, fontSize = 12.sp)
        }
    }
}
