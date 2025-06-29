package com.example.androidtemplate.data.requests

import java.math.BigDecimal

data class TransferRequest(
    val fromAccountNumber: String,
    val toAccountNumber: String,
    val amount: BigDecimal,
    val description: String?
)