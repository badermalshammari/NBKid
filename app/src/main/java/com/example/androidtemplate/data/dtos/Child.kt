package com.example.androidtemplate.data.dtos

import com.example.androidtemplate.R

// data/dtos/Child.kt
data class Child(
    val childId: Long,
    val name: String,
    val civilId: String,
    val birthday: String,
    val gender: String,
    val avatar: String,
    val stats: String,
    val avatarResId: Int = R.drawable.zain
)