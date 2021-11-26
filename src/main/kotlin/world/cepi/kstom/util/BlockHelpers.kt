package world.cepi.kstom.util

import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.Instance
import net.minestom.server.utils.block.BlockUtils
import net.minestom.server.utils.chunk.ChunkUtils
import kotlin.math.floor
import kotlin.math.roundToInt

fun Chunk.getBlockStateId(position: Pos): Short = this.getBlockStateId(position)

fun Chunk.getCustomBlockId(position: Pos): Short = this.getCustomBlockId(position)

fun Instance.blockUtilsAt(position: Pos): BlockUtils = BlockUtils(this, position)
fun Pos.blockUtilsIn(instance: Instance): BlockUtils = BlockUtils(instance, this)

fun Instance.chunksInRange(position: Pos, range: Int): List<Pair<Int, Int>> {

    val list = mutableListOf<Pair<Int, Int>>()

    for (x in -range..range) {
        for (z in -range..range) {
            list.add(position.chunkX() + x to position.chunkZ() + z)
        }
    }

    return list
}
fun Instance.isChunkLoaded(x: Double, y: Double): Boolean = ChunkUtils.isLoaded(this, x, y)

fun Vec.toBlockPosition(): Pos =
    Pos(this.x().roundToInt().toDouble(), this.y().roundToInt().toDouble(), this.z().roundToInt().toDouble())

fun Vec.toExactBlockPosition(): Pos =
    Pos(floor(this.x()).toInt().toDouble(), floor(this.y()).toInt().toDouble(), floor(this.z()).toInt().toDouble())