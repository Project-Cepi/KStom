package world.cepi.kstom.command.arguments.generation.annotations

import world.cepi.kstom.command.arguments.generation.DynamicWordGenerator
import kotlin.reflect.KClass

annotation class DynamicWord(val generator: KClass<out DynamicWordGenerator>)
