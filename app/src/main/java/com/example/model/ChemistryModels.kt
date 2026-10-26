package com.example.model

data class TopicNote(
    val id: String,
    val unit: String,
    val title: String,
    val hindiTitle: String,
    val summary: String,
    val body: String,
    val keyFormulas: List<String> = emptyList(),
    val examTips: List<String> = emptyList(),
    val isBookmarked: Boolean = false
)

data class McqQuestion(
    val id: String,
    val unit: String,
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    val explanation: String,
    val hindiExplanation: String,
    val difficulty: String = "Part B (2M)"
)

data class FormulaCard(
    val id: String,
    val unit: String,
    val title: String,
    val formula: String,
    val description: String,
    val applicability: String
)

data class VideoResource(
    val title: String,
    val subject: String,
    val description: String,
    val url: String,
    val badge: String
)
