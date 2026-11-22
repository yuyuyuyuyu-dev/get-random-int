package dev.yuyuyuyuyu.getrandomint.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.yuyuyuyuyu.getrandomint.di.domainModule
import dev.yuyuyuyuyu.getrandomint.di.uiModule
import dev.yuyuyuyuyu.getrandomint.ui.getRandomInt.GetRandomInt
import dev.yuyuyuyuyu.getrandomint.ui.getRandomInt.GetRandomIntRoute
import dev.yuyuyuyuyu.getrandomint.ui.openSourceLicenseList.OpenSourceLicenseList
import dev.yuyuyuyuyu.getrandomint.ui.openSourceLicenseList.OpenSourceLicenseListRoute
import dev.yuyuyuyuyu.mymaterialtheme.MyMaterialTheme
import dev.yuyuyuyuyu.simpleTopAppBar.SimpleTopAppBar
import getrandomint.composeapp.generated.resources.Res
import getrandomint.composeapp.generated.resources.app_name
import getrandomint.composeapp.generated.resources.open_source_licenses
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun GetRandomIntApp() {
    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current

    val backStack = remember { mutableStateListOf<NavKey>(GetRandomIntRoute) }

    KoinApplication(
        application = {
            printLogger()
            modules(uiModule, domainModule)
        },
    ) {
        MyMaterialTheme {
            Scaffold(
                modifier =
                    Modifier.pointerInput(focusManager) {
                        detectTapGestures { focusManager.clearFocus() }
                    },
                topBar = {
                    SimpleTopAppBar(
                        title =
                            when (backStack.lastOrNull()) {
                                is OpenSourceLicenseListRoute -> stringResource(Res.string.open_source_licenses)
                                else -> stringResource(Res.string.app_name)
                            },
                        navigateBackIsPossible = backStack.size > 1,
                        onNavigateBackButtonClick = { backStack.removeLastOrNull() },
                        onOpenSourceLicensesButtonClick = { backStack.add(OpenSourceLicenseListRoute) },
                        onSourceCodeButtonClick = {
                            uriHandler.openUri("https://github.com/yuyuyuyuyu-dev/get-random-int")
                        },
                    )
                },
            ) { paddingValues ->
                NavDisplay(
                    backStack = backStack,
                    modifier = Modifier.padding(paddingValues),
                    onBack = { backStack.removeLastOrNull() },
                    entryDecorators =
                        listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator(),
                        ),
                    entryProvider =
                        entryProvider {
                            entry<GetRandomIntRoute> { GetRandomInt() }
                            entry<OpenSourceLicenseListRoute> { OpenSourceLicenseList() }
                        },
                )
            }
        }
    }
}
