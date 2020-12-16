package world.cepi.kstom

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.ArgumentCallback
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.condition.CommandCondition
import org.jetbrains.annotations.NotNull

fun Command.addSyntax(lambda: () -> Unit) {
    this.setDefaultExecutor { _, _ -> lambda.invoke()}
}

fun Command.addSyntax(lambda: (sender: CommandSender) -> Unit) {
    this.setDefaultExecutor { sender, _ -> lambda.invoke(sender)}
}

fun Command.addSyntax(lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    this.setDefaultExecutor { sender, args -> lambda.invoke(sender, args)}
}

fun Command.addSyntax(vararg arguments: Argument<*>, lambda: () -> Unit) {
    this.addSyntax({ _, _ -> lambda.invoke()}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, lambda: (sender: CommandSender) -> Unit) {
    this.addSyntax({ sender, _ -> lambda.invoke(sender)}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    this.addSyntax({ sender, args -> lambda.invoke(sender, args)}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: () -> Boolean, lambda: () -> Unit) {
    this.addSyntax({ _, _ -> condition.invoke()}, { _, _ -> lambda.invoke()}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: (source: CommandSender) -> Boolean, lambda: () -> Unit) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, { _, _ -> lambda.invoke()}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: (source: CommandSender, commandString: String) -> Boolean, lambda: () -> Unit) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, { _, _ -> lambda.invoke()}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: () -> Boolean, lambda: (sender: CommandSender) -> Unit) {
    this.addSyntax({ _, _ -> condition.invoke()}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: (source: CommandSender) -> Boolean, lambda: (sender: CommandSender) -> Unit) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: (source: CommandSender, commandString: String) -> Boolean, lambda: (sender: CommandSender) -> Unit) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, { sender, _ -> lambda.invoke(sender)}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: () -> Boolean, lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    this.addSyntax({ _, _ -> condition.invoke()}, { sender, args -> lambda.invoke(sender, args)}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: (source: CommandSender) -> Boolean, lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    this.addSyntax({ source, _ -> condition.invoke(source)}, { sender, args -> lambda.invoke(sender, args)}, *arguments)
}

fun Command.addSyntax(vararg arguments: Argument<*>, condition: (source: CommandSender, commandString: String) -> Boolean, lambda: (sender: CommandSender, args: Arguments) -> Unit) {
    this.addSyntax({ source, string -> condition.invoke(source, string ?: "")}, { sender, args -> lambda.invoke(sender, args)}, *arguments)
}

fun Command.setArgumentCallback(arg: Argument<*>, lambda: () -> Unit) {
    this.setArgumentCallback({ _, _, _ -> lambda.invoke() }, arg)
}

fun Command.setArgumentCallback(arg: Argument<*>, lambda: (source: CommandSender) -> Unit) {
    this.setArgumentCallback({ source, _, _ -> lambda.invoke(source) }, arg)
}

fun Command.setArgumentCallback(arg: Argument<*>, lambda: (source: CommandSender, value: String) -> Unit) {
    this.setArgumentCallback({ source, value, _ -> lambda.invoke(source, value) }, arg)
}

fun Command.setArgumentCallback(arg: Argument<*>, lambda: (source: CommandSender, value: String, error: Int) -> Unit) {
    this.setArgumentCallback({ source, value, error -> lambda.invoke(source, value, error) }, arg)
}