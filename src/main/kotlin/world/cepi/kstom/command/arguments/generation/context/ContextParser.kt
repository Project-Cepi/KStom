package world.cepi.kstom.command.arguments.generation.context

import net.minestom.server.command.CommandSender

/**
 * Represents an object that can parse context
 *
 * @param T the type of object to grant
 */
interface ContextParser<T> {

    fun parse(sender: CommandSender): T?

}