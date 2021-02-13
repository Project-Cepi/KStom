package world.cepi.kstom

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import net.minestom.server.MinecraftServer
import net.minestom.server.event.Event
import java.util.concurrent.Executors

public val IOContext: ExecutorCoroutineDispatcher = Executors
    .newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    .asCoroutineDispatcher()
public val IOScope: CoroutineScope = CoroutineScope(IOContext)

private const val eventBufferSize = 100