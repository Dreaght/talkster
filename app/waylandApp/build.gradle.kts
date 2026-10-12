import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(project(":app:shared"))
    implementation(compose.desktop.currentOs)
    implementation(libs.kortex.wayland)
    implementation(libs.compose.material3)
}

compose.desktop {
    application {
        mainClass = "dev.dreaght.talkster.MainKt"
        jvmArgs("--enable-native-access=ALL-UNNAMED")

        nativeDistributions {
            targetFormats(TargetFormat.Deb)
            packageName = "dev.dreaght.talkster.wayland"
            packageVersion = "1.0.0"
        }

        buildTypes.release {
            proguard {
                isEnabled.set(true)
                obfuscate.set(true)
                optimize.set(true)
                configurationFiles.from(project.file("proguard-rules.pro"))
            }
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
        vendor.set(JvmVendorSpec.JETBRAINS)
    }
}
