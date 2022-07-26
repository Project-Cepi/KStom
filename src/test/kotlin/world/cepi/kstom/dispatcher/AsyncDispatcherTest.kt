package world.cepi.kstom.dispatcher

import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.minestom.server.ServerProcess
import net.minestom.server.timer.ExecutionType
import net.minestom.server.timer.SchedulerManager
import net.minestom.server.timer.Task

class AsyncDispatcherTest : StringSpec({

    "Should not perform if process is not alive" {
        val process = mockk<ServerProcess>()
        every { process.isAlive } returns false
        val schedulerManager = mockk<SchedulerManager>()
        every { process.scheduler() } returns schedulerManager

        val dispatcher = AsyncCoroutineDispatcher(process)
        dispatcher.dispatch(mockk()) {}

        verify(exactly = 0) { schedulerManager.scheduleNextProcess(any(), any()) }
        verify(exactly = 0) { schedulerManager.scheduleNextTick(any()) }
    }

    "Should perform task in sync context" {
        val process = mockk<ServerProcess>()
        every { process.isAlive } returns true
        val schedulerManager = mockk<SchedulerManager>()
        every { process.scheduler() } returns schedulerManager
        every { schedulerManager.scheduleNextProcess(mockk(), mockk()) } returns mockk()

        val dispatcher = SyncCoroutineDispatcher(process)

        val runnable = mockk<Runnable>()
        dispatcher.dispatch(mockk(), runnable)

        verify(exactly = 1) { schedulerManager.scheduleNextProcess(runnable, ExecutionType.ASYNC) }
        verify(exactly = 0) { schedulerManager.scheduleNextTick(any()) }
    }

})