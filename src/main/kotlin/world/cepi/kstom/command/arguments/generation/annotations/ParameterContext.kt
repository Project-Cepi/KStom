package world.cepi.kstom.command.arguments.generation.annotations

import world.cepi.kstom.command.arguments.context.ContextParser
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ParameterContext(val parser: KClass<ContextParser<*>>)
