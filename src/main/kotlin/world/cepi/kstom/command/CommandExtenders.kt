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

public inline fun Command.addSyntax(crossinline lambda: () -> Unit) {
    setDefaultExecutor { _, _ ->  lambda() }
}

public inline fun Command.addSyntax(crossinline lambda: (sender: CommandSender) -> Unit) {
    setDefaultExecutor { sender, _ ->  lambda(sender) }
}

public inline fun Command.addSyntax(crossinline lambda: (sender: CommandSender, args: CommandContext) -> Unit) {
    defaultExecutor = CommandExecutor { sender, args ->  lambda(sender, args) }
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: () -> Unit
): Collection<CommandSyntax> {
    return addSyntax({ _, _ ->  lambda() }, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: (sender: CommandSender) -> Unit
): Collection<CommandSyntax> {
    return addSyntax({ sender, _ ->  lambda(sender) }, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: (sender: CommandSender, args: CommandContext) -> Unit
): Collection<CommandSyntax> {
    return addSyntax({ sender, args ->  lambda(sender, args) }, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: () -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { _, _ -> condition() },
        { _, _ -> lambda() },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: () -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, _ -> condition(source) },
        { _, _ -> lambda() },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda: () -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, string -> condition(source, string ?: "") },
        { _, _ -> lambda() },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { _, _ -> condition() },
        { sender, _ -> lambda(sender) },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
) {
    addConditionalSyntax(
        { source, _ -> condition(source) },
        { sender, _ -> lambda(sender) },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda:  (sender: CommandSender) -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, string -> condition(source, string ?: "") },
        { sender, _ -> lambda(sender) },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: (sender: CommandSender, args: CommandContext) -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { _, _ -> condition()},
        { sender, args ->  lambda(sender, args) },
        *arguments
    )
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: (sender: CommandSender, args: CommandContext) -> Unit
) {
    addConditionalSyntax(
        { source, _ -> condition(source)},
        { sender, args ->  lambda(sender, args) },
        *arguments
    )
}

public inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline condition: (
    source: CommandSender,
    commandString: String
) -> Boolean, crossinline lambda: (sender: CommandSender, args: CommandContext) -> Unit): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { source, string -> condition(source, string ?: "")},
        { sender, args ->  lambda(sender, args) },
        *arguments
    )
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: () -> Unit) {
    setArgumentCallback({ _, _ ->  lambda() }, arg)
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: (source: CommandSender) -> Unit) {
    setArgumentCallback({ source, _ ->  lambda(source) }, arg)
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: (source: CommandSender, value: ArgumentSyntaxException) -> Unit) {
    setArgumentCallback({ source, value ->  lambda(source, value) }, arg)
}

public inline fun Command.default(crossinline block:  (sender: CommandSender, args: CommandContext) -> Unit) {
    defaultExecutor = CommandExecutor { sender, args ->  block(sender, args) }
}

public fun Command.addSubcommands(vararg subcommands: Command) {
    subcommands.forEach { this.addSubcommand(it) }
}