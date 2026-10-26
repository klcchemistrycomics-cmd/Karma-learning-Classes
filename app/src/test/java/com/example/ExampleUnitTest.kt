package com.example

import com.example.viewmodel.KarmaViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testNotesFilteringByUnit() {
        val viewModel = KarmaViewModel()
        viewModel.setSelectedUnitFilter("Organic Chemistry")
        val organicNotes = viewModel.getFilteredNotes()

        assertTrue(organicNotes.isNotEmpty())
        assertTrue(organicNotes.all { it.unit.equals("Organic Chemistry", ignoreCase = true) })
    }

    @Test
    fun testNotesSearchQuery() {
        val viewModel = KarmaViewModel()
        viewModel.setNoteSearchQuery("Kinetics")
        val searchResults = viewModel.getFilteredNotes()

        assertTrue(searchResults.isNotEmpty())
        assertTrue(searchResults.any { it.title.contains("Kinetics", ignoreCase = true) })
    }

    @Test
    fun testBookmarkToggle() {
        val viewModel = KarmaViewModel()
        val firstNote = viewModel.uiState.value.notes.first()
        assertFalse(firstNote.isBookmarked)

        viewModel.toggleBookmarkNote(firstNote.id)
        val updatedNote = viewModel.uiState.value.notes.first { it.id == firstNote.id }
        assertTrue(updatedNote.isBookmarked)

        viewModel.toggleOnlyBookmarked()
        val bookmarkedNotes = viewModel.getFilteredNotes()
        assertEquals(1, bookmarkedNotes.size)
    }

    @Test
    fun testMcqAnswerEvaluation() {
        val viewModel = KarmaViewModel()
        val firstMcq = viewModel.getFilteredMcqs().first()

        // Select the correct option
        viewModel.selectMcqOption(firstMcq.answerIndex)
        viewModel.checkMcqAnswer()

        assertTrue(viewModel.uiState.value.isMcqAnswerChecked)
        assertEquals(1, viewModel.uiState.value.mcqCorrectCount)
        assertEquals(1, viewModel.uiState.value.mcqAttemptedCount)
    }

    @Test
    fun testAiGeneratorOutput() {
        val viewModel = KarmaViewModel()
        viewModel.setAiTopic("Pericyclic Reactions")
        viewModel.setAiMode("Comprehensive Notes")
        viewModel.generateAiContent()

        val output = viewModel.uiState.value.aiGeneratedContent
        assertTrue(output.contains("Pericyclic Reactions"))
        assertTrue(output.contains("CSIR-NET REVISION GUIDE"))
    }
}
