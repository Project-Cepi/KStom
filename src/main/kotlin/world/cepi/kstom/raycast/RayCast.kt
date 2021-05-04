package world.cepi.kstom.raycast

import net.minestom.server.collision.BoundingBox
import net.minestom.server.entity.LivingEntity
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.Position
import net.minestom.server.utils.Vector
import world.cepi.kstom.toBlockPosition

/**
 * Ray cast utilities for Minestom.
 */
object RayCast {

    /**
     * Casts a ray from a point, pointing to a direction, and seeing if that ray hits an entity.
     *
     * @param instance The instance to cast the ray in.
     * @param origin The entity requesting this raycast -- used to check if the entity it sees isn't itself
     * @param start The start point of this ray cast.
     * @param direction The direction this raycast should go in. TODO specifics on direction
     * @param maxDistance The max distance this raycast should go in before stopping.
     * @param stepLength The length per step.
     * @param shouldContinue Optional lambda to see if the raycast should stop -- EX at water or a solid block.
     * @param onBlockStep Callback for whenever this raycast completes a step.
     *
     * @return a [Result] (containing the final position, what it found, and the entity it found if any.)
     */
    public fun castRay(
        instance: Instance,
        origin: LivingEntity?,
        start: Vector,
        direction: Vector,
        maxDistance: Double = 100.0,
        stepLength: Double = 1.0,
        shouldContinue: (BlockPosition) -> Boolean = { true },
        onBlockStep: (BlockPosition) -> Unit = { }
    ): Result {

        /*
         Normalize the direction, making it less/equal to (1, 1, 1)
          then multiply by step to properly add to the step length.
         */
        direction.normalize().multiply(stepLength)

        val reachedPosition = mutableSetOf<BlockPosition>()

        // Wrap the start in a block position variable -- initialized to stop making the same object again
        val blockPos = BlockPosition(start)

        // current step, always starts at the origin.
        var step = 0.0

        // step again and again until the max distance is reached.
        while (step < maxDistance) {

            // checks the [shouldContinue] lambda, if it returns false this most likely hit some sort of block.
            if (!shouldContinue.invoke(blockPos)) {
                return Result(start, HitType.BLOCK, null)
            }

            // checks if there is an entity in this step -- if so, return that.
            val target = positionInEntity(instance, start.toPosition(), origin)
            if (target != null) {
                return Result(start, HitType.ENTITY, target)
            }

            if (!reachedPosition.contains(blockPos)) {
                reachedPosition.add(BlockPosition(blockPos.x, blockPos.y, blockPos.z))
                onBlockStep.invoke(blockPos)
            }

            // add the precalculated direction to the block position
            blockPos.add(direction.toBlockPosition())

            step += stepLength
        }

        return Result(start, HitType.NONE, null)
    }

    /**
     * Check if this position is inside a [LivingEntity]
     *
     * @param instance The instance to find the entities in.
     * @param position The position to check against the entities
     * @param origin The entity who requested this function, used to make sure it doesn't intersect with itself.
     */
    private fun positionInEntity(instance: Instance, position: Position, origin: LivingEntity?): LivingEntity? {
        // get all the entities in the chunk
        val chunkEntities = instance.getChunkEntities(instance.getChunkAt(position))

        // find the first entity that isn't this entity and that the position is in this entity.
        return chunkEntities
            .filterIsInstance<LivingEntity>()
            .firstOrNull { it != origin && collides(it.boundingBox, position) }
    }

    private fun collides(boundingBox: BoundingBox, rayPos: Position): Boolean {
        return (minX(rayPos) <= boundingBox.maxX && maxX(rayPos) >= boundingBox.minX) &&
                (minY(rayPos) <= boundingBox.maxY && maxY(rayPos) >= boundingBox.minY) &&
                (minZ(rayPos) <= boundingBox.maxZ && maxZ(rayPos) >= boundingBox.minZ)
    }

    // Fuzzy positioning logic, leaves room for human error

    private fun minX(position: Position) = position.x - 0.125

    private fun maxX(position: Position) = position.x + 0.125

    private fun minY(position: Position) = position.y - 0.125

    private fun maxY(position: Position) = position.y + 0.125

    private fun minZ(position: Position) = position.z - 0.125

    private fun maxZ(position: Position) = position.z + 0.125
}