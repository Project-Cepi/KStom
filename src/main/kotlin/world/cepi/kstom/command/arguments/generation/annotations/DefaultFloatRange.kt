package world.cepi.kstom.command.arguments.generation.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultFloatRange(val minimum: Float, val maximum: Float)
