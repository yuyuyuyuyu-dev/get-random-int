package dev.yuyuyuyuyu.getrandomint.nonSpecification.ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.getrandomint.ui.GetRandomIntApp
import getrandomint.composeapp.generated.resources.Res
import getrandomint.composeapp.generated.resources.app_name
import getrandomint.composeapp.generated.resources.open_source_licenses
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.awaitPendingResourceStateLoads
import org.jetbrains.compose.resources.getString
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class GetRandomIntAppTest {
    @Test
    fun `should show the open source licenses`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithContentDescription("menu").performClick()
            awaitResourceLoads()
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
            awaitResourceLoads()
            onNodeWithText(getString(Res.string.open_source_licenses)).performClick()

            // Act
            onNodeWithContentDescription("navigate back").performClick()

            // Assert
            awaitResourceLoads()
            onNodeWithText(getString(Res.string.app_name)).assertExists()
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
            awaitResourceLoads()
            onNode(hasText("Source code") or hasText("ソースコード")).performClick()

            // Assert
            assertEquals(listOf("https://github.com/yuyuyuyuyu-dev/get-random-int"), openedUris)
        }

    @Test
    fun `should clear the focus when the outside of the text fields is tapped`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }
            onNodeWithText("Min").performClick()

            // Act
            onRoot().performTouchInput { click(bottomCenter - Offset(0f, 1f)) }

            // Assert
            onNodeWithText("Min").assertIsNotFocused()
        }

    @Test
    fun `should keep the focus when a text field is tapped`() =
        runComposeUiTest {
            // Arrange
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performClick()

            // Assert
            onNodeWithText("Min").assertIsFocused()
        }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun ComposeUiTest.awaitResourceLoads() {
        waitForIdle()
        awaitPendingResourceStateLoads()
        waitForIdle()
    }
}
