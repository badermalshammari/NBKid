package com.example.androidtemplate.data.dtos

data class ChildStoreItem(
    val id: Long,
    val globalItem: GlobalItem,
    val globalItemName: String?,
    val isHidden: Boolean,
    val wishList: Boolean
)

data class GlobalItem(
    val id: Long,
    val name: String,
    val imageUrl: String? = null // in case your backend supports it
)