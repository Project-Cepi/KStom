package world.cepi.kstom.command.arguments.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class MaxAmount(val max: Double)