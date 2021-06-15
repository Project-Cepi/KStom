package world.cepi.kstom.command

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.CommandSyntax
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import world.cepi.kstom.Manager

fun launchTry(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.launch(CoroutineExceptionHandler { _, exception ->
        Manager.exception.handleException(exception)
    }, block = block)
}

public inline fun Command.addSyntax(crossinline lambda: suspend () -> Unit) {
    setDefaultExecutor { _, _ -> launchTry { lambda() }}
}

public inline fun Command.addSyntax(crossinline lambda: suspend (sender: CommandSender) -> Unit) {
    setDefaultExecutor { sender, _ -> launchTry { lambda(sender) }}
}

public inline fun Command.addSyntax(crossinline lambda: suspend (sender: CommandSender, args: CommandContext) -> Unit) {
    defaultExecutor = CommandExecutor { sender, args -> launchTry { lambda(sender, args) } }
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: suspend () -> Unit
): Collection<CommandSyntax> {
    return addSyntax({ _, _ -> launchTry { lambda() }}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: suspend (sender: CommandSender) -> Unit
): Collection<CommandSyntax> {
    return addSyntax({ sender, _ -> launchTry { lambda(sender) }}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: suspend (sender: CommandSender, args: CommandContext) -> Unit
): Collection<CommandSyntax> {
    return addSyntax({ sender, args -> launchTry { lambda(sender, args)} }, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: suspend () -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { _, _ -> condition() },
        { _, _ -> launchTry { lambda() } },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: suspend () -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, _ -> condition(source) },
        { _, _ -> launchTry { lambda() } },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda: suspend () -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, string -> condition(source, string ?: "") },
        { _, _ -> launchTry { lambda() } },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: suspend (sender: CommandSender) -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { _, _ -> condition() },
        { sender, _ -> launchTry { lambda(sender) } },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: suspend (sender: CommandSender) -> Unit
) {
    addConditionalSyntax(
        { source, _ -> condition(source) },
        { sender, _ -> launchTry { lambda(sender) } },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda: suspend (sender: CommandSender) -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, string -> condition(source, string ?: "") },
        { sender, _ -> launchTry { lambda(sender) } },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: suspend (sender: CommandSender, args: CommandContext) -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { _, _ -> condition()},
        { sender, args -> launchTry { lambda(sender, args) } },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: suspend (sender: CommandSender, args: CommandContext) -> Unit
) {
    addConditionalSyntax(
        { source, _ -> condition(source)},
        { sender, args -> launchTry { lambda(sender, args) } },
        *arguments
    )
}

public inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline condition: (
    source: CommandSender,
    commandString: String
) -> Boolean, crossinline lambda: suspend (sender: CommandSender, args: CommandContext) -> Unit): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, string -> condition(source, string ?: "")},
        { sender, args -> launchTry { lambda(sender, args) } },
        *arguments
    )
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: suspend () -> Unit) {
    setArgumentCallback({ _, _ -> launchTry { lambda() } }, arg)
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: suspend (source: CommandSender) -> Unit) {
    setArgumentCallback({ source, _ -> launchTry { lambda(source) } }, arg)
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: suspend (source: CommandSender, value: ArgumentSyntaxException) -> Unit) {
    setArgumentCallback({ source, value -> launchTry { lambda(source, value) } }, arg)
}

public inline fun Command.default(crossinline block: suspend (sender: CommandSender, args: CommandContext) -> Unit) {
    defaultExecutor = CommandExecutor { sender, args -> launchTry { block(sender, args) } }
}

public fun Command.addSubcommands(vararg subcommands: Command) {
    subcommands.forEach { this.addSubcommand(it) }
}