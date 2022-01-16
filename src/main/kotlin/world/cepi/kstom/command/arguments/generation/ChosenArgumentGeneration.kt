package world.cepi.kstom.command.arguments.generation

import world.cepi.kstom.command.kommand.Kommand
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class ChosenArgumentGeneration<T : Any>(override val clazz: KClass<T>): ArgumentGenerator<T>(clazz, run {
    if (clazz.isSubclassOf(CustomArgumentGeneration::class)) {
        (clazz as CustomArgumentGeneration<*>).argumentsForGeneration
    } else {
        generateSyntaxes(clazz)
    }
}) {
    override fun generate(syntax: Kommand.SyntaxContext, args: List<String>, index: Int): T {
        return if (clazz.isSubclassOf(CustomArgumentGeneration::class)) {
            (clazz as CustomArgumentGeneration<T>).generate(syntax, args, index)
        } else {
            ClassArgumentGenerator(clazz).generate(syntax, args, index)
        }
    }
}