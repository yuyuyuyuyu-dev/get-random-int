package dev.yuyuyuyuyu.getrandomint.nonSpecification.ui.getRandomInt

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.text.AnnotatedString
import dev.yuyuyuyuyu.getrandomint.ui.GetRandomIntApp
import getrandomint.composeapp.generated.resources.Res
import getrandomint.composeapp.generated.resources.copy_to_clipboard
import org.jetbrains.compose.resources.getString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class GetRandomIntTest {
    @Test
    fun `should show no int and disable copying before a range is entered`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            waitForIdle()

            // Assert
            onNode(isGeneratedInt).assertDoesNotExist()
            onNodeWithText(getString(Res.string.copy_to_clipboard)).assertIsNotEnabled()
        }

    @Test
    fun `should show no int while max is empty`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput("1")

            // Assert
            onNode(isGeneratedInt).assertDoesNotExist()
            onNodeWithText(getString(Res.string.copy_to_clipboard)).assertIsNotEnabled()
        }

    @Test
    fun `should show no int while min is empty`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Max").performTextInput("10")

            // Assert
            onNode(isGeneratedInt).assertDoesNotExist()
            onNodeWithText(getString(Res.string.copy_to_clipboard)).assertIsNotEnabled()
        }

    @Test
    fun `should show no int when min is greater than max`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput("10")
            onNodeWithText("Max").performTextInput("5")

            // Assert
            onNode(isGeneratedInt).assertDoesNotExist()
            onNodeWithText(getString(Res.string.copy_to_clipboard)).assertIsNotEnabled()
        }

    @Test
    fun `should show min when min equals max`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput("7")
            onNodeWithText("Max").performTextInput("7")

            // Assert
            assertEquals(7, generatedInt())
            onNodeWithText(getString(Res.string.copy_to_clipboard)).assertIsEnabled()
        }

    @Test
    fun `should generate an int from the whole range of Int`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput(Int.MIN_VALUE.toString())
            onNodeWithText("Max").performTextInput(Int.MAX_VALUE.toString())

            // Assert
            onNode(isGeneratedInt).assertExists()
        }

    @Test
    fun `should generate a negative int from a negative range`() =
        runComposeUiTest {
            // Arrange
            val range = -20..-11
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput(range.first.toString())
            onNodeWithText("Max").performTextInput(range.last.toString())

            // Assert
            assertTrue(generatedInt() in range)
        }

    @Test
    fun `should expose the generated int to accessibility services on its own`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput("1")
            onNodeWithText("Max").performTextInput("100")

            // Assert
            onNode(isGeneratedInt).assertExists()
        }

    @Test
    fun `should leave min empty when the input is not an int`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput("abc")

            // Assert
            onNodeWithText("Min").assert(hasNoInput)
        }

    @Test
    fun `should leave max empty when the input is larger than Int can hold`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Max").performTextInput("2147483648")

            // Assert
            onNodeWithText("Max").assert(hasNoInput)
        }

    @Test
    fun `should copy the generated int to the clipboard`() =
        runComposeUiTest {
            // Arrange
            replaceClipboardWithFake()
            setContent { GetRandomIntApp() }
            onNodeWithText("Min").performTextInput("1")
            onNodeWithText("Max").performTextInput("100")
            val expected = generatedInt().toString()

            try {
                // Act
                onNodeWithText(getString(Res.string.copy_to_clipboard)).performClick()

                // Assert
                assertEquals(expected, textInFakeClipboard()?.toString())
            } finally {
                restoreClipboard()
            }
        }

    private fun ComposeUiTest.generatedInt(): Int =
        onNode(isGeneratedInt)
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]
            .single()
            .text
            .toInt()

    private val isGeneratedInt =
        SemanticsMatcher("is the generated int") { node ->
            SemanticsProperties.EditableText !in node.config &&
                node.config
                    .getOrNull(SemanticsProperties.Text)
                    ?.singleOrNull()
                    ?.text
                    ?.toIntOrNull() != null
        }

    private val hasNoInput = SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString(""))
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
