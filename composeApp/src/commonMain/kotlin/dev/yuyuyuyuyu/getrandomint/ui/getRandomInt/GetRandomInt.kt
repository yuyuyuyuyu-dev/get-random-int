package dev.yuyuyuyuyu.getrandomint.ui.getRandomInt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.yuyuyuyuyu.getrandomint.ui.components.CopyToClipboardButton

@Composable
fun GetRandomInt(state: GetRandomIntScreen.State, modifier: Modifier = Modifier) = Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    TextField(
        value = state.min?.toString() ?: "",
        onValueChange = { state.eventSink(GetRandomIntScreen.Event.MinChanged(newValue = it)) },
        label = { Text("Min") },
    )

    TextField(
        value = state.max?.toString() ?: "",
        onValueChange = { state.eventSink(GetRandomIntScreen.Event.MaxChanged(newValue = it)) },
        label = { Text("Max") },
    )

    Text(text = state.randomInt?.toString() ?: "")

    CopyToClipboardButton(
        textToCopy = state.randomInt?.toString() ?: "",
        enabled = state.randomInt != null,
    )
}
