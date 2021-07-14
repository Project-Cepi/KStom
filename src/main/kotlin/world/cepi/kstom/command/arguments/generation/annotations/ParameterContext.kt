package world.cepi.kstom.command.arguments.generation.annotations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ParameterContext(val parser: KClass<*>)
