package dev.dreaght.talkster

import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DecomposeSettings
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.dreaght.talkster.root.DefaultRootComponent
import dev.dreaght.talkster.root.RootContent
import dev.nucleusframework.application.DecoratedWindow
import dev.nucleusframework.application.NucleusBackend
import dev.nucleusframework.application.nucleusApplication

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    DecomposeSettings.update { settings ->
        settings.copy(mainThreadCheckEnabled = false)
    }

    val lifecycle = LifecycleRegistry()

    nucleusApplication(backend = NucleusBackend.Tao) {
        val windowState = rememberWindowState()

        val root =
            DefaultRootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
                platform = JVMPlatform()
            )

        DecoratedWindow(
            onCloseRequest = ::exitApplication,
            title = "talkster",
        ) {
            LifecycleController(
                lifecycleRegistry = lifecycle,
                windowState = windowState,
                windowInfo = LocalWindowInfo.current,
            )
            RootContent(root)
        }
    }
}
