package com.example.androidtemplate.data.dtos

import java.math.BigDecimal

data class BankCardDto(
    val cardId: Long,
    val cardNumber: String,
    val accountNumber: Long,
    val cardHolderName: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val cvv: String,
    val balance: BigDecimal,
    val isParentCard: Boolean,
    val cardDesign: String,
    val childId: Long?,
    val parentId: Long?,
    val isActive: Boolean
)