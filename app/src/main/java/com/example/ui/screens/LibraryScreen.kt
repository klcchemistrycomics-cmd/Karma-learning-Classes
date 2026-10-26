package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TopicNote
import com.example.ui.theme.*
import com.example.util.PdfExporter
import com.example.viewmodel.KarmaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: KarmaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val filteredNotes = viewModel.getFilteredNotes()

    var activeReadingNote by remember { mutableStateOf<TopicNote?>(null) }

    val allUnits = listOf(
        "All Units",
        "Organic Chemistry",
        "Inorganic Chemistry",
        "Physical Chemistry",
        "Spectroscopy",
        "Pericyclic Reactions",
        "Coordination Chemistry",
        "Thermodynamics"
    )

    if (activeReadingNote != null) {
        NoteDetailSheet(
            note = activeReadingNote!!,
            onDismiss = { activeReadingNote = null },
            onBookmarkToggle = { viewModel.toggleBookmarkNote(activeReadingNote!!.id) },
            onExportPdf = {
                val result = PdfExporter.exportNoteToPdf(context, activeReadingNote!!)
                result.fold(
                    onSuccess = { msg -> Toast.makeText(context, msg, Toast.LENGTH_LONG).show() },
                    onFailure = { err -> Toast.makeText(context, "Export failed: ${err.localizedMessage}", Toast.LENGTH_SHORT).show() }
                )
            },
            onShare = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, activeReadingNote!!.title)
                    putExtra(Intent.EXTRA_TEXT, "${activeReadingNote!!.title}\n\n${activeReadingNote!!.body}\n\nShared via Karma Learning Classes CSIR-NET App")
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Study Note"))
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = uiState.noteSearchQuery,
            onValueChange = { viewModel.setNoteSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("note_search_input"),
            placeholder = { Text("Search topics, formulas, Hindi concepts...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                if (uiState.noteSearchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { viewModel.setNoteSearchQuery("") },
                        modifier = Modifier.testTag("clear_note_search")
                    ) {
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
            // Bookmarks only toggle chip
            FilterChip(
                selected = uiState.onlyBookmarkedNotes,
                onClick = { viewModel.toggleOnlyBookmarked() },
                label = { Text("Saved ★") },
                leadingIcon = {
                    Icon(
                        if (uiState.onlyBookmarkedNotes) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                modifier = Modifier.testTag("filter_saved_chip")
            )

            allUnits.forEach { unit ->
                val isSelected = uiState.selectedUnitFilter.equals(unit, ignoreCase = true)
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setSelectedUnitFilter(unit) },
                    label = { Text(unit) },
                    modifier = Modifier.testTag("filter_chip_$unit")
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Header info count
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${filteredNotes.size} Topics Available",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate500
            )
            Text(
                text = "Bilingual Notes (Hindi + Eng)",
                fontSize = 12.sp,
                color = AzureSecondary,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredNotes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.SearchOff,
                        contentDescription = null,
                        tint = Slate500,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No topics found for \"${uiState.noteSearchQuery}\"",
                        fontWeight = FontWeight.SemiBold,
                        color = Slate700
                    )
                    Text(
                        "Try searching for Aromaticity, CFT, Kinetics, NMR, or clear filters.",
                        fontSize = 12.sp,
                        color = Slate500
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filteredNotes, key = { it.id }) { note ->
                    NoteCardItem(
                        note = note,
                        onClick = { activeReadingNote = note },
                        onBookmarkToggle = { viewModel.toggleBookmarkNote(note.id) },
                        onExportPdf = {
                            val result = PdfExporter.exportNoteToPdf(context, note)
                            result.fold(
                                onSuccess = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                                onFailure = { err -> Toast.makeText(context, "Error: ${err.message}", Toast.LENGTH_SHORT).show() }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCardItem(
    note: TopicNote,
    onClick: () -> Unit,
    onBookmarkToggle: () -> Unit,
    onExportPdf: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("note_card_${note.id}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Unit badge
                Surface(
                    color = AzureLight,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = note.unit,
                        color = NavyPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBookmarkToggle,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("bookmark_${note.id}")
                    ) {
                        Icon(
                            if (note.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save Note",
                            tint = if (note.isBookmarked) ScholarGold else Slate500,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = onExportPdf,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("pdf_${note.id}")
                    ) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Export PDF",
                            tint = AzureSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = note.hindiTitle,
                fontSize = 13.sp,
                color = AzureSecondary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = note.summary,
                fontSize = 13.sp,
                color = Slate500,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${note.keyFormulas.size} Key Formulas • ${note.examTips.size} Tips",
                    fontSize = 11.sp,
                    color = Slate500,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Read Note",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = NavyPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailSheet(
    note: TopicNote,
    onDismiss: () -> Unit,
    onBookmarkToggle: () -> Unit,
    onExportPdf: () -> Unit,
    onShare: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = AzureLight,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = note.unit,
                        color = NavyPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Row {
                    IconButton(onClick = onBookmarkToggle) {
                        Icon(
                            if (note.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (note.isBookmarked) ScholarGold else Slate500
                        )
                    }
                    IconButton(onClick = onShare) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = AzureSecondary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = note.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = note.hindiTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = AzureSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onExportPdf,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("export_pdf_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Export PDF")
                }

                OutlinedButton(
                    onClick = onShare,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share Note")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // Body text
            Text(
                text = note.body,
                fontSize = 14.5.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Key formulas section
            if (note.keyFormulas.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Functions, contentDescription = null, tint = AzureSecondary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Key Formulas & Relationships / मुख्य सूत्र",
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        note.keyFormulas.forEach { kf ->
                            Text(
                                text = "• $kf",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate900,
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            // Exam tips section
            if (note.examTips.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ScholarGoldLight),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = ScholarGold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "CSIR-NET Exam Traps / परीक्षा युक्तियाँ",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF78350F),
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        note.examTips.forEach { tip ->
                            Text(
                                text = "★ $tip",
                                fontSize = 13.sp,
                                color = Color(0xFF451A03),
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Karma Learning Classes • Verified Chemistry Study Series",
                fontSize = 11.sp,
                color = Slate500,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
