package com.example.androidtemplate.data.requests

import java.math.BigDecimal

data class TransferRequest(
    val fromCardNumber: String,
    val toCardNumber: String,
    val amount: BigDecimal,
    val description: String?
)