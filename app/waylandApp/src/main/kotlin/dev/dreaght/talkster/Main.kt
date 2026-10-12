package dev.dreaght.talkster

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.DecomposeSettings
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.lifecycle.resume
import com.fromwau.kern.result.onError
import com.fromwau.kortex.wayland.Window
import com.fromwau.kortex.wayland.WindowStatus
import com.fromwau.kortex.wayland.kortexApplication
import com.fromwau.kortex.wayland.rememberWindowState
import dev.dreaght.talkster.root.DefaultRootComponent
import dev.dreaght.talkster.root.RootContent
import kotlin.system.exitProcess

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    DecomposeSettings.update { settings ->
        settings.copy(mainThreadCheckEnabled = false)
    }

    val lifecycle = LifecycleRegistry()

    kortexApplication {
        val windowState = rememberWindowState()
        var showing by remember { mutableStateOf(true) }
        val status = windowState.status

        val root =
            DefaultRootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
                platform = JVMPlatform()
            )

        LaunchedEffect(status) {
            when (status) {
                is WindowStatus.Placing -> {
                    lifecycle.create()
                }
                is WindowStatus.OnScreen -> {
                    lifecycle.resume()
                }
                is WindowStatus.Ended -> {
                    lifecycle.destroy()
                }
            }
        }

        if (showing) {
            Window(
                title = "talkster",
                appId = "dev.dreaght.talkster",
                state = windowState
            ) {
                if (windowState.closeRequested) {
                    ConfirmQuit(
                        onKeep = { windowState.declineClose() },
                        onQuit = { showing = false },
                    )
                }
                RootContent(root)
            }
        }
        LaunchedEffect(status) { if (status is WindowStatus.Ended) exitApplication() }
    }.onError { error ->
        System.err.println("kortex: $error")
        exitProcess(1)
    }
}
