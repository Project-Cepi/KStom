package world.cepi.kstom.command.arguments.context

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.ArgumentCallback
import net.minestom.server.command.builder.arguments.Argument
import world.cepi.kstom.command.arguments.ArgumentContext
import world.cepi.kstom.command.arguments.generation.CallbackGenerator

/**
 * Represents an object that can parse context
 *
 * @param T the type of object to grant
 */
interface ContextParser<T> {

    fun or(): Argument<out T>? = null

    fun parse(sender: CommandSender): T?

    val callbackMessage: Component

    fun toArgumentContext() = ArgumentContext(
        id = or()?.id,
        argument = or()
    ) { parse(this) }.also {
        it.callback = ArgumentCallback { sender, _ ->
            sender.sendMessage(CallbackGenerator.errorSymbol.append(callbackMessage.color(NamedTextColor.RED)))
        }
    }

}