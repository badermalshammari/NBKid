package com.example.androidtemplate.data.dtos

data class ChildStoreItemDto(
    val id: Long,
    var isHidden: Boolean,
    val wishList: Boolean,
    val globalItem: GlobalItemDto
)