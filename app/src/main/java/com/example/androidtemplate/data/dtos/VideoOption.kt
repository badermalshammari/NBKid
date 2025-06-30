package com.example.androidtemplate.data.dtos

data class VideoOption(
    val id: Long,
    val title: String,
    val description: String,
    val videoFilename: String,
    val coverPicture: String,
    val time: Int,
    val youtubeUrl: String? = null)