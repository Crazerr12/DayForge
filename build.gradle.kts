import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.ktlint) apply true
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    ktlint {
        version.set("1.1.1")
        android.set(true)
        verbose.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(ReporterType.HTML)
            reporter(ReporterType.CHECKSTYLE)
        }
        filter {
            include("**/*.kt")
            exclude("**/build/**")
        }
    }

    detekt {
        parallel = true
        allRules = true
        autoCorrect = true
        buildUponDefaultConfig = true
        config.setFrom(file("$rootDir/config/detekt/detekt-config.yml"))
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
            xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
            txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
            sarif.required.set(
                true,
            ) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
            md.required.set(true) // simple Markdown format
        }
    }

    tasks.withType<Detekt>().configureEach {
        this.jvmTarget = "1.8"
    }
    tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
        this.jvmTarget = "1.8"
    }

    dependencies {
        ktlintRuleset("io.nlopez.compose.rules:ktlint:0.3.11")
        detektPlugins("io.nlopez.compose.rules:detekt:0.3.11")
    }
}