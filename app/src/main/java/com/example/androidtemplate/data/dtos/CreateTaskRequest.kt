package com.example.androidtemplate.data.dtos

enum class TaskType{
    TASK, VIDEO, QUIZ
}

data class CreateTaskRequest(
    val parentId: Long,
    val childId: Long,
    val title: String,
    val description: String,
    val type: TaskType,
    val gems: Int? = null,
    val educationalContentId: Long? = null
)