package world.cepi.kstom.util

import net.minestom.server.collision.BoundingBox
import net.minestom.server.entity.LivingEntity
import net.minestom.server.instance.Instance
import net.minestom.server.utils.Position

object Fuzzy {

    fun minX(position: Position, margin: Double = 0.125) = position.x - margin

    fun maxX(position: Position, margin: Double = 0.125) = position.x + margin

    fun minY(position: Position, margin: Double = 0.125) = position.y - margin

    fun maxY(position: Position, margin: Double = 0.125) = position.y + margin

    fun minZ(position: Position, margin: Double = 0.125) = position.z - margin

    fun maxZ(position: Position, margin: Double = 0.125) = position.z + margin

    fun collides(boundingBox: BoundingBox, pos: Position, margin: Double = 0.125): Boolean {
        return (minX(pos, margin) <= boundingBox.maxX && maxX(pos, margin) >= boundingBox.minX) &&
                (minY(pos, margin) <= boundingBox.maxY && maxY(pos, margin) >= boundingBox.minY) &&
                (minZ(pos, margin) <= boundingBox.maxZ && maxZ(pos, margin) >= boundingBox.minZ)
    }

    /**
     * Check if this position is inside a [LivingEntity]
     *
     * @param instance The instance to find the entities in.
     * @param position The position to check against the entities
     * @param origin The entity who requested this function, used to make sure it doesn't intersect with itself.
     * @param margin The extra margin this padding can contain (ex raycasting, shooting a hitscan)
     */
    fun positionInEntity(
        instance: Instance,
        position: Position,
        origin: LivingEntity?,
        margin: Double = 0.125
    ): LivingEntity? {
        // get all the entities in the chunk
        val chunkEntities = instance.getChunkEntities(instance.getChunkAt(position))

        // find the first entity that isn't this entity and that the position is in this entity.
        return chunkEntities
            .filterIsInstance<LivingEntity>()
            .firstOrNull { it != origin && Fuzzy.collides(it.boundingBox, position, margin) }
    }

}