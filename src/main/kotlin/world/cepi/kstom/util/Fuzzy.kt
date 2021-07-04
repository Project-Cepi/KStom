package world.cepi.kstom.util

import net.minestom.server.collision.BoundingBox
import net.minestom.server.entity.LivingEntity
import net.minestom.server.instance.Instance
import net.minestom.server.utils.Position
import net.minestom.server.utils.Vector
import java.util.concurrent.ThreadLocalRandom

object Fuzzy {

    fun collides(boundingBox: BoundingBox, pos: Position, margin: Double = 0.125) =
        boundingBox.expand(margin, margin, margin).intersect(pos)

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
            .firstOrNull { it != origin && collides(it.boundingBox, position, margin) }
    }

}

/**
 * Spreads a vector by another vector. Useful for accuracy changes.
 */
fun Vector.spread(spread: Double): Vector {
    val vec = this.clone()
    if (spread == 0.0) return vec;
    val threadLocalRandom = ThreadLocalRandom.current()

    vec.rotateAroundX(threadLocalRandom.nextDouble(-spread, spread))
    vec.rotateAroundY(threadLocalRandom.nextDouble(-spread, spread))
    vec.rotateAroundZ(threadLocalRandom.nextDouble(-spread, spread))

    return vec
}