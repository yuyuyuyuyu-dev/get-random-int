package dev.yuyuyuyuyu.getrandomint.domain.useCase

import kotlin.test.Test
import kotlin.test.assertTrue

class GetRandomIntUseCaseTest {
    private val getRandomIntUseCase = GetRandomIntUseCase()

    @Test
    fun `invoke() should return random int between 1 and 10`() {
        // Arrange
        val min = 1
        val max = 10
        val numberOfAttempts = 1000

        // Act
        val actual = (1..numberOfAttempts).map { getRandomIntUseCase(min, max) }

        // Assert
        assertTrue(actual.all { it in min..max })
    }

    @Test
    fun `invoke() should return random int between 11 and 20`() {
        // Arrange
        val min = 11
        val max = 20
        val numberOfAttempts = 1000

        // Act
        val actual = (1..numberOfAttempts).map { getRandomIntUseCase(min, max) }

        // Assert
        assertTrue(actual.all { it in min..max })
    }

    @Test
    fun `invoke() should return random int between 21 and 30`() {
        // Arrange
        val min = 21
        val max = 30
        val numberOfAttempts = 1000

        // Act
        val actual = (1..numberOfAttempts).map { getRandomIntUseCase(min, max) }

        // Assert
        assertTrue(actual.all { it in min..max })
    }
}
