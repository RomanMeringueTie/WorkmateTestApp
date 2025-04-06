package com.example.pix.ui.grid

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pix.R
import com.example.pix.ui.theme.Pink40
import com.example.pix.ui.theme.Purple40
import com.example.pix.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridPicturesFailure(message: String?, onRetry: () -> Unit) {
    BasicAlertDialog(onDismissRequest = {}) {
        Card(
            border = BorderStroke(width = 3.dp, color = Purple40),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(R.string.error),
                textAlign = TextAlign.Center,
                color = Purple40,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = message ?: stringResource(R.string.unknown_error),
                textAlign = TextAlign.Center,
                color = Pink40,
            )
            ElevatedButton(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = onRetry,
                colors = ButtonColors(
                    contentColor = Purple40,
                    containerColor = Purple80,
                    disabledContentColor = Purple40,
                    disabledContainerColor = Purple80
                )
            ) {
                Text(
                    text = stringResource(R.string.retry),
                )
            }
        }
    }
}