package dev.yuyuyuyuyu.getrandomint.di

import dev.yuyuyuyuyu.getrandomint.domain.useCase.GetRandomIntUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetRandomIntUseCase)
}
