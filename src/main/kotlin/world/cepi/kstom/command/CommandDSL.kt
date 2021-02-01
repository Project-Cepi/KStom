package world.cepi.kstom.command

import net.minestom.server.command.CommandManager
import net.minestom.server.command.builder.Command

fun CommandManager.registerCommand(name: String, command: Command.() -> Unit) {
    this.register(object : Command(name) {
        init {
            command(this)
        }
    })
}