package com.example.androidtemplate.data.dtos

data class KidTask(
    val taskId: Long,
    val title: String,
    val description: String,
    val type: TaskType,
    val points: Int?,
    val gems: Int
)

enum class TaskType {
    TASK, QUIZ, VIDEO
}