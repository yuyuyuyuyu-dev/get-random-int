package dev.yuyuyuyuyu.getrandomint

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.yuyuyuyuyu.getrandomint.ui.GetRandomIntApp
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        GetRandomIntApp()
    }
}