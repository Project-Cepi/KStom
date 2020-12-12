package world.cepi.kstom

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.Argument

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