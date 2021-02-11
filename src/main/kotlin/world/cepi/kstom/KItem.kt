package world.cepi.kstom

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

public class KItem(material: Material, amount: Byte = 1, damage: Int = 0): ItemStack(material, amount, damage) {

    private val _clicks = MutableSharedFlow<Click>(extraBufferCapacity = 10)
    public val clicks: SharedFlow<Click> = _clicks.asSharedFlow()

    public val leftClicks: Flow<Click.Left> = _clicks.filterIsInstance()
    public val rightClicks: Flow<Click.Right> = _clicks.filterIsInstance()

    override fun onLeftClick(player: Player, hand: Player.Hand): Unit = runBlocking {
        _clicks.emit(Click.Left(player, hand))
    }

    override fun onRightClick(player: Player, hand: Player.Hand): Unit = runBlocking {
        _clicks.emit(Click.Right(player, hand))
    }

    public sealed class Click(
        public val player: Player,
        public val hand: Player.Hand
    ) {
        public class Left(player: Player, hand: Player.Hand) : Click(player, hand)
        public class Right(player: Player, hand: Player.Hand) : Click(player, hand)

        public operator fun component1(): Player = player
        public operator fun component2(): Player.Hand = hand
    }
}

/**
 * DSL for KItem.
 *
 * @param material The material of the item
 * @param amount The amount of item to have
 * @param init The DSL lambda
 */
public inline fun kitem(material: Material = Material.PAPER, amount: Byte = 1, init: KItem.() -> Unit): KItem =
    KItem(material, amount).apply(init)