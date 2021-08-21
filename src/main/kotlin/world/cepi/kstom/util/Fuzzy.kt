package world.cepi.kstom.util

import net.minestom.server.collision.BoundingBox
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.LivingEntity
import net.minestom.server.instance.Instance
import java.util.concurrent.ThreadLocalRandom

object Fuzzy {

    fun collides(boundingBox: BoundingBox, pos: Pos, margin: Double = 0.125) =
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
        position: Pos,
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
 * Spreads each axis of a vector individually. Useful for accuracy changes.
 *
 * @author emortal
 */
fun Vec.spread(spreadX: Double, spreadY: Double, spreadZ: Double, random: ThreadLocalRandom = ThreadLocalRandom.current()): Vec {
    if (spreadX == 0.0 && spreadY == 0.0 && spreadZ == 0.0) return this

    return rotate(
        random.nextDouble(-spreadX, spreadX),
        random.nextDouble(-spreadY, spreadY),
        random.nextDouble(-spreadZ, spreadZ)
    )
}

/**
 * Spreads a vector by all 3 axis. Useful for accuracy changes.
 *
 * @author emortal
 */
fun Vec.spread(spread: Double, random: ThreadLocalRandom = ThreadLocalRandom.current()): Vec =
    this.spread(spread, spread, spread, random)
