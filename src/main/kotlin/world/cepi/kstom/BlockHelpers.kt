package world.cepi.kstom

import net.minestom.server.instance.Chunk
import net.minestom.server.utils.BlockPosition

public fun Chunk.getBlockStateId(position: BlockPosition): Short = this.getBlockStateId(position.x, position.y, position.z)

public fun Chunk.getCustomBlockId(position: BlockPosition): Short = this.getCustomBlockId(position.x, position.y, position.z)