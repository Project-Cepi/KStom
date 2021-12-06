package world.cepi.kstom.command.arguments.generation.annotations

import world.cepi.kstom.command.arguments.generation.CustomArgumentGenerator
import kotlin.reflect.KClass

annotation class CustomArgument(val generator: KClass<CustomArgumentGenerator>)
