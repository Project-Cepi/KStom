package world.cepi.kstom.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import net.minestom.server.MinecraftServer
import net.minestom.server.ServerProcess
import net.minestom.server.timer.ExecutionType
import kotlin.coroutines.CoroutineContext

/**
 * @see [SyncCoroutineDispatcher]
 */
val syncDispatcher: CoroutineDispatcher get() = SyncCoroutineDispatcher(MinecraftServer.process())

/**
 * Dispatcher to execute task in a [sync][ExecutionType.SYNC] context of the server.
 * @property serverProcess Server's process
 */
internal class SyncCoroutineDispatcher(
    private val serverProcess: ServerProcess
): CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!serverProcess.isAlive) {
            return
        }

        serverProcess.scheduler().scheduleNextProcess(block, ExecutionType.SYNC)
    }
}