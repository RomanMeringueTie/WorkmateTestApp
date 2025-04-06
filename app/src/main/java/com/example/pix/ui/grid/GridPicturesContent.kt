package com.example.pix.ui.grid

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.pix.data.flickr.mapper.toOriginalUrl
import com.example.pix.domain.entity.Picture
import com.example.pix.ui.theme.Purple40
import com.example.pix.ui.theme.Purple80
import kotlinx.serialization.json.Json

@Composable
fun GridPicturesContent(
    modifier: Modifier, list: List<Picture>, onClick: (String) -> Unit
) {

    Column(modifier = modifier.padding(top = 10.dp)) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(list) { picture: Picture ->
                ImageCell(
                    modifier = Modifier, picture = picture, onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun ImageCell(
    modifier: Modifier, picture: Picture, onClick: (String) -> Unit
) {

    var fontSize by remember { mutableStateOf(12.sp) }

    Card(
        modifier = modifier
            .padding(10.dp)
            .clickable { onClick(Json.encodeToString(picture.toOriginalUrl())) },
        border = BorderStroke(
            width = 1.dp, color = Purple80
        )
    ) {
        AsyncImage(
            model = picture.url,
            contentDescription = picture.title,
            modifier = Modifier
                .height(60.dp)
                .padding(top = 10.dp)
                .fillMaxSize(),
            alignment = Alignment.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            text = picture.title,
            color = Purple40,
            maxLines = 1,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            onTextLayout = {
                if (it.multiParagraph.didExceedMaxLines) {
                    fontSize = fontSize * .9F
                }
            }
        )
    }
}