package world.cepi.kstom.util

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Entity
import net.minestom.server.instance.Instance
import net.minestom.server.utils.entity.EntityUtils
import java.util.function.Consumer

fun Instance.forEachRange(position: Point, viewDistance: Int, consumer: Consumer<Entity>): Unit =
    EntityUtils.forEachRange(this, position, viewDistance, consumer)

fun Entity.isVisibleTo(other: Entity): Boolean = EntityUtils.areVisible(this, other)

/**
 * Get's the entity's eye location as a [Point] object
 *
 * @author emortal
 */
fun Entity.eyePosition(): Point {
    if (this.isSneaking) return position.add(0.0, 1.23, 0.0)
    return position.add(0.0, 1.53, 0.0)
}