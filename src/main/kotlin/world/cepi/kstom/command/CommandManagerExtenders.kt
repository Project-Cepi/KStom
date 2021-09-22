package world.cepi.kstom.command

import net.minestom.server.command.CommandManager
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import world.cepi.kstom.Manager

public inline fun commandUnregistered(name: String, block: Command.() -> Unit) =
    Command(name).apply(block)

public inline fun CommandManager.command(name: String, block: Command.() -> Unit): Unit =
    register(Command(name).apply(block))

public fun CommandManager.register(name: String, block: (sender: CommandSender, args: CommandContext) -> Unit): Unit =
    register(Command(name).apply { setDefaultExecutor(block) })

public fun Command.register() {
    Manager.command.register(this)
}

public fun Command.unregister() {
    Manager.command.unregister(this)
}