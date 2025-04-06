package com.example.pix.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val url: String,
    val title: String,
)