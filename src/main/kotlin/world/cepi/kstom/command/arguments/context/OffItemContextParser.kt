package world.cepi.kstom.command.arguments.context

import net.kyori.adventure.text.Component
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack

/**
 * Context parser to get a player's item in main hand.
 */
object OffItemContextParser : ContextParser<ItemStack> {

    override fun parse(sender: CommandSender): ItemStack? =
        (sender as? Player)?.itemInOffHand

    override val callbackMessage = Component.text("No item found in offhand!")

}