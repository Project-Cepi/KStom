package world.cepi.kstom.command.arguments.annotations

import java.time.temporal.ChronoUnit
import net.minestom.server.utils.time.Tick

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultTickDuration(
    /** The amount of time in milliseconds */
    val amount: Long,
    val isClient: Boolean = false
)


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultChronoDuration(
    /** The amount of time in milliseconds */
    val amount: Long,
    val timeUnit: ChronoUnit
)
