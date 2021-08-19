package world.cepi.kstom.util

import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.entity.EntityUtils
import java.util.function.Consumer

public fun Instance.forEachRange(position: Pos, viewDistance: Int, consumer: Consumer<Entity>): Unit =
    EntityUtils.forEachRange(this, position, viewDistance, consumer)

public fun Entity.isVisibleTo(other: Entity): Boolean {
    return viewers.contains(other as? Player ?: return true)
}

/**
 * Get's the entity's eye location as a [Position] object
 */
fun Entity.eyePosition(): Pos {
    if (this.isSneaking) return position.add(0.0, 1.23, 0.0)
    return position.add(0.0, 1.53, 0.0)
}