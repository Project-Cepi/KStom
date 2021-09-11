package world.cepi.kstom.raycast

import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import net.minestom.server.entity.LivingEntity
import net.minestom.server.instance.Instance
import world.cepi.kstom.util.Fuzzy
import world.cepi.kstom.util.blockUtilsAt
import world.cepi.kstom.util.toExactBlockPosition

/**
 * Ray cast utilities for Minestom.
 *
 * @author AtomIsHere
 */
object RayCast {

    /**
     * Casts a ray from a point, pointing to a direction, and seeing if that ray hits an entity.
     *
     * @param instance The instance to cast the ray in.
     * @param origin The entity requesting this raycast. Used to skip itself if it sees itself.
     * @param start The start point of this ray cast. Can't be (0, 0, 0)
     * @param direction The direction this raycast should go in. TODO specifics on direction
     * @param maxDistance The max distance this raycast should go in before stopping. Must be greater than 0.
     * @param stepLength The length per step. Must be greater than 0.
     * @param shouldContinue Optional lambda to see if the raycast should stop -- EX at water or a solid block.
     * @param onBlockStep Callback for whenever this raycast completes a step.
     * @param margin Any extra padding for this RayCast
     *
     * @return a [Result] (containing the final position, what it found, and the entity it found if any.)
     */
    fun castRay(
        instance: Instance,
        origin: LivingEntity? = null,
        start: Point,
        direction: Vec,
        maxDistance: Double = 100.0,
        stepLength: Double = .25,
        shouldContinue: (Point) -> Boolean = { !instance.getBlock(it).isSolid },
        onBlockStep: (Point) -> Unit = { },
        acceptEntity: (Point, Entity) -> Boolean = { _, _ -> true },
        margin: Double = 0.125
    ): Result {
        require(maxDistance > 0) { "Max distance must be greater than 0!" }
        require(stepLength > 0) { "Step length must be greater than 0!" }

        /*
         Normalize the direction, making it less/equal to (1, 1, 1)
          then multiply by step to properly add to the step length.
         */
        val adjustedDirection = direction.normalize().mul(stepLength)

        var lastPos = start
        var currentPos = start

        // current step, always starts at the origin.
        var step = 0.0

        // step again and again until the max distance is reached.
        while (step < maxDistance) {

            // checks the [shouldContinue] lambda, if it returns false this most likely hit some sort of block.
            if (!shouldContinue.invoke(currentPos)) {
                return Result(currentPos, HitType.BLOCK, null)
            }

            // checks if there is an entity in this step -- if so, return that.
            val target = Fuzzy.positionInEntity(instance, currentPos, origin, margin)
            if (target != null && acceptEntity(currentPos, target)) {
                return Result(currentPos, HitType.ENTITY, target)
            }

            if (lastPos != currentPos) {
                onBlockStep.invoke(currentPos)
            }

            // add the precalculated direction to the block position, and refresh the lastBlockCache
            lastPos = currentPos
            currentPos = currentPos.add(adjustedDirection)

            step += stepLength
        }

        return Result(currentPos, HitType.NONE, null)
    }
}