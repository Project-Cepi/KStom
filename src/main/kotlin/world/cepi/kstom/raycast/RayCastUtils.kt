package world.cepi.kstom.raycast

import net.minestom.server.collision.BoundingBox
import net.minestom.server.entity.LivingEntity
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.Position
import net.minestom.server.utils.Vector
import kotlin.math.floor

public fun castRay(instance: Instance, origin: LivingEntity?, start: Vector, direction: Vector, maxDistance: Double, stepLength: Double, shouldContinue: (BlockPosition) -> Boolean, onBlockStep: (BlockPosition) -> Unit): Result {
    return castRay(instance, origin, start.x, start.y, start.z, direction.x, direction.y, direction.z, maxDistance, stepLength, shouldContinue, onBlockStep)
}

public fun castRay(instance: Instance, origin: LivingEntity?, startX: Double, startY: Double, startZ: Double, dirX: Double, dirY: Double, dirZ: Double, maxDistance: Double, stepLength: Double, shouldContinue: (BlockPosition) -> Boolean, onBlockStep: (BlockPosition) -> Unit): Result {
    val ray = Vector(dirX, dirY, dirZ)
    ray.normalize().multiply(stepLength)

    val position = Vector(startX, startY, startZ)
    val blockPos = BlockPosition(position)
    val reachedPosition = mutableSetOf<BlockPosition>()

    var hit: Result? = null
    var step = 0.0
    while(step < maxDistance) {
        blockPos.x = floor(position.x).toInt()
        blockPos.y = floor(position.y).toInt()
        blockPos.z = floor(position.z).toInt()

        if(blockPos.y < 0 || blockPos.y >= 255) {
            hit = Result(position, HitType.OUT_OF_BOUNDS, null)
            break
        }

        if(!shouldContinue.invoke(blockPos)) {
            hit = Result(position, HitType.BLOCK, null)
            break
        }

        val target = getLookingAt(instance, position.toPosition(), origin)
        if(target != null) {
            hit = Result(position, HitType.ENTITY, target)
            break
        }

        if(!reachedPosition.contains(blockPos)) {
            reachedPosition.add(BlockPosition(blockPos.x, blockPos.y, blockPos.z))
            onBlockStep.invoke(blockPos)
        }

        position.add(ray.x, ray.y, ray.z)

        step += stepLength
    }

    if(hit == null) {
        return Result(position, HitType.NONE, null)
    }
    return hit
}

private fun getLookingAt(instance: Instance, position: Position, origin: LivingEntity?): LivingEntity? {
    val chunkEntities = instance.getChunkEntities(instance.getChunkAt(position))
    for(check in chunkEntities) {
        if(check != origin && collides(check.boundingBox, position) && check is LivingEntity) {
            return check
        }
    }

    return null
}

private fun collides(boundingBox: BoundingBox, rayPos: Position): Boolean {
    return (getMinX(rayPos) <= boundingBox.maxX && getMaxX(rayPos) >= boundingBox.minX) &&
            (getMinY(rayPos) <= boundingBox.maxY && getMaxY(rayPos) >= boundingBox.minY) &&
            (getMinZ(rayPos) <= boundingBox.maxZ && getMaxZ(rayPos) >= boundingBox.minZ)
}

private fun getMinX(position: Position): Double {
    return position.x - 0.125
}

private fun getMaxX(position: Position): Double {
    return position.x + 0.125
}

private fun getMinY(position: Position): Double {
    return position.y - 0.125
}

private fun getMaxY(position: Position): Double {
    return position.y + 0.125
}

private fun getMinZ(position: Position): Double {
    return position.z - 0.125
}

private fun getMaxZ(position: Position): Double {
    return position.z + 0.125
}