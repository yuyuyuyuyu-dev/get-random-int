package dev.yuyuyuyuyu.getrandomint.di

import com.slack.circuit.foundation.Circuit
import dev.yuyuyuyuyu.getrandomint.ui.getRandomInt.GetRandomInt
import dev.yuyuyuyuyu.getrandomint.ui.getRandomInt.GetRandomIntPresenter
import dev.yuyuyuyuyu.getrandomint.ui.getRandomInt.GetRandomIntScreen
import dev.yuyuyuyuyu.getrandomint.ui.openSourceLicenseList.OpenSourceLicenseList
import dev.yuyuyuyuyu.getrandomint.ui.openSourceLicenseList.OpenSourceLicenseListPresenter
import dev.yuyuyuyuyu.getrandomint.ui.openSourceLicenseList.OpenSourceLicenseListScreen
import org.koin.dsl.module

val uiModule = module {
    single {
        Circuit.Builder()

            .addPresenter<GetRandomIntScreen, GetRandomIntScreen.State>(GetRandomIntPresenter())
            .addUi<GetRandomIntScreen, GetRandomIntScreen.State> { state, modifier ->
                GetRandomInt(state, modifier)
            }

            .addPresenter<OpenSourceLicenseListScreen, OpenSourceLicenseListScreen.State>(
                OpenSourceLicenseListPresenter(),
            )
            .addUi<OpenSourceLicenseListScreen, OpenSourceLicenseListScreen.State> { _, modifier ->
                OpenSourceLicenseList(modifier)
            }

            .build()
    }
}
