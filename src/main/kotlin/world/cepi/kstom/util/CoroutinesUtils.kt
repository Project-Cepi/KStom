package world.cepi.kstom.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.entity.fakeplayer.FakePlayer
import net.minestom.server.entity.fakeplayer.FakePlayerOption
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.IChunkLoader
import net.minestom.server.instance.Instance
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.utils.Position
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

public val IOContext: ExecutorCoroutineDispatcher = Executors
    .newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    .asCoroutineDispatcher()

public val IOScope: CoroutineScope = CoroutineScope(IOContext)

public suspend fun FakePlayer(uuid: UUID, username: String, option: FakePlayerOption = FakePlayerOption()): FakePlayer = suspendCoroutine { cont ->
    FakePlayer.initPlayer(uuid, username, option) {
        cont.resume(it)
    }
}

public suspend fun Entity.waitNextTick(): Entity = suspendCoroutine { cont -> scheduleNextTick { cont.resume(it) } }

public suspend fun Entity.suspendTeleport(position: Position): Unit = suspendCoroutine { teleport(position) { it.resume(Unit) } }
public suspend fun Player.suspendTeleport(position: Position): Unit = suspendCoroutine { teleport(position) { it.resume(Unit) } }

public suspend fun IChunkLoader.saveChunk(chunk: Chunk): Unit = suspendCoroutine { saveChunk(chunk) { it.resume(Unit) } }
public suspend fun IChunkLoader.saveChunks(chunk: Collection<Chunk>): Unit = suspendCoroutine { saveChunks(chunk) { it.resume(Unit) } }

public suspend fun Instance.suspendSaveChunkToStorage(chunk: Chunk): Unit = suspendCoroutine { saveChunkToStorage(chunk) { it.resume(Unit) } }
public suspend fun Instance.suspendSaveChunksToStorage(): Unit = suspendCoroutine { saveChunksToStorage { it.resume(Unit) } }

public suspend fun InstanceContainer.suspendSaveInstance(): Unit = suspendCoroutine { saveInstance { it.resume(Unit) } }
