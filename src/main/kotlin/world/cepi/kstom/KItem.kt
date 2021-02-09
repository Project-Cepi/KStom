package world.cepi.kstom

import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

public class KItem(material: Material, amount: Byte = 1, damage: Int = 0): ItemStack(material, amount, damage) {

    /** A mutable list of all the event callbacks for left click. Add to this list if you want to listen to it. */
    public val leftCallbacks: MutableList<(Player, Player.Hand) -> Unit> = mutableListOf()

    /** A mutable list of all the event callbacks for right click. Add to this list if you want to listen to it. */
    public val rightCallbacks: MutableList<(Player, Player.Hand) -> Unit> = mutableListOf()

    override fun onLeftClick(player: Player, hand: Player.Hand) {
        leftCallbacks.forEach { it(player, hand) }
    }

    override fun onRightClick(player: Player, hand: Player.Hand) {
        rightCallbacks.forEach { it(player, hand) }
    }

}

/**
 * DSL for KItem.
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
public inline fun kitem(material: Material = Material.PAPER, amount: Byte = 1, init: KItem.() -> Unit): KItem {
    val item = KItem(material, amount)
    item.init()
    return item
}