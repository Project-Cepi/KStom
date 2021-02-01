package world.cepi.kstom.command

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.exception.ArgumentSyntaxException

public inline fun Command.addSyntax(crossinline lambda: () -> Unit) {
    this.setDefaultExecutor { _, _ -> lambda.invoke()}
}

public inline fun Command.addSyntax(crossinline lambda: (sender: CommandSender) -> Unit) {
    this.setDefaultExecutor { sender, _ -> lambda.invoke(sender)}
}

public fun Command.addSyntax(lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    defaultExecutor = CommandExecutor(lambda)
}

public inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline lambda: () -> Unit) {
    this.addSyntax({ _, _ -> lambda.invoke()}, *arguments)
}

public inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline lambda: (sender: CommandSender) -> Unit) {
    this.addSyntax({ sender, _ -> lambda.invoke(sender)}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline lambda: (sender: CommandSender, args: Arguments) -> Unit
) {
    this.addSyntax({ sender, args -> lambda.invoke(sender, args)}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: () -> Unit
) {
    this.addSyntax({ _, _ -> condition.invoke()}, { _, _ -> lambda.invoke()}, *arguments)
}

public inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline condition: (source: CommandSender) -> Boolean, crossinline lambda: () -> Unit) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, { _, _ -> lambda.invoke()}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda: () -> Unit
) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, { _, _ -> lambda.invoke()}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
) {
    this.addSyntax({ _, _ -> condition.invoke()}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender, commandString: String) -> Boolean,
    crossinline lambda: (sender: CommandSender) -> Unit
) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: () -> Boolean,
    noinline lambda: (sender: CommandSender, args: Arguments) -> Unit
) {
    this.addSyntax({ _, _ -> condition.invoke()}, lambda, *arguments)
}

public inline fun Command.addSyntax(
    vararg arguments: Argument<*>,
    crossinline condition: (source: CommandSender) -> Boolean,
    noinline lambda: (sender: CommandSender, args: Arguments) -> Unit
) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, lambda, *arguments)
}

public inline fun Command.addSyntax(vararg arguments: Argument<*>, crossinline condition: (
    source: CommandSender,
    commandString: String
) -> Boolean, noinline lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, lambda, *arguments)
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: () -> Unit) {
    this.setArgumentCallback({ _, _ -> lambda.invoke() }, arg)
}

public inline fun Command.setArgumentCallback(arg: Argument<*>, crossinline lambda: (source: CommandSender) -> Unit) {
    this.setArgumentCallback({ source, _ -> lambda.invoke(source) }, arg)
}

public fun Command.setArgumentCallback(arg: Argument<*>, lambda: (source: CommandSender, value: ArgumentSyntaxException) -> Unit) {
    this.setArgumentCallback(lambda, arg)
}

public fun Command.default(block: (sender: CommandSender, args: Arguments) -> Unit) {
    defaultExecutor = CommandExecutor(block)
}