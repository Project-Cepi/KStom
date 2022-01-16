package world.cepi.kstom.command.arguments.generation

import world.cepi.kstom.command.kommand.Kommand
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.isSubclassOf

class ChosenArgumentGeneration<T : Any>(override val clazz: KClass<T>): ArgumentGenerator<T>(clazz, run {
    if (clazz.companionObjectInstance is CustomArgumentGeneration<*>) {
        (clazz.companionObjectInstance as CustomArgumentGeneration<*>).argumentsForGeneration
    } else {
        generateSyntaxes(clazz)
    }
}) {
    override fun generate(syntax: Kommand.SyntaxContext, args: List<String>, index: Int): T {
        return if (clazz.companionObjectInstance is CustomArgumentGeneration<*>) {
            (clazz.companionObjectInstance as CustomArgumentGeneration<T>).generate(syntax, args, index)
        } else {
            ClassArgumentGenerator(clazz).generate(syntax, args, index)
        }
    }
}