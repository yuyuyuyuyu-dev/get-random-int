package dev.yuyuyuyuyu.getrandomint.domain.useCase

class GetRandomIntUseCase {
    operator fun invoke(min: Int, max: Int): Int = (min..max).random()
}
