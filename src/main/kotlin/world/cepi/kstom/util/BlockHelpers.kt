package world.cepi.kstom.util

import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.Instance
import net.minestom.server.utils.block.BlockUtils
import net.minestom.server.utils.chunk.ChunkUtils
import kotlin.math.floor
import kotlin.math.roundToInt

public fun Chunk.getBlockStateId(position: Pos): Short = this.getBlockStateId(position)

public fun Chunk.getCustomBlockId(position: Pos): Short = this.getCustomBlockId(position)

public fun Instance.blockUtilsAt(position: Pos): BlockUtils = BlockUtils(this, position)
public fun Pos.blockUtilsIn(instance: Instance): BlockUtils = BlockUtils(instance, this)

public fun Instance.chunksInRange(position: Pos, range: Int): List<Pair<Int, Int>> = ChunkUtils
    .getChunksInRange(position, range)
    .map { ChunkUtils.getChunkCoordX(it) to ChunkUtils.getChunkCoordZ(it) }
public fun Instance.isChunkLoaded(x: Double, y: Double): Boolean = ChunkUtils.isLoaded(this, x, y)

public fun Vec.toBlockPosition(): Pos =
    Pos(this.x().roundToInt().toDouble(), this.y().roundToInt().toDouble(), this.z().roundToInt().toDouble())

public fun Vec.toExactBlockPosition(): Pos =
    Pos(floor(this.x()).toInt().toDouble(), floor(this.y()).toInt().toDouble(), floor(this.z()).toInt().toDouble())