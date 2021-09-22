package world.cepi.kstom.command

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.arguments.*
import world.cepi.kstom.command.kommand.Kommand

/**
 * This demo's XY:
 *
 * Create a command that manages the level number for a player.
 */

object Old : Command("hey") {
    init {
        val add = ArgumentType.Literal("add")
        val remove = ArgumentType.Literal("remove")
        val set = ArgumentType.Literal("set")

        val amount = ArgumentType.Integer("amount").min(0)

        addSyntax({ sender, context ->
            sender.sendMessage("Usage: add|remove|set <amount>")
        })

        addConditionalSyntax({ sender, _ -> sender is Player }, { sender, context ->
            val player = sender as Player

            player.level += context[amount]
        }, add, amount)

        addConditionalSyntax({ sender, _ -> sender is Player }, { sender, context ->
            val player = sender as Player

            player.level = (player.level - context[amount]).coerceAtLeast(0)

        }, remove, amount)

        addConditionalSyntax({ sender, _ -> sender is Player }, { sender, context ->
            val player = sender as Player

            player.level = context[amount]

        }, set, amount)

    }
}

object New : Kommand({
    val add by literal
    val remove by literal
    val set by literal

    val amount by ArgumentType::Integer.delegate { this.min(0) }

    syntax {
        sender.sendMessage("Usage: add|remove|set <amount>")
    }

    syntax(add, amount).onlyPlayers {
        player.level += !amount
    }

    syntax(remove, amount).onlyPlayers {
        player.level = (player.level - !amount).coerceAtLeast(0)
    }

    syntax(set, amount).onlyPlayers {
        player.level = !amount
    }


}, "hey")