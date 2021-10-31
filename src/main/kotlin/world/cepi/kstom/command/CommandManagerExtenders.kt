package world.cepi.kstom.command

import net.minestom.server.command.CommandManager
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext

inline fun commandUnregistered(name: String, block: Command.() -> Unit) =
    Command(name).apply(block)

inline fun CommandManager.command(name: String, block: Command.() -> Unit): Unit =
    register(Command(name).apply(block))

inline fun CommandManager.register(name: String, crossinline block: (sender: CommandSender, args: CommandContext) -> Unit): Unit =
    register(Command(name).apply { default(block) })

