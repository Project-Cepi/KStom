package world.cepi.kstom.util

import net.minestom.server.collision.BoundingBox
import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Entity

fun Entity.intersect(point: Point, preferredBoundingBox: BoundingBox = boundingBox) =
    position.x + preferredBoundingBox.minX() <= point.x && position.x + preferredBoundingBox.maxX() >= point.x &&
            position.y + preferredBoundingBox.minY() <= point.y && position.y + preferredBoundingBox.maxY() >= point.y &&
            position.z + preferredBoundingBox.minZ() <= point.z && position.z + preferredBoundingBox.maxZ() >= point.z

fun Entity.intersect(entity: Entity) {
    this.boundingBox.intersectEntity(this.position, entity)
}

fun BoundingBox.expand(amount: Double) = expand(amount, amount, amount)