package com.example.androidtemplate.data.dtos

data class KidTaskDto(
    val taskId: Long,
    val title: String,
    val description: String,
    val type: String,
    val points: Int?,
    val gems: Int,
    val childName: String,
    val status: String
)