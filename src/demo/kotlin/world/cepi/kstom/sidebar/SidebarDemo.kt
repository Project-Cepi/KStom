package world.cepi.kstom.sidebar

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.entity.Player
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerStartFlyingEvent
import net.minestom.server.event.player.PlayerStopFlyingEvent
import world.cepi.kstom.event.listenOnly

fun attatchSidebar(player: Player, node: EventNode<Event>) = sidebar(Component.text("Cool sidebar")) {
    fun flying(isFlying: Boolean) = line("flying",2) {
        Component.text("Flying: ", NamedTextColor.GRAY)
            .append(Component.text(isFlying, NamedTextColor.WHITE))
    }

    val website = line("website", 0, Component.text("Cool Website"))

    add(
        flying(player.isFlying),
        emptyLine("empty", 1),
        website
    )

    node.listenOnly<PlayerStartFlyingEvent> {
        refresh(flying(true))
    }

    node.listenOnly<PlayerStopFlyingEvent> {
        refresh(flying(false))
    }

}
