package dev.yuyuyuyuyu.getrandomint.ui.getRandomInt

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.presenter.Presenter

class GetRandomIntPresenter : Presenter<GetRandomIntScreen.State> {
    @Composable
    override fun present(): GetRandomIntScreen.State {
        return GetRandomIntScreen.State
    }
}
