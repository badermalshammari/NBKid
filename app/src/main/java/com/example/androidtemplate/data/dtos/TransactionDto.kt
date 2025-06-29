package com.example.androidtemplate.data.dtos

import java.math.BigDecimal

data class TransactionDto(
    val transactionId: Long,
    val fromAccountNumber: String,
    val toAccountNumber: String,
    val amount: BigDecimal,
    val timestamp: String,
    val description: String?
)