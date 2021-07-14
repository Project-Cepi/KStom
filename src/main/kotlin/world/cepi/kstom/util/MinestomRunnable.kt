package world.cepi.kstom.util

import net.minestom.server.timer.Task
import world.cepi.kstom.Manager
import java.time.Duration

/**
 * A utility to make it easier to build runnables
 *
 * @author emortal
 */
abstract class MinestomRunnable : Runnable {
    private var t: Task? = null
    private var repeatDuration: Duration = Duration.ZERO
    private var delayDuration: Duration = Duration.ZERO

    fun delay(duration: Duration): MinestomRunnable {
        delayDuration = duration
        return this
    }

    fun repeat(duration: Duration): MinestomRunnable {
        repeatDuration = duration
        return this
    }

    fun schedule(): Task {
        val t = Manager.scheduler.buildTask(this)
            .delay(delayDuration)
            .repeat(repeatDuration)
            .schedule()

        this.t = t
        return t
    }

    fun cancel() {
        if (t == null) return
        t!!.cancel()
    }
}