package world.cepi.kstom.command

import net.minestom.server.command.CommandManager
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command

inline fun CommandManager.command(name: String, block: Command.() -> Unit) =
    register(Command(name).apply(block))

fun CommandManager.register(name: String, block: (sender: CommandSender, args: Arguments) -> Unit) =
    register(Command(name).apply { default(block) })