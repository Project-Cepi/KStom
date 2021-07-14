package world.cepi.kstom.command.arguments.generation.context

import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack

/**
 * Context parser to get a player's item in main hand.
 */
object MainItemContextParser : ContextParser<ItemStack> {

    override fun parse(sender: CommandSender): ItemStack? =
        (sender as? Player)?.itemInMainHand

}