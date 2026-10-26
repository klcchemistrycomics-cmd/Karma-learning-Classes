package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.ChemistryRepository
import com.example.model.FormulaCard
import com.example.model.McqQuestion
import com.example.model.TopicNote
import com.example.model.VideoResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AppNavTab(val label: String) {
    LIBRARY("Library"),
    MCQ("MCQ Practice"),
    FORMULAS("Formulas"),
    AI_PLANNER("AI Generator"),
    RESOURCES("Videos & Info")
}

data class KarmaUiState(
    val currentTab: AppNavTab = AppNavTab.LIBRARY,
    val notes: List<TopicNote> = ChemistryRepository.sampleNotes,
    val selectedNoteId: String = ChemistryRepository.sampleNotes.first().id,
    val noteSearchQuery: String = "",
    val selectedUnitFilter: String = "All Units",
    val onlyBookmarkedNotes: Boolean = false,
    // MCQ State
    val mcqQuestions: List<McqQuestion> = ChemistryRepository.sampleMcqs,
    val selectedMcqUnit: String = "All Units",
    val currentMcqIndex: Int = 0,
    val selectedOptionIndex: Int = -1,
    val isMcqAnswerChecked: Boolean = false,
    val mcqCorrectCount: Int = 0,
    val mcqAttemptedCount: Int = 0,
    // Formula State
    val formulas: List<FormulaCard> = ChemistryRepository.sampleFormulas,
    val formulaSearchQuery: String = "",
    val selectedFormulaUnit: String = "All Units",
    // AI Planner State
    val aiTopic: String = "Aromaticity & Hückel Rule",
    val aiMode: String = "Comprehensive Notes",
    val aiGeneratedContent: String = "",
    val isAiGenerating: Boolean = false,
    // Toast / Feedback message
    val userMessage: String? = null
)

class KarmaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(KarmaUiState())
    val uiState: StateFlow<KarmaUiState> = _uiState.asStateFlow()

    fun setTab(tab: AppNavTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectNote(note: TopicNote) {
        _uiState.update { it.copy(selectedNoteId = note.id) }
    }

    fun setNoteSearchQuery(query: String) {
        _uiState.update { it.copy(noteSearchQuery = query) }
    }

    fun setSelectedUnitFilter(unit: String) {
        _uiState.update { it.copy(selectedUnitFilter = unit) }
    }

    fun toggleOnlyBookmarked() {
        _uiState.update { it.copy(onlyBookmarkedNotes = !it.onlyBookmarkedNotes) }
    }

    fun toggleBookmarkNote(noteId: String) {
        _uiState.update { state ->
            val updated = state.notes.map { note ->
                if (note.id == noteId) note.copy(isBookmarked = !note.isBookmarked) else note
            }
            state.copy(notes = updated)
        }
    }

    // MCQ Controls
    fun setMcqUnitFilter(unit: String) {
        _uiState.update { state ->
            state.copy(
                selectedMcqUnit = unit,
                currentMcqIndex = 0,
                selectedOptionIndex = -1,
                isMcqAnswerChecked = false
            )
        }
    }

    fun selectMcqOption(index: Int) {
        if (!_uiState.value.isMcqAnswerChecked) {
            _uiState.update { it.copy(selectedOptionIndex = index) }
        }
    }

    fun checkMcqAnswer() {
        val state = _uiState.value
        val filtered = getFilteredMcqs()
        if (state.selectedOptionIndex >= 0 && !state.isMcqAnswerChecked && filtered.isNotEmpty()) {
            val currentQ = filtered[state.currentMcqIndex.coerceIn(0, filtered.lastIndex)]
            val isCorrect = state.selectedOptionIndex == currentQ.answerIndex
            _uiState.update {
                it.copy(
                    isMcqAnswerChecked = true,
                    mcqCorrectCount = if (isCorrect) it.mcqCorrectCount + 1 else it.mcqCorrectCount,
                    mcqAttemptedCount = it.mcqAttemptedCount + 1
                )
            }
        }
    }

    fun nextMcqQuestion() {
        val filtered = getFilteredMcqs()
        if (filtered.isNotEmpty()) {
            _uiState.update {
                val nextIndex = (it.currentMcqIndex + 1) % filtered.size
                it.copy(
                    currentMcqIndex = nextIndex,
                    selectedOptionIndex = -1,
                    isMcqAnswerChecked = false
                )
            }
        }
    }

    fun previousMcqQuestion() {
        val filtered = getFilteredMcqs()
        if (filtered.isNotEmpty()) {
            _uiState.update {
                val prevIndex = if (it.currentMcqIndex - 1 < 0) filtered.size - 1 else it.currentMcqIndex - 1
                it.copy(
                    currentMcqIndex = prevIndex,
                    selectedOptionIndex = -1,
                    isMcqAnswerChecked = false
                )
            }
        }
    }

    fun resetMcqQuiz() {
        _uiState.update {
            it.copy(
                currentMcqIndex = 0,
                selectedOptionIndex = -1,
                isMcqAnswerChecked = false,
                mcqCorrectCount = 0,
                mcqAttemptedCount = 0
            )
        }
    }

    // Formulas
    fun setFormulaSearchQuery(query: String) {
        _uiState.update { it.copy(formulaSearchQuery = query) }
    }

    fun setFormulaUnitFilter(unit: String) {
        _uiState.update { it.copy(selectedFormulaUnit = unit) }
    }

    // AI Generator
    fun setAiTopic(topic: String) {
        _uiState.update { it.copy(aiTopic = topic) }
    }

    fun setAiMode(mode: String) {
        _uiState.update { it.copy(aiMode = mode) }
    }

    fun generateAiContent() {
        val topic = _uiState.value.aiTopic.ifBlank { "Chemical Science" }
        val mode = _uiState.value.aiMode

        val result = when (mode) {
            "Comprehensive Notes" -> """
=== CSIR-NET REVISION GUIDE: $topic ===
(Karma Learning Classes • Study Framework)

1. Core Definition & Principle / मुख्य परिभाषा एवं सिद्धांत:
• $topic encompasses fundamental concepts essential for CSIR-NET Part B and Part C problems.
• Focus on underlying molecular orbital theory, thermodynamic parameters, and electronic symmetry principles.

2. Essential Chemical Rules & Models / महत्वपूर्ण नियम एवं मॉडल:
• Structural criteria: Symmetry elements, hybridization, orbital overlap, and electronic configurations.
• Spectroscopic / Thermodynamic fingerprint: Characteristic NMR chemical shifts, IR bands, or ΔG / CFSE values.
• Stereochemical outcome: Retention, inversion, syn/anti addition, or conrotatory/disrotatory paths.

3. Typical CSIR-NET Exam Traps / सामान्य परीक्षा भ्रांतियाँ:
• Do not confuse kinetic product (formed fastest, lower activation barrier) with thermodynamic product (most stable, lower ΔG).
• Always verify solvent polarity and counterion effects in polar mechanisms.
• Look for steric hindrance in crowded organometallic and asymmetric reactions.

4. Quick Self-Test Drill:
• Write down the rate law or electron-count equation related to $topic.
• Draw the 3D transition state and identify any orbital symmetry forbidden pathways.
• Solve at least 3 previous year questions (PYQs) from 2018-2024 on this concept.
""".trimIndent()

            "5-Question Drill" -> """
=== 5-QUESTION CONCEPTUAL DRILL: $topic ===
(Karma Learning Classes • Practice Drill)

Q1. Under standard CSIR-NET examination conditions, the primary governing factor in $topic is:
A) Steric crowding only
B) Orbital symmetry and thermodynamic stability
C) Reaction vessel volume
D) Solvent boiling point
-> Correct Concept: Orbital symmetry and thermodynamic stability dictate reaction paths.

Q2. What is the characteristic sign or value associated with a favorable process in $topic?
A) ΔG > 0
B) ΔG < 0
C) Rate constant k = 0
D) Inactive transition state
-> Correct Concept: Spontaneous processes require ΔG < 0 under constant T & P.

Q3. In spectroscopy or electronic analysis of $topic, which technique provides definitive proof?
A) Melting point determination only
B) 1D/2D NMR and Mass Spectrometry
C) Visual color inspection
D) Simple filtration
-> Correct Concept: High-resolution NMR and MS reveal connectivity and isotope distribution.

Q4. How does increasing temperature affect the equilibrium in an exothermic step of $topic?
A) Shifts equilibrium forward
B) Shifts equilibrium backward (decreases K)
C) Has zero effect
D) Doubles catalyst activity
-> Correct Concept: By Le Chatelier's principle and van 't Hoff equation, K decreases as T increases.

Q5. Which common mistake should students avoid when solving Part C problems on $topic?
A) Checking conservation of orbital symmetry
B) Ignoring counterion and stereochemical constraints
C) Calculating total valence electrons
D) Balancing oxidation numbers
-> Correct Concept: Part C problems frequently hide subtle stereochemical constraints.
""".trimIndent()

            else -> """
=== 30-DAY CSIR-NET STUDY STRATEGY: $topic ===
(Karma Learning Classes • Master Preparation Roadmap)

• Week 1 (Days 1–7): Fundamentals & Theory Mastery
  - Study basic definitions, MO diagrams, and structural properties.
  - Review textbook chapters (Clayden for Organic, Huheey/Miessler for Inorganic, Puri-Sharma-Pathania/Atkins for Physical).
  - Draft your own 2-page formula cheat sheet.

• Week 2 (Days 8–14): Part B (2-Mark) Direct Problems
  - Practice 50+ memory and formula-based questions.
  - Focus on electron counting, term symbols, rate laws, and Woodward-Fieser calculations.
  - Time yourself: aim for < 1.5 minutes per question.

• Week 3 (Days 15–21): Part C (4-Mark) Multistep Analytics
  - Tackle complex multistep synthesis, spectral problem solving (NMR+IR+MS), and catalytic mechanisms.
  - Formulate concept maps linking reaction intermediates to final stereochemical products.

• Week 4 (Days 22–30): Timed Mock Tests & Error Log
  - Take 3 full-length sectional mocks.
  - Maintain an "Error Notebook" for recurrent slips.
  - Final 3 days: Daily revision of key formulas and quick notes.
""".trimIndent()
        }

        _uiState.update { it.copy(aiGeneratedContent = result) }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }

    fun setUserMessage(msg: String) {
        _uiState.update { it.copy(userMessage = msg) }
    }

    fun getFilteredNotes(): List<TopicNote> {
        val state = _uiState.value
        return state.notes.filter { note ->
            val matchUnit = state.selectedUnitFilter == "All Units" || note.unit.equals(state.selectedUnitFilter, ignoreCase = true)
            val matchQuery = state.noteSearchQuery.isBlank() ||
                    note.title.contains(state.noteSearchQuery, ignoreCase = true) ||
                    note.hindiTitle.contains(state.noteSearchQuery, ignoreCase = true) ||
                    note.summary.contains(state.noteSearchQuery, ignoreCase = true) ||
                    note.body.contains(state.noteSearchQuery, ignoreCase = true)
            val matchBookmark = !state.onlyBookmarkedNotes || note.isBookmarked
            matchUnit && matchQuery && matchBookmark
        }
    }

    fun getFilteredMcqs(): List<McqQuestion> {
        val state = _uiState.value
        return if (state.selectedMcqUnit == "All Units") {
            state.mcqQuestions
        } else {
            state.mcqQuestions.filter { it.unit.equals(state.selectedMcqUnit, ignoreCase = true) }
        }
    }

    fun getFilteredFormulas(): List<FormulaCard> {
        val state = _uiState.value
        return state.formulas.filter { f ->
            val matchUnit = state.selectedFormulaUnit == "All Units" || f.unit.equals(state.selectedFormulaUnit, ignoreCase = true)
            val matchQuery = state.formulaSearchQuery.isBlank() ||
                    f.title.contains(state.formulaSearchQuery, ignoreCase = true) ||
                    f.formula.contains(state.formulaSearchQuery, ignoreCase = true) ||
                    f.description.contains(state.formulaSearchQuery, ignoreCase = true)
            matchUnit && matchQuery
        }
    }
}
