package world.cepi.kstom.command.arguments.generation

import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.entity.Entity
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeVec
import world.cepi.kstom.command.arguments.ArgumentContext
import world.cepi.kstom.command.arguments.ArgumentContextValue
import world.cepi.kstom.command.arguments.generation.annotations.GenerationConstructor
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.kstom.serializer.SerializableEntityFinder
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters

class ClassArgumentGenerator<T : Any>(override val clazz: KClass<T>): ArgumentGenerator<T>(
    clazz,
    generateSyntaxes(clazz)
) {

    override fun generate(syntax: Kommand.SyntaxContext, args: List<String>, fullIndex: Int): T = with(syntax) {
        val constructor = clazz.constructors
            .firstOrNull { it.hasAnnotation<GenerationConstructor>() }
            ?: clazz.primaryConstructor
            ?: throw IllegalStateException("No constructor found, make sure the class has a constructor!")

        val classes = constructor.valueParameters.map {
            it.type.classifier as KClass<*>
        }

        val generatedArguments = args.mapIndexed { index, argument ->

            val value = context.get<Any>(argument)
            val clazz = classes[index]

            // Entity finder serializable exception
            if (value is EntityFinder) {
                return@mapIndexed SerializableEntityFinder(context.getRaw(argument))
            }

            if (value is ArgumentContextValue<*>) {
                return@mapIndexed value.from(sender)
            }

            if (value is Pair<*, *>) {

                value as Pair<String, CommandContext>

                return@mapIndexed ClassArgumentGenerator(clazz.sealedSubclasses.first { it.simpleName == value.first }).generate(
                    Kommand.SyntaxContext(sender, value.second),
                    value.second.map.keys.toMutableList().also { it.removeAt(0) },
                    -1
                )
            }

            // Handle special context / sub edge cases
            when (value) {
                is RelativeVec -> return@mapIndexed value.from(sender as? Entity)
            }

            return@mapIndexed value
        }

        try {
            return constructor.call(*generatedArguments.toTypedArray())
        } catch (exception: IllegalArgumentException) {
            // Print a more useful debug exception

            throw IllegalArgumentException("Expected types were $classes but received $generatedArguments; input: ${context.input}")
        }
    }

    companion object {
        inline fun <reified T: Any> Kommand.syntaxesFrom(
            vararg arguments: Argument<*>,
            noinline lambda: Kommand.SyntaxContext.(T) -> Unit
        ): ArgumentGenerator<T> = ClassArgumentGenerator(T::class).also { it.applySyntax(this, arguments, emptyArray(), lambda) }

        fun <T : Any> Kommand.syntaxesFrom(
            clazz: KClass<T>,
            vararg arguments: Argument<*>,
            lambda: Kommand.SyntaxContext.(T) -> Unit
        ): ArgumentGenerator<T> = ClassArgumentGenerator(clazz).also { it.applySyntax(this, arguments, emptyArray(), lambda) }

        inline fun <reified T: Any> Kommand.syntaxesFrom(
            beforeArguments: Array<Argument<*>>,
            afterArguments: Array<Argument<*>>,
            noinline lambda: Kommand.SyntaxContext.(T) -> Unit
        ): ArgumentGenerator<T> = ClassArgumentGenerator(T::class).also { it.applySyntax(this, beforeArguments, afterArguments, lambda) }

        fun <T: Any> Kommand.syntaxesFrom(
            clazz: KClass<T>,
            beforeArguments: Array<Argument<*>>,
            afterArguments: Array<Argument<*>>,
            lambda: Kommand.SyntaxContext.(T) -> Unit
        ): ArgumentGenerator<T> = ClassArgumentGenerator(clazz).also { it.applySyntax(this, beforeArguments, afterArguments, lambda) }
    }

}

fun <T: Any> argumentsFromClass(clazz: KClass<T>) = ClassArgumentGenerator(clazz)
inline fun <reified T: Any> argumentsFromClass() = ClassArgumentGenerator(T::class)