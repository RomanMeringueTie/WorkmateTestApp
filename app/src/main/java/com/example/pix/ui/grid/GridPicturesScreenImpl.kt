package com.example.pix.ui.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pix.R
import com.example.pix.presentation.GridPicturesState
import com.example.pix.presentation.ScreenState
import com.example.pix.ui.theme.Purple40
import com.example.pix.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridPicturesScreenImpl(
    modifier: Modifier,
    gridPicturesState: GridPicturesState,
    onClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = gridPicturesState.isRefreshing,
        onRefresh = onRetry
    ) {

        Column(modifier = modifier) {

            Box {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.grid_header),
                    color = Purple40,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = onRetry,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Image(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.refresh_icon),
                        colorFilter = ColorFilter.tint(color = Purple80)
                    )
                }
            }

            when (val s = gridPicturesState.state) {
                is ScreenState.Failure -> {
                    GridPicturesFailure(
                        message = s.message,
                        onRetry = onRetry
                    )
                }

                ScreenState.Loading -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        LinearProgressIndicator(
                            color = Purple40
                        )
                    }
                }

                is ScreenState.Success -> {
                    GridPicturesContent(
                        modifier = modifier,
                        list = s.data,
                        onClick = { string: String ->
                            onClick(string)
                        }
                    )
                }
            }
        }
    }
}