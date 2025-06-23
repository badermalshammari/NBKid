package com.example.androidtemplate.data.dtos


data class CreateChildRequest(
    val name: String,
    val civilId: String,
    val birthday: String, // (yyyy-MM-dd)
    val gender: String    // "ZAIN" or "ZAINAH"
)