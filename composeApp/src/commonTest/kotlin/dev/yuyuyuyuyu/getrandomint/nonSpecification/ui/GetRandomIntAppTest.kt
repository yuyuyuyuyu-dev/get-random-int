package dev.yuyuyuyuyu.getrandomint.nonSpecification.ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.getrandomint.ui.GetRandomIntApp
import getrandomint.composeapp.generated.resources.Res
import getrandomint.composeapp.generated.resources.app_name
import getrandomint.composeapp.generated.resources.open_source_licenses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.jetbrains.compose.resources.getString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalTestApi::class)
class GetRandomIntAppTest {
    @Test
    fun `should show the open source licenses`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithContentDescription("menu").performClick()
            awaitExactlyOne(hasText(getString(Res.string.open_source_licenses)))
            onNodeWithText(getString(Res.string.open_source_licenses)).performClick()

            // Assert
            onNodeWithText(getString(Res.string.open_source_licenses)).assertExists()
            onNodeWithText("Min").assertDoesNotExist()
        }

    @Test
    fun `should go back to the random int from the open source licenses`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }
            onNodeWithContentDescription("menu").performClick()
            awaitExactlyOne(hasText(getString(Res.string.open_source_licenses)))
            onNodeWithText(getString(Res.string.open_source_licenses)).performClick()

            // Act
            onNodeWithContentDescription("navigate back").performClick()

            // Assert
            awaitExactlyOne(hasText(getString(Res.string.app_name)))
            onNodeWithText("Min").assertExists()
            onNodeWithContentDescription("navigate back").assertDoesNotExist()
        }

    @Test
    fun `should open the source code`() =
        runComposeUiTest {
            // Arrange
            val openedUris = mutableListOf<String>()
            setContent {
                CompositionLocalProvider(
                    LocalUriHandler provides
                        object : UriHandler {
                            override fun openUri(uri: String) {
                                openedUris += uri
                            }
                        },
                ) {
                    GetRandomIntApp()
                }
            }

            // Act
            onNodeWithContentDescription("menu").performClick()
            awaitExactlyOne(hasText("Source code") or hasText("ソースコード"))
            onNode(hasText("Source code") or hasText("ソースコード")).performClick()

            // Assert
            assertEquals(listOf("https://github.com/yuyuyuyuyu-dev/get-random-int"), openedUris)
        }

    private suspend fun ComposeUiTest.awaitExactlyOne(matcher: SemanticsMatcher) =
        withContext(Dispatchers.Default) {
            withTimeout(5.seconds) {
                while (onAllNodes(matcher).fetchSemanticsNodes().size != 1) {
                    delay(10.milliseconds)
                }
            }
        }
}
