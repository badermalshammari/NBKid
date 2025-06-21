package com.example.androidtemplate.data.requests


data class RegisterRequest(
    val username: String,
    val password: String,
    val phoneNumber: String,
    val name: String,
)