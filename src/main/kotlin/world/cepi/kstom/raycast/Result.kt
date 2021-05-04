package world.cepi.kstom.raycast

import net.minestom.server.entity.LivingEntity
import net.minestom.server.utils.Vector

/**
 * The result of a ray cast.
 */
data class Result(
    /** The last position this ray cast checked before it stopped. */
    val finalPosition: Vector,
    /** What type of object it hit. */
    val hitType: HitType,
    /** The entity it hit, if any. */
    val hitEntity: LivingEntity?
)
