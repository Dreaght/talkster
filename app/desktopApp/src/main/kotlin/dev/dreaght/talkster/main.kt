package dev.dreaght.talkster

import dev.nucleusframework.application.DecoratedWindow
import dev.nucleusframework.application.NucleusBackend
import dev.nucleusframework.application.nucleusApplication

fun main() = nucleusApplication(backend = NucleusBackend.Tao) {
    DecoratedWindow(
        onCloseRequest = ::exitApplication,
        title = "talkster",
    ) {
        App()
    }
}
