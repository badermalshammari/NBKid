package com.example.androidtemplate.data.requests

import com.example.androidtemplate.data.dtos.Parent

data class AuthResponse(
    val token: String,
    val parent: Parent
)