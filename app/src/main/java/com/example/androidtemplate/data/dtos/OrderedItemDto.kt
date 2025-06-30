package com.example.androidtemplate.data.dtos

data class OrderedItemDto(
    val orderId: Long,
    val childName: String,
    val itemName: String,
    val itemImageUrl: String,
    val gemsCost: Int,
    val status: String,
    val orderedAt: String
)