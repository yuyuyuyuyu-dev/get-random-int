package dev.yuyuyuyuyu.getrandomint.ui.getRandomInt

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen

data object GetRandomIntScreen : Screen {
    data class State(
        val min: Int?,
        val max: Int?,
        val randomInt: Int?,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class MinChanged(val newValue: String) : Event()
        data class MaxChanged(val newValue: String) : Event()
    }
}
