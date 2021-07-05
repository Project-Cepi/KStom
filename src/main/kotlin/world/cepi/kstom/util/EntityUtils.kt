package world.cepi.kstom.util

import net.minestom.server.entity.Entity
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.Instance
import net.minestom.server.utils.Position
import net.minestom.server.utils.entity.EntityUtils
import java.util.function.Consumer

public fun Instance.forEachRange(position: Position, viewDistance: Int, consumer: Consumer<Entity>): Unit =
    EntityUtils.forEachRange(this, position, viewDistance, consumer)

public fun Entity.isVisibleTo(other: Entity): Boolean = EntityUtils.areVisible(this, other)

/**
 * Get's the entity's eye location as a [Position] object
 */
fun Entity.eyePosition(): Position {
    if (this.isSneaking) return position.clone().add(0.0, 1.23, 0.0)
    return position.clone().add(0.0, 1.53, 0.0)
}