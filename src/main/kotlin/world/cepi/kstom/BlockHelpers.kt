package world.cepi.kstom

import net.minestom.server.instance.Chunk
import net.minestom.server.utils.BlockPosition

fun Chunk.getBlockStateId(position: BlockPosition) = this.getBlockStateId(position.x, position.y, position.z)

fun Chunk.getCustomBlockId(position: BlockPosition) = this.getCustomBlockId(position.x, position.y, position.z)