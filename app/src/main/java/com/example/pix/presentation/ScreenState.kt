package com.example.pix.presentation

sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>
    data class Success<T>(val data: T) : ScreenState<T>
    data class Failure(val message: String?) : ScreenState<Nothing>
}