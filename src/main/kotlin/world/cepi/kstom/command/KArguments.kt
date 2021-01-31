package world.cepi.kstom.command

import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.*
import kotlin.reflect.KProperty


/**
 * KArguments class is used to declare delegated arguments for a command
 */
open class KArguments {
    /** The arguments will be initialized when command is called */
    lateinit var arguments: Arguments
    /** List of arguments that will be passed down to `addSyntax` as an argument array */
    val argumentList = mutableListOf<Argument<*>>()

    protected inline fun <reified T> Argument<T>.default(value: T) = setDefaultValue(value)
    operator fun <T> Argument<T>.getValue(thisRef: Any, property: KProperty<*>) =
        arguments.get(this)
}

/**
 * Adds a syntax to the command with executor
 * being extension function to CommandContext that will contain delegated arguments
 *
 * @param provider The provider of instance of KArguments' subclass
 * @param block The function that will be called every time command is called
 */
inline fun <T: KArguments> Command.syntax(crossinline provider: () -> T, crossinline block: CommandContext<T>.() -> Unit) {
    val executor = CommandExecutor { sender, args ->
        try {
            CommandContext(sender, provider().withArguments(args), args)
                .run(block)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    addSyntax(executor, *(provider().argumentList.toTypedArray()))
}