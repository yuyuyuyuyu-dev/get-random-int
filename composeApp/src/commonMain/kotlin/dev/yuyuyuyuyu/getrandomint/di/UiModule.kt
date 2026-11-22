package dev.yuyuyuyuyu.getrandomint.di

import dev.yuyuyuyuyu.getrandomint.ui.getRandomInt.GetRandomIntViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule =
    module {
        viewModelOf(::GetRandomIntViewModel)
    }
