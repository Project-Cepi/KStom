package world.cepi.kstom.command.arguments.generation.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultBlock(val block: String)
