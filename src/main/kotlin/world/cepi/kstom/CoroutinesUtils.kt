package world.cepi.kstom

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

public val IOContext: ExecutorCoroutineDispatcher = Executors
    .newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    .asCoroutineDispatcher()
public val IOScope: CoroutineScope = CoroutineScope(IOContext)