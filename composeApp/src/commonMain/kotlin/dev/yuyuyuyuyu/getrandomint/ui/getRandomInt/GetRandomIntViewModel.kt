package dev.yuyuyuyuyu.getrandomint.ui.getRandomInt

import androidx.lifecycle.ViewModel
import dev.yuyuyuyuyu.getrandomint.domain.useCase.GetRandomIntUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetRandomIntViewModel(
    private val getRandomIntUseCase: GetRandomIntUseCase,
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(GetRandomIntUiState(min = null, max = null, randomInt = null))
    val uiState: StateFlow<GetRandomIntUiState> = mutableUiState.asStateFlow()

    fun onMinChange(newValue: String) = updateRange(min = newValue.toIntOrNull(), max = uiState.value.max)

    fun onMaxChange(newValue: String) = updateRange(min = uiState.value.min, max = newValue.toIntOrNull())

    private fun updateRange(
        min: Int?,
        max: Int?,
    ) {
        mutableUiState.value =
            GetRandomIntUiState(
                min = min,
                max = max,
                randomInt =
                    if (min != null && max != null && min <= max) {
                        getRandomIntUseCase(min = min, max = max)
                    } else {
                        null
                    },
            )
    }
}
