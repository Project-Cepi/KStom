package world.cepi.kstom

import net.minestom.server.instance.Chunk
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.Position
import net.minestom.server.utils.block.BlockUtils
import net.minestom.server.utils.chunk.ChunkUtils

public fun Chunk.getBlockStateId(position: BlockPosition): Short = this.getBlockStateId(position.x, position.y, position.z)

public fun Chunk.getCustomBlockId(position: BlockPosition): Short = this.getCustomBlockId(position.x, position.y, position.z)

public fun Instance.blockUtilsAt(position: BlockPosition): BlockUtils = BlockUtils(this, position)
public fun BlockPosition.blockUtilsIn(instance: Instance): BlockUtils = BlockUtils(instance, this)

public fun Instance.chunksInRange(position: Position, range: Int): List<Pair<Int, Int>> = ChunkUtils
    .getChunksInRange(position, range)
    .map { ChunkUtils.getChunkCoordX(it) to ChunkUtils.getChunkCoordZ(it) }
public fun Instance.isChunkLoaded(x: Double, y: Double): Boolean = ChunkUtils.isLoaded(this, x, y)
