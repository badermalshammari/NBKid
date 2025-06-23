package com.example.androidtemplate.data.dtos

data class LeaderboardEntryDto(
    val childId: Long,
    val name: String,
    val avatar: String,
    val points: Int
)