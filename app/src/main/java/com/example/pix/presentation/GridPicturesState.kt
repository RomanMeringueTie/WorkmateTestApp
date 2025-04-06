package com.example.pix.presentation

import com.example.pix.domain.entity.Picture

data class GridPicturesState(
    val state: ScreenState<List<Picture>> = ScreenState.Loading,
    val isRefreshing: Boolean = true
)