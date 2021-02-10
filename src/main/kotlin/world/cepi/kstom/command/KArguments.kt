package world.cepi.kstom.command

import kotlinx.coroutines.launch
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.Argument
import world.cepi.kstom.IOScope
import world.cepi.kstom.command.arguments.argumentsFromConstructor
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility


/**
 * KArguments class is used to declare delegated arguments for a command
 */
public open class KArguments {
    /** The arguments will be initialized when command is called */
    public lateinit var arguments: Arguments
    /** List of arguments that will be passed down to `addSyntax` as an argument array */
    public val argumentList: MutableList<Argument<*>> = mutableListOf()

    protected inline fun <reified T> Argument<T>.default(value: T): Argument<T> = setDefaultValue(value)
    public operator fun <T> Argument<T>.getValue(thisRef: Any, property: KProperty<*>): T =
        arguments.get(this)
}

/**
 * Adds a syntax to the command with executor
 * being extension function to CommandContext that will contain delegated arguments
 *
 * @param provider The provider of instance of KArguments' subclass
 * @param block The function that will be called every time command is called
 */
public inline fun <T: KArguments> Command.syntax(crossinline provider: () -> T, crossinline block: suspend CommandContext<T>.() -> Unit) {
    val executor = CommandExecutor { sender, args ->
        try {
            CommandContext(sender, provider().withArguments(args), args)
                .run { IOScope.launch { block() } }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    addSyntax(executor, *(provider().argumentList.toTypedArray()))
}

public inline fun <reified T> Command.syntax(crossinline block: suspend CommandContext<T>.() -> Unit) {
    if (T::class.visibility != KVisibility.PUBLIC) throw IllegalArgumentException("Class must be public")
    T::class.constructors.forEach { constructor ->
        val arguments = argumentsFromConstructor(constructor)
        val executor = CommandExecutor { sender, args ->
            try {
                val instance = constructor.call(*arguments.map { args.get(it) }.toTypedArray())

                CommandContext(sender, instance, args)
                    .run { IOScope.launch { block() } }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        addSyntax(executor, *arguments.toTypedArray())
    }
}