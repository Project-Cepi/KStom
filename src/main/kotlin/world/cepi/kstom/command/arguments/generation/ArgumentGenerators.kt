package world.cepi.kstom.command.arguments.generation

import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.coordinate.Point
import net.minestom.server.item.Material
import net.minestom.server.potion.PotionEffect
import net.minestom.server.sound.SoundEvent
import world.cepi.kstom.command.arguments.*
import world.cepi.kstom.command.arguments.generation.annotations.*
import world.cepi.kstom.tree.CombinationNode
import java.util.*
import kotlin.IllegalStateException
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmName

data class PotentialArgument(val name: String, val clazz: KClass<*>, val annotations: List<Annotation>)

val whitelistedSealedClasses = arrayOf(
    SoundEvent::class,
    Material::class,
    PotionEffect::class,
    Point::class
)

/**
 * Can generate a list of Arguments from a class.
 *
 * @param T The class (type) to generate from
 *
 * @return A organized hashmap of arguments and its classifier
 *
 * @throws NullPointerException If the constructor or any arguments are invalid.
 */
inline fun <reified T : Any> generateSyntaxes() = generateSyntaxes(T::class)

fun KClass<*>.bestConstructor() = constructors.firstOrNull { con -> con.hasAnnotation<GenerationConstructor>() }
        ?: primaryConstructor
        ?: constructors.firstOrNull()

val KClass<*>.name get() = simpleName ?: jvmName

/**
 * Can generate a list of Arguments from a class.
 *
 * @param clazz The class to generate from
 *
 * @return A organized hashmap of arguments and its classifier
 *
 * @throws Exception If the constructor or any arguments are invalid.
 */
fun generateSyntaxes(clazz: KClass<*>): List<List<Argument<*>>> {
    if (clazz.isSealed && clazz.sealedSubclasses.isNotEmpty()) {
        return clazz.sealedSubclasses.map {
            argumentsFromClass(it) ?: throw IllegalStateException("Could not generate arguments for class $it in $clazz")
        }.flatten()
    }

    return argumentsFromClass(clazz) ?: throw IllegalStateException("Could not generate arguments for class $clazz")
}

fun argumentsFromClass(clazz: KClass<*>) =
    if (clazz.java.isRecord)
        argumentsFromPotentialArguments(clazz.java.recordComponents.map {
            PotentialArgument(
                it.name,
                it.type.kotlin,
                it.annotations.toList()
            )
        })
    else clazz.bestConstructor()?.let { constructor ->
        argumentsFromPotentialArguments(
            constructor.valueParameters.map {
                PotentialArgument(
                    it.name ?: it.kind.name,
                    it.type.classifier as KClass<*>,
                    it.annotations
                )
            }
        )
    }


/**
 * Can generate a list of Arguments from a function.
 *
 * Example:
 * Attack [sealed class], with EnergyAttack(energy: Int) and ClickAttack(clickType: Enum)
 * (damage: Int, attack: Attack)
 *
 * Should generate:
 * (damage: Int, energy: Literal, energy: Int) and (damage: Int, clickType: Literal, clickType: Enum)
 *
 * @param function The function to generate args from.
 *
 * @return A list of lists; the first list is possible argument combinations. The second list is a list of arguments.
 */
fun argumentsFromPotentialArguments(potentialArguments: List<PotentialArgument>): List<List<Argument<*>>> {
    // list of all combinations ordered.
    // EX, if you have [damage: Int, energy, energy: Int] / [damage: Int, clickType, clickType: Enum],
    // the list would be: [[damage]], [[energy, energy: Int], [clickType, clickType: Int]]
    val args: List<Array<Argument<*>>> = potentialArguments.mapIndexed { index, arg ->

        if (arg.clazz.isSealed && !whitelistedSealedClasses.contains(arg.clazz)) {
            // list(list(energy, energy: Int), list(clickType, clickType: Int))
            return@mapIndexed arg.clazz.sealedSubclasses.map { subClass ->
                ArgumentPrintableGroup(
                    subClass.name,
                    arrayOf(
                        (subClass.name).replaceFirstChar { it.lowercase() }.literal(),
                        *(argumentsFromClass(subClass) ?: throw IllegalStateException("Could not generate arguments for class $subClass"))
                            .flatten().toTypedArray()
                    )
                )

            }.toTypedArray() // energy (energy: Int) / clickType (clickType: Enum)
        }

        arrayOf(
            argumentFromClass(
                arg.name,
                arg.clazz,
                arg.annotations,
                potentialArguments.size - 1 == index
            )
        )

    }

    val rootNode = CombinationNode<Argument<*>>(ShellArgument) // empty node

    // list of ((damage)), ((energy, energy: Int), (clickType, clickType: Enum))
    // should turn into:
    // ((damage)): ((energy, energy: Int), (clickType, clickType: Enum))
    args.forEach {
        rootNode.addItemsToLastNodes(*it)
    }

    return rootNode.traverseAndGenerate()
}