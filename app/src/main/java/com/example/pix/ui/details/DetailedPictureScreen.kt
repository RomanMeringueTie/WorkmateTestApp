package com.example.pix.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.pix.R
import com.example.pix.domain.entity.Picture
import com.example.pix.presentation.DetailedPictureViewModel
import com.example.pix.ui.theme.Purple40
import com.example.pix.ui.theme.Purple80
import com.example.pix.ui.utils.StableWrapper

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DetailedPictureScreen(
    modifier: Modifier,
    viewModel: DetailedPictureViewModel,
    onBack: () -> Unit,
) {

    val isDownloadSuccess = viewModel.isDownloadSuccess.collectAsState()

    val context = LocalContext.current

    val contextStableWrapper = StableWrapper(context)

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    colorFilter = ColorFilter.tint(color = Purple80),
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = viewModel.picture.title,
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                color = Purple40,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { viewModel.savePicture(context) }) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.download),
                    colorFilter = ColorFilter.tint(color = Purple80),
                )
            }
        }

        ZoomableImage(modifier = Modifier.fillMaxWidth(), picture = viewModel.picture)

        DownloadSuccessIndicator(
            modifier = Modifier,
            isSuccess = isDownloadSuccess.value,
            context = contextStableWrapper
        )

    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun ZoomableImage(modifier: Modifier, picture: Picture) {

    var scale by remember {
        mutableFloatStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )

        }
        AsyncImage(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state),
            model = picture.url,
            contentDescription = picture.title,
        )
    }
}

@Composable
private fun DownloadSuccessIndicator(
    modifier: Modifier,
    isSuccess: Boolean?,
    context: StableWrapper<Context>
) {

    Box(modifier = modifier) {
        when (isSuccess) {
            true -> {
                Toast.makeText(
                    context.value,
                    stringResource(R.string.download_success),
                    Toast.LENGTH_SHORT
                ).show()
            }

            false -> {
                Toast.makeText(
                    context.value,
                    stringResource(R.string.download_error),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            null -> {}
        }
    }

}