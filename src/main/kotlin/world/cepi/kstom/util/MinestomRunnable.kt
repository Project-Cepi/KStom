package world.cepi.kstom.util

import net.minestom.server.timer.Task
import net.minestom.server.timer.TaskSchedule
import world.cepi.kstom.Manager
import java.time.Duration

/**
 * A utility to make it easier to build runnables
 *
 * @author emortal
 * @author DasLixou
 */
abstract class MinestomRunnable : Runnable {
    private var task: Task? = null
    private var delaySchedule: TaskSchedule = TaskSchedule.immediate()
    private var repeatSchedule: TaskSchedule = TaskSchedule.stop()

    constructor(delay: Duration = Duration.ZERO, repeat: Duration = Duration.ZERO) {
        delay(delay)
        repeat(repeat)
    }

    constructor(delay: TaskSchedule = TaskSchedule.immediate(), repeat: TaskSchedule = TaskSchedule.stop()) {
        delay(delay)
        repeat(repeat)
    }

    fun delay(duration: Duration) = this.also { delaySchedule = if(duration != Duration.ZERO) TaskSchedule.duration(duration) else TaskSchedule.immediate() }
    fun delay(schedule: TaskSchedule) = this.also { delaySchedule = schedule }

    fun repeat(duration: Duration) = this.also { repeatSchedule = if(duration != Duration.ZERO) TaskSchedule.duration(duration) else TaskSchedule.stop() }
    fun repeat(schedule: TaskSchedule) = this.also { repeatSchedule = schedule }

    fun schedule(): Task {
        val t = Manager.scheduler.buildTask(this)
            .let { if (delaySchedule != TaskSchedule.immediate()) it.delay(delaySchedule) else it }
            .let { if (repeatSchedule != TaskSchedule.stop()) it.repeat(repeatSchedule) else it }
            .schedule()
        this.task = t
        return t
    }

    fun cancel() = task?.cancel()
}
