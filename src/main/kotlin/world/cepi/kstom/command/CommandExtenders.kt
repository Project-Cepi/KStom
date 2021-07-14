package world.cepi.kstom.command

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.CommandSyntax
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.exception.ArgumentSyntaxException

data class SyntaxContext(val sender: CommandSender, val context: CommandContext)
data class ConditionContext(val sender: CommandSender, val input: String)
data class ArgumentCallbackContext(val sender: CommandSender, val exception: ArgumentSyntaxException)

inline fun Command.addSyntax(crossinline lambda: SyntaxContext.() -> Unit) {
    setDefaultExecutor { sender, context ->  lambda(SyntaxContext(sender, context)) }
}
inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: SyntaxContext.() -> Unit
): Collection<CommandSyntax> {
    return addSyntax({ sender, context ->  lambda(SyntaxContext(sender, context)) }, *arguments)
}


inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: ConditionContext.() -> Boolean,
    crossinline lambda: SyntaxContext.() -> Unit
): Collection<CommandSyntax> {
    return addConditionalSyntax(
        { sender, string -> condition(ConditionContext(sender, string ?: "")) },
        { sender, context -> lambda(SyntaxContext(sender, context)) },
        *arguments
    )
}

inline fun Command.setArgumentCallback(
    arg: Argument<*>,
    crossinline lambda: ArgumentCallbackContext.() -> Unit
) {
    setArgumentCallback({ source, value ->  lambda(ArgumentCallbackContext(source, value)) }, arg)
}

inline fun Command.default(crossinline block:  (sender: CommandSender, args: CommandContext) -> Unit) {
    defaultExecutor = CommandExecutor { sender, args ->  block(sender, args) }
}

fun Command.addSubcommands(vararg subcommands: Command) {
    subcommands.forEach { this.addSubcommand(it) }
}