package world.cepi.kstom.command.arguments.annotations

import net.minestom.server.utils.time.TimeUnit

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultUpdateOption(val amount: Long, val timeUnit: TimeUnit)
