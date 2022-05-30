package world.cepi.kstom.nbt

import net.minestom.server.instance.block.Block
import net.minestom.server.instance.block.BlockFace

object BlockFaceUtils {

    fun fromFacing(facing: String) = when (facing) {
        "down" -> BlockFace.BOTTOM
        "up" -> BlockFace.TOP
        "east" -> BlockFace.EAST
        "south" -> BlockFace.SOUTH
        "west" -> BlockFace.WEST
        "north" -> BlockFace.NORTH
        else -> throw IllegalArgumentException("Argument '$facing' isn't a facing")
    }

}

fun Block.hasFacing(): Boolean = properties().containsKey("facing")

fun Block.getFacing(): BlockFace = BlockFaceUtils.fromFacing(getProperty("facing"))

fun BlockFace.getFacing(): String = when (this) {
    BlockFace.BOTTOM -> "down"
    BlockFace.TOP -> "up"
    BlockFace.EAST -> "east"
    BlockFace.SOUTH -> "south"
    BlockFace.WEST -> "west"
    BlockFace.NORTH -> "north"
}

fun BlockFace.applyFacing(block: Block): Block = block.withProperty("facing", getFacing())

/**
 * Rotates faces clockwise
 * Bottom and Top will remain the same
 */
fun BlockFace.next(): BlockFace = when (this) {
    BlockFace.BOTTOM -> BlockFace.BOTTOM
    BlockFace.TOP -> BlockFace.TOP
    BlockFace.EAST -> BlockFace.SOUTH
    BlockFace.SOUTH -> BlockFace.WEST
    BlockFace.WEST -> BlockFace.NORTH
    BlockFace.NORTH -> BlockFace.EAST
}

/**
 * Rotates faces counterclockwise
 * Bottom and Top will remain the same
 */
fun BlockFace.previous(): BlockFace = when (this) {
    BlockFace.BOTTOM -> BlockFace.BOTTOM
    BlockFace.TOP -> BlockFace.TOP
    BlockFace.EAST -> BlockFace.NORTH
    BlockFace.SOUTH -> BlockFace.EAST
    BlockFace.WEST -> BlockFace.SOUTH
    BlockFace.NORTH -> BlockFace.WEST
}