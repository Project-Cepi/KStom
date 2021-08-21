package world.cepi.kstom.util

import net.minestom.server.MinecraftServer
import net.minestom.server.timer.Task
import world.cepi.kstom.Manager
import java.time.Duration

/**
 * A utility to make it easier to build runnables
 *
 * @author emortal
 */
abstract class MinestomRunnable : Runnable {
    private var task: Task? = null
    private var repeatDuration: Duration = Duration.ZERO
    private var delayDuration: Duration = Duration.ZERO

    fun delay(duration: Duration) = this.also { delayDuration = duration }

    fun repeat(duration: Duration) = this.also { repeatDuration = duration }

    fun schedule(): Task {
        val t = Manager.scheduler.buildTask(this)
            .let { if (delayDuration != Duration.ZERO) it.delay(delayDuration) else it }
            .let { if (repeatDuration != Duration.ZERO) it.repeat(repeatDuration) else it }
            .schedule()
        this.task = t
        return t
    }

    fun cancel() = task?.cancel()
}
