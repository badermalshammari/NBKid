package com.example.androidtemplate.data.dtos


data class WalletResponseDto(
    val walletId: Long? = null,
    val child: ChildDto?= null,
    val pointsBalance: Int,
    val gems: Int
)

data class ChildDto(
    val childId: Long,
    val name: String,
    val avatar: String,
    val stats: String
)