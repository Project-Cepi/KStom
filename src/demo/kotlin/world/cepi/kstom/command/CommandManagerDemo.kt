package world.cepi.kstom.command

import net.minestom.server.command.builder.Command
import world.cepi.kstom.Manager

object ShellCommand : Command("shell")

fun old() {

    Manager.command.register(object : Command("otherShell") {
        init {
            default { sender, _ ->
                sender.sendMessage("This is another shell!")
            }
        }
    })

    Manager.command.register(object : Command("anotherShell") {
        init {
            default { sender, _ ->
                sender.sendMessage("This is another shell!")
            }
        }
    })

    Manager.command.register(ShellCommand)
    Manager.command.unregister(ShellCommand)
}

fun new() {

    Manager.command.register("otherShell") { sender, _ ->
        sender.sendMessage("This is another shell!")
    }

    Manager.command.command("anotherShell") {
        default { sender, _ ->
            sender.sendMessage("This is another shell!")
        }
    }

    ShellCommand.register()
    ShellCommand.unregister()
}