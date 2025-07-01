package com.example.androidtemplate.data.dtos


data class CreateChildRequest(
    val name: String,
    val civilId: String,
    val birthday: String,
    val gender: String,
    val cardDesign: String
)