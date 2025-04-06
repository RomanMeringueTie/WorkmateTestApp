package com.example.pix.ui.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pix.presentation.GridPicturesViewModel

@Composable
fun GridPicturesScreen(
    modifier: Modifier,
    viewModel: GridPicturesViewModel = hiltViewModel(),
    onClick: (String) -> Unit,
) {

    val state = viewModel.state.collectAsState()

    Box(modifier = modifier) {
        GridPicturesScreenImpl(
            modifier = modifier,
            gridPicturesState = state.value,
            onClick = onClick,
            onRetry = { viewModel.onRetry() }
        )
    }
}
