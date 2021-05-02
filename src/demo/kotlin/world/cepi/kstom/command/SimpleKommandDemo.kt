package world.cepi.kstom.command

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.SimpleCommand

object Old : SimpleCommand("hello", "howdy", "ello") {

    override fun hasAccess(sender: CommandSender, commandString: String?): Boolean {
        return sender.isConsole
    }

    override fun process(sender: CommandSender, command: String, args: Array<out String>): Boolean {
        sender.sendMessage(command)
        return true
    }

}

object New : SimpleKommand(
    "hello", "howdy", "ello",
    condition = { sender, _ -> sender.isConsole },
    process = { _, _, _, -> true }
)