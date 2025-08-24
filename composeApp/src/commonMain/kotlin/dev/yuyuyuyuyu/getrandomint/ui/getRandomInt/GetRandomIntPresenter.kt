package dev.yuyuyuyuyu.getrandomint.ui.getRandomInt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.presenter.Presenter
import dev.yuyuyuyuyu.getrandomint.domain.useCase.GetRandomIntUseCase

class GetRandomIntPresenter(
    private val getRandomIntUseCase: GetRandomIntUseCase,
) : Presenter<GetRandomIntScreen.State> {
    @Composable
    override fun present(): GetRandomIntScreen.State {
        var min by rememberSaveable { mutableStateOf<Int?>(null) }
        var max by rememberSaveable { mutableStateOf<Int?>(null) }

        return GetRandomIntScreen.State(
            min = min,
            max = max,
            randomInt = let {
                val min = min ?: return@let null
                val max = max ?: return@let null

                getRandomIntUseCase(min = min, max = max)
            },
        ) { event ->
            when (event) {
                is GetRandomIntScreen.Event.MinChanged -> min = event.newValue.toIntOrNull()
                is GetRandomIntScreen.Event.MaxChanged -> max = event.newValue.toIntOrNull()
            }
        }
    }
}
