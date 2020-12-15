package world.cepi.kstom

import net.minestom.server.command.CommandProcessor
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player

class KommandProcessor(
        val name: String,
        val aliases: List<String> = listOf(),
        val condition: (player: Player) -> Boolean = { _ -> true },
        val process: (sender: CommandSender, command: String, args: List<String>) -> Boolean = { _, _, _ -> true }
): CommandProcessor {
    override fun getCommandName(): String {
       return name
    }

    override fun getAliases(): Array<String> {
        return aliases.toTypedArray()
    }

    override fun process(sender: CommandSender, command: String, args: Array<out String>): Boolean {
        return process.invoke(sender, command, args.toList())
    }

    override fun hasAccess(player: Player): Boolean {
        return condition.invoke(player)
    }
}