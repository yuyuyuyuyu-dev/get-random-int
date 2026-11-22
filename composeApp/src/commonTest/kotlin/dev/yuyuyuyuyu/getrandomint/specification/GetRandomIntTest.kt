package dev.yuyuyuyuyu.getrandomint.specification

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.getrandomint.ui.GetRandomIntApp
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class GetRandomIntTest {
    @Test
    fun `should generate a random int from the chosen range`() =
        runComposeUiTest {
            // Arrange
            val dynamicPorts = 49152..65535
            setContent { GetRandomIntApp() }

            // Act
            onNodeWithText("Min").performTextInput(dynamicPorts.first.toString())
            onNodeWithText("Max").performTextInput(dynamicPorts.last.toString())

            // Assert
            val newPort =
                onNode(isGeneratedInt, useUnmergedTree = true)
                    .fetchSemanticsNode()
                    .config[SemanticsProperties.Text]
                    .single()
                    .text
                    .toInt()
            assertTrue(newPort in dynamicPorts)
        }

    private val isGeneratedInt =
        SemanticsMatcher("is the generated int") { node ->
            SemanticsProperties.EditableText !in node.config &&
                node.config
                    .getOrNull(SemanticsProperties.Text)
                    ?.singleOrNull()
                    ?.text
                    ?.toIntOrNull() != null
        }
}
