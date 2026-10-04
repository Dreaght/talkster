import dev.nucleusframework.desktop.application.dsl.CompressionLevel
import dev.nucleusframework.desktop.application.dsl.NativeImageMarch
import dev.nucleusframework.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.nucleus)
}

dependencies {
    implementation(project(":app:shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)

    implementation(libs.nucleus.nucleusApplication)
    implementation(libs.nucleus.decoratedWindowTao)
}

nucleus.application {
    mainClass = "dev.dreaght.talkster.MainKt"

    nativeDistributions {
        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Pacman, TargetFormat.AppImage)
        packageName = "dev.dreaght.talkster"
        packageVersion = "1.0.0"

        homepage = "https://github.com/Dreaght/rats"

        cleanupNativeLibs = true
        enableAotCache = true
        compressionLevel = CompressionLevel.Normal

        linux {
            appImage {
                compressionLevel = CompressionLevel.Normal
            }
            debMaintainer = "Dreaght <dreaght@icloud.com>"
        }
    }

    graalvm {
        isEnabled.set(true)
        javaLanguageVersion.set(25)
        imageName.set("rats")
        march.set(NativeImageMarch.NATIVE)
        buildArgs.add("-O2")
    }

    buildTypes.release {
        proguard {
            isEnabled.set(true)
            obfuscate.set(true)
            optimize.set(false)
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
        vendor.set(JvmVendorSpec.JETBRAINS)
    }
}
