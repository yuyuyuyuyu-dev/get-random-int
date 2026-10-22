import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versionCatalogUpdate)
}

val ktlintVersion = libs.versions.ktlintCli.get()

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    extensions.configure<DetektExtension> {
        source.setFrom("src")
        buildUponDefaultConfig = true
    }

    extensions.configure<KtlintExtension> {
        version = ktlintVersion
        filter {
            val buildPath =
                layout.buildDirectory
                    .get()
                    .asFile.path + File.separator
            exclude { it.file.path.startsWith(buildPath) }
        }
    }
}
