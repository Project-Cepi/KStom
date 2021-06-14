package world.cepi.kstom.command.arguments.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultNumber(val number: Double)
