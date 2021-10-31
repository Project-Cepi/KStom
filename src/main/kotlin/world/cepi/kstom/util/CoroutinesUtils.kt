package world.cepi.kstom.util

import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.entity.fakeplayer.FakePlayer
import net.minestom.server.entity.fakeplayer.FakePlayerOption
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.IChunkLoader
import net.minestom.server.instance.Instance
import net.minestom.server.instance.InstanceContainer
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun FakePlayer(uuid: UUID, username: String, option: FakePlayerOption = FakePlayerOption()): FakePlayer = suspendCoroutine { cont ->
    FakePlayer.initPlayer(uuid, username, option) {
        cont.resume(it)
    }
}

suspend fun Entity.waitNextTick(): Entity = suspendCoroutine { cont -> scheduleNextTick { cont.resume(it) } }

suspend fun Entity.suspendTeleport(position: Pos): Unit = suspendCoroutine { teleport(position).whenComplete { _, _ -> it.resume(Unit) } }
suspend fun Player.suspendTeleport(position: Pos): Unit = suspendCoroutine { teleport(position).whenComplete { _, _ -> it.resume(Unit) } }

suspend fun IChunkLoader.saveChunk(chunk: Chunk): Unit = suspendCoroutine { saveChunk(chunk).whenComplete { _, _ -> it.resume(Unit) } }
suspend fun IChunkLoader.saveChunks(chunk: Collection<Chunk>): Unit = suspendCoroutine { saveChunks(chunk).whenComplete { _, _ -> it.resume(Unit) } }

suspend fun Instance.suspendSaveChunkToStorage(chunk: Chunk): Unit = suspendCoroutine { saveChunkToStorage(chunk).whenComplete { _, _ -> it.resume(Unit) } }
suspend fun Instance.suspendSaveChunksToStorage(): Unit = suspendCoroutine { saveChunksToStorage().whenComplete { _, _ -> it.resume(Unit) } }

suspend fun InstanceContainer.suspendSaveInstance(): Unit = suspendCoroutine { saveInstance().whenComplete { _, _ -> it.resume(Unit) } }
