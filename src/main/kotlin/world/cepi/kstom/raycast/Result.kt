package world.cepi.kstom.raycast

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.LivingEntity

/**
 * The result of a ray cast.
 */
data class Result(
    /** The last position this ray cast checked before it stopped. */
    val finalPosition: Point,
    /** What type of object it hit. */
    val hitType: HitType,
    /** The entity it hit, if any. */
    val hitEntity: LivingEntity?
)
