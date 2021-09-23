package world.cepi.kstom.command

import net.minestom.server.command.CommandManager
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import world.cepi.kstom.Manager
import world.cepi.kstom.command.kommand.Kommand

public fun commandUnregistered(name: String, block: Kommand.() -> Unit) =
    Kommand(block, name)

public fun CommandManager.command(name: String, block: Kommand.() -> Unit): Unit =
    Kommand(block, name).register()

public fun CommandManager.register(name: String, block: Kommand.SyntaxContext.() -> Unit): Unit =
    register(Command(name).apply {
        setDefaultExecutor { sender, context -> block(Kommand.SyntaxContext(sender, context)) }
    })

public fun Command.register() {
    Manager.command.register(this)
}

public fun Command.unregister() {
    Manager.command.unregister(this)
}