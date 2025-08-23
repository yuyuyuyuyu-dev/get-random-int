package dev.yuyuyuyuyu.getrandomint

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.yuyuyuyuyu.getrandomint.ui.GetRandomIntApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GetRandomInt",
    ) {
        GetRandomIntApp()
    }
}