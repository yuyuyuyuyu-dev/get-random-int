package dev.yuyuyuyuyu.getrandomint.specification

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.getrandomint.ui.GetRandomIntApp
import getrandomint.composeapp.generated.resources.Res
import getrandomint.composeapp.generated.resources.copy_to_clipboard
import org.jetbrains.compose.resources.getString
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class GetRandomIntTest {
    @Test
    fun `should generate a random int from the chosen range`() =
        runComposeUiTest {
            // Arrange
            val dynamicPorts = 49152..65535
            replaceClipboardWithFake()
            setContent { GetRandomIntApp() }

            try {
                // Act
                onNodeWithText("Min").performTextInput(dynamicPorts.first.toString())
                onNodeWithText("Max").performTextInput(dynamicPorts.last.toString())
                onNodeWithText(getString(Res.string.copy_to_clipboard)).performClick()

                // Assert
                val newPort = textInFakeClipboard()?.toString()?.toIntOrNull()
                assertNotNull(newPort)
                assertTrue(newPort in dynamicPorts)
            } finally {
                restoreClipboard()
            }
        }
}

@JsFun(
    """() => {
        navigator.clipboard.writeText = (text) => {
            navigator.clipboard.fakeText = text;
            return Promise.resolve();
        };
    }""",
)
private external fun replaceClipboardWithFake()

@JsFun("() => navigator.clipboard.fakeText ?? null")
private external fun textInFakeClipboard(): JsString?

@JsFun(
    """() => {
        delete navigator.clipboard.writeText;
        delete navigator.clipboard.fakeText;
    }""",
)
private external fun restoreClipboard()
