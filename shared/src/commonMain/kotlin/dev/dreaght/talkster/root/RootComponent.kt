package dev.dreaght.talkster.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dev.dreaght.talkster.Platform
import dev.dreaght.talkster.greeting.DefaultGreetingComponent
import dev.dreaght.talkster.greeting.GreetingComponent
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class GreetingScreen(val component: GreetingComponent) : Child()
    }
}


class DefaultRootComponent(
    componentContext: ComponentContext,
    val platform: Platform
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.GreetingScreen(platform),
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.GreetingScreen -> RootComponent.Child.GreetingScreen(mainComponent(componentContext))
        }

    private fun mainComponent(componentContext: ComponentContext): GreetingComponent =
        DefaultGreetingComponent(
            componentContext = componentContext,
            platform = platform
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data class GreetingScreen(val platform: Platform) : Config
    }
}