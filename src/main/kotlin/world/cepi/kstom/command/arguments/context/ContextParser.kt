package world.cepi.kstom.command.arguments.context

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.arguments.Argument

/**
 * Represents an object that can parse context
 *
 * @param T the type of object to grant
 */
interface ContextParser<T> {

    fun or(): Pair<Argument<out T>, String>? = null

    fun parse(sender: CommandSender): T?

}