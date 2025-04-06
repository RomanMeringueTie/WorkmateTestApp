package com.example.pix.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed interface Route {
    @Serializable
    data object Grid : Route

    @Serializable
    data class Details(val picture: String) : Route
}