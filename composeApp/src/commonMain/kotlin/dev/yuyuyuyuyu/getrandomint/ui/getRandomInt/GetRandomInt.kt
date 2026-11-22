package dev.yuyuyuyuyu.getrandomint.ui.getRandomInt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.yuyuyuyuyu.getrandomint.ui.components.CopyToClipboardButton
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GetRandomInt(
    modifier: Modifier = Modifier,
    viewModel: GetRandomIntViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = uiState.min?.toString() ?: "",
            onValueChange = viewModel::onMinChange,
            label = { Text("Min") },
        )

        TextField(
            value = uiState.max?.toString() ?: "",
            onValueChange = viewModel::onMaxChange,
            label = { Text("Max") },
        )

        Text(text = uiState.randomInt?.toString() ?: "")

        CopyToClipboardButton(
            textToCopy = uiState.randomInt?.toString() ?: "",
            enabled = uiState.randomInt != null,
        )
    }
}
