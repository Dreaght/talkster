import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        val webCommonMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.compose.ui)
            }
        }

        val jsMain by getting {
            dependsOn(webCommonMain)
            dependencies {
                implementation(project(":app:shared"))
            }
        }

        val wasmJsMain by getting {
            dependsOn(webCommonMain)
            dependencies {
                implementation(project(":app:shared"))
            }
        }
    }
}
