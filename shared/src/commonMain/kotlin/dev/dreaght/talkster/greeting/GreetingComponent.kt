package dev.dreaght.talkster.greeting

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.dreaght.talkster.Platform

interface GreetingComponent {
    val model: Value<Model>

    fun onToggleClicked()

    data class Model(
        val showContent: Boolean,
        val greetingText: String
    )
}

class DefaultGreetingComponent(
    componentContext: ComponentContext,
    val platform: Platform
) : GreetingComponent, ComponentContext by componentContext {
    override val model: MutableValue<GreetingComponent.Model> = MutableValue(
        GreetingComponent.Model(showContent = false, greetingText = sayHello(platform.name))
    )

    override fun onToggleClicked() {
        model.update { it.copy(showContent = !it.showContent) }
    }
}
