package world.cepi.kstom.util

import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.Instance
import net.minestom.server.utils.block.BlockUtils
import net.minestom.server.utils.chunk.ChunkUtils

fun Instance.blockUtilsAt(position: Pos): BlockUtils = BlockUtils(this, position)

fun chunksInRange(position: Pos, range: Int): List<Pair<Int, Int>> = ChunkUtils
    .getChunksInRange(position, range)
    .map { ChunkUtils.getChunkCoordX(it) to ChunkUtils.getChunkCoordZ(it) }

fun Instance.isChunkLoaded(x: Double, y: Double): Boolean = ChunkUtils.isLoaded(this, x, y)