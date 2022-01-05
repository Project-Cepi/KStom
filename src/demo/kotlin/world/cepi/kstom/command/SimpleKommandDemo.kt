package world.cepi.kstom.command

import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.SimpleCommand

object OldSimple : SimpleCommand("hello", "howdy", "ello") {

    override fun hasAccess(sender: CommandSender, commandString: String?): Boolean {
        return sender is ConsoleSender
    }

    override fun process(sender: CommandSender, command: String, args: Array<out String>): Boolean {
        sender.sendMessage(command)
        return true
    }

}

object NewSimple : SimpleKommand(
    "hello", "howdy", "ello",
    condition = { sender, _ -> sender is ConsoleSender },
    process = { _, _, _, -> true }
)