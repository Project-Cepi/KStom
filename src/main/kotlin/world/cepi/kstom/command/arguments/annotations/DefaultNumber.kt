package world.cepi.kstom.command.arguments.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class DefaultNumber(val number: Double)
