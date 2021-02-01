package world.cepi.kstom.command

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.exception.ArgumentSyntaxException

inline fun Command.addSyntax(crossinline lambda: () -> Unit) {
    this.setDefaultExecutor { _, _ -> lambda.invoke()}
}

inline fun Command.addSyntax(crossinline lambda: (sender: CommandSender) -> Unit) {
    this.setDefaultExecutor { sender, _ -> lambda.invoke(sender)}
}

fun Command.addSyntax(lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    defaultExecutor = CommandExecutor(lambda)
}

inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline lambda: () -> Unit) {
    this.addSyntax({ _, _ -> lambda.invoke()}, *arguments)
}

inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline lambda: (sender: CommandSender) -> Unit) {
    this.addSyntax({ sender, _ -> lambda.invoke(sender)}, *arguments)
}

inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: (sender: CommandSender, args: Arguments) -> Unit
) {
    this.addSyntax({ sender, args -> lambda.invoke(sender, args)}, *arguments)
}

inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: () -> Unit
) {
    this.addSyntax({ _, _ -> condition.invoke()}, { _, _ -> lambda.invoke()}, *arguments)
}

inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline condition: (source: CommandSender) -> Boolean, crossinline lambda: () -> Unit) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, { _, _ -> lambda.invoke()}, *arguments)
}

inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda: () -> Unit
) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, { _, _ -> lambda.invoke()}, *arguments)
}

inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
) {
    this.addSyntax({ _, _ -> condition.invoke()}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

inline  fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    noinline lambda: (sender: CommandSender, args: Arguments) -> Unit
) {
    this.addSyntax({ _, _ -> condition.invoke()}, lambda, *arguments)
}

inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    noinline lambda: (sender: CommandSender, args: Arguments) -> Unit
) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, lambda, *arguments)
}

inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline condition: (
    source: CommandSender,
    commandString: String
) -> Boolean, noinline lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, lambda, *arguments)
}

inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: () -> Unit) {
    this.setArgumentCallback({ _, _, -> lambda.invoke() }, arg)
}

inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: (source: CommandSender) -> Unit) {
    this.setArgumentCallback({ source, _ -> lambda.invoke(source) }, arg)
}

fun Command.setArgumentCallback(arg: Argument<*>, lambda: (source: CommandSender, value: ArgumentSyntaxException) -> Unit) {
    this.setArgumentCallback(lambda, arg)
}

fun Command.default(block: (sender: CommandSender, args: Arguments) -> Unit) {
    defaultExecutor = CommandExecutor(block)
}