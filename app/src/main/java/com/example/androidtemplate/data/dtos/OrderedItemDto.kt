package com.example.androidtemplate.data.dtos

data class OrderedItemDto(
    val id: Long,
    val itemName: String,
    val gemsCost: Int,
    val orderedAt: String
)