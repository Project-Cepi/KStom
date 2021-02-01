package world.cepi.kstom

import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

public class KItem(material: Material, amount: Byte = 1, damage: Int = 0): ItemStack(material, amount, damage) {

    public val leftCallbacks: MutableList<(Player, Player.Hand) -> Unit> = mutableListOf()
    public val rightCallbacks: MutableList<(Player, Player.Hand) -> Unit> = mutableListOf()

    override fun onLeftClick(player: Player, hand: Player.Hand) {
        leftCallbacks.forEach { it(player, hand) }
    }

    override fun onRightClick(player: Player, hand: Player.Hand) {
        rightCallbacks.forEach { it(player, hand) }
    }

}

public fun item(material: Material = Material.PAPER, amount: Byte = 1, init: KItem.() -> Unit): KItem {
    val item = KItem(material, amount)
    item.init()
    return item
}