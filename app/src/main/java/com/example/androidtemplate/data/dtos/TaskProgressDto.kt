package com.example.androidtemplate.data.dtos

data class TaskProgressDto(
    val taskProgressId: Long,
    val taskId: Long,
    val childId: Long,
    val status: String,
    val progressPercentage: Int,
    val earnedPoints: Int,
    val earnedGems: Int,
    val completedAt: String?
)