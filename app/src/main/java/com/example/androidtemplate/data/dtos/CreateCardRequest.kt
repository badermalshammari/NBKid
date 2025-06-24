package com.example.androidtemplate.data.dtos

data class CreateCardRequest(
    val ownerId: Long,
    val isParentCard: Boolean
)