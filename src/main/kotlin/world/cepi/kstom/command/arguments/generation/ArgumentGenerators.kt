package world.cepi.kstom.command.arguments.generation

import net.kyori.adventure.text.Component
import net.minestom.server.color.Color
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandResult
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.potion.PotionEffect
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.Vector
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeBlockPosition
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.TimeUnit
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.command.SyntaxContext
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.*
import world.cepi.kstom.command.arguments.generation.annotations.*
import world.cepi.kstom.command.arguments.context.ContextParser
import world.cepi.kstom.serializer.SerializableEntityFinder
import world.cepi.kstom.tree.CombinationNode
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.time.Duration
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

class GeneratedArguments<T : Any>(
    val clazz: KClass<T>,
    val args: List<List<Argument<*>>>
) {

    fun applySyntax(
        command: Command,
        vararg arguments: Argument<*>,
        lambda: SyntaxContext.(T) -> Unit
    ) = applySyntax(command, arguments, lambda)

    @JvmName("arrayApplySyntax")
    fun applySyntax(
        command: Command,
        argumentsBefore: Array<out Argument<*>>,
        lambda: SyntaxContext.(T) -> Unit
    ) = args.forEach {
        command.addSyntax(*argumentsBefore, *it.toTypedArray()) {
            val instance = createInstance(clazz, it.map { arg -> arg.id }, context, sender)

            lambda(this, instance)
        }
    }

    companion object {
        fun <T : Any> createInstance(
            clazzToGenerate: KClass<T>,
            currentArgs: List<String>,
            context: CommandContext,
            sender: CommandSender
        ): T {

            val constructor = clazzToGenerate.primaryConstructor
                ?: throw NullPointerException("Constructor is null, make sure the class has a constructor!")

            val classes = constructor.valueParameters.map {
                it.type.classifier as KClass<*>
            }

            val generatedArguments = currentArgs.map { argumentName ->

                val value = context.get<Any>(argumentName)

                // Entity finder serializable exception
                if (value is EntityFinder) {
                    return@map SerializableEntityFinder(context.getRaw(argumentName))
                }

                if (value is ArgumentContextValue<*>) {
                    return@map value.from(sender)
                }

                if (value is Pair<*, *>) {

                    value as Pair<String, CommandContext>

                    println(value.first)
                    return@map createInstance(Class.forName(value.first).kotlin, value.second.map.keys.toList(), value.second, sender)
                }

                // Handle special context / sub edge cases
                when (value) {
                    is RelativeVec -> return@map value.from(sender as? Entity)
                    is RelativeBlockPosition -> return@map value.from(sender as? Entity)
                }

                return@map value
            }

            try {
                return constructor.call(*generatedArguments.toTypedArray())
            } catch (exception: IllegalArgumentException) {
                // Print a more useful debug exception

                throw IllegalArgumentException("Expected types were $classes but received $generatedArguments")
            }
        }

        inline fun <reified T: Any> Command.createSyntaxesFrom(
            vararg arguments: Argument<*>,
            noinline lambda: SyntaxContext.(T) -> Unit
        ) = generateSyntaxes<T>().applySyntax(this, arguments, lambda)
    }

}

/**
 * Can generate a list of Arguments from a class.
 *
 * @param T The class (type) to generate from
 *
 * @return A organized hashmap of arguments and its classifier
 *
 * @throws NullPointerException If the constructor or any arguments are invalid.
 */
public inline fun <reified T : Any> generateSyntaxes(): GeneratedArguments<T> =
    generateSyntaxes(T::class)

/**
 * Can generate a list of Arguments from a class.
 *
 * @param clazz The class to generate from
 *
 * @return A organized hashmap of arguments and its classifier
 *
 * @throws NullPointerException If the constructor or any arguments are invalid.
 */
public fun <T : Any> generateSyntaxes(clazz: KClass<T>): GeneratedArguments<T> {
    if (clazz.isSealed && clazz.sealedSubclasses.isNotEmpty()) {
        return GeneratedArguments(clazz, clazz.sealedSubclasses.map {
            argumentsFromFunction(it.primaryConstructor!!)
        }.flatten())
    }

    return GeneratedArguments(clazz, argumentsFromFunction(clazz.primaryConstructor!!))
}

/**
 * Can generate a list of Arguments from a function.
 *
 * Example:
 * Attack (sealed): (energy: Int) / (clickType: Enum)
 * (damage: Int, attack: Attack)
 *
 * Should generate:
 * (damage: Int, energy, energy: Int) / (damage: Int, clickType, clickType: Enum)
 *
 * @param function The function to generate args from.
 *
 * @return A list of lists; the first list is possible argument combinations. The second list is a list of arguments.
 */
public fun argumentsFromFunction(function: KFunction<*>): List<List<Argument<*>>> {
    // list of all combinations ordered.
    // EX, if you have [damage: Int, energy, energy: Int] / [damage: Int, clickType, clickType: Enum],
    // the list would be: [[damage]], [[energy, energy: Int], [clickType, clickType: Int]]
    val args: List<Array<Argument<*>>> = function.valueParameters.mapIndexed { index, parameter ->

        val clazz = parameter.type.classifier!! as KClass<*>

        if (clazz.isSealed) {
            // list(list(energy, energy: Int), list(clickType, clickType: Int))
            return@mapIndexed clazz.sealedSubclasses.map { subClass ->
                ArgumentPrintableGroup(
                    subClass,
                    arrayOf(
                        subClass.simpleName!!.replaceFirstChar { it.lowercase() }.literal(),
                        *argumentsFromFunction(subClass.primaryConstructor!!)
                            .flatten().toTypedArray()
                    )
                )

            }.toTypedArray() // energy (energy: Int) / clickType (clickType: Enum)
        }

        arrayOf(
            argumentFromClass(
                parameter.name ?: parameter.type.jvmErasure.simpleName!!,
                clazz,
                parameter.annotations,
                function.valueParameters.size - 1 == index
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

/**
 * Generates a Minestom argument based on the class
 *
 * @param name The name of the argument
 * @param clazz The class to base the argument off of
 *
 * @return An argument that matches with the class.
 *
 */
public fun argumentFromClass(
    name: String,
    clazz: KClass<*>,
    annotations: List<Annotation> = emptyList(),
    isLast: Boolean
): Argument<*> {

    return when (clazz) {
        String::class -> if (isLast)
            ArgumentType.StringArray(name).map { it.joinToString(" ") }
        else
            ArgumentType.String(name)
        Int::class -> ArgumentType.Integer(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min.toInt()) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max.toInt()) }
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()
                ?.let { argument.defaultValue(it.number.toInt()) }
        }
        Double::class -> ArgumentType.Double(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max) }
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()?.let { argument.defaultValue(it.number) }
        }
        Color::class -> ArgumentType.Color(name)
        EntityType::class -> ArgumentType.EntityType(name)
        Material::class -> ArgumentMaterial(name)
        Boolean::class -> ArgumentType.Boolean(name).also { argument ->
            annotations.filterIsInstance<DefaultBoolean>().firstOrNull()
                ?.let { argument.defaultValue(it.boolean) }
        }
        Float::class -> ArgumentType.Float(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min.toFloat()) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max.toFloat()) }
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()
                ?.let { argument.defaultValue(it.number.toFloat()) }
        }
        ItemStack::class, Material::class -> ArgumentType.ItemStack(name).also { argument ->
            annotations.filterIsInstance<DefaultMaterial>().firstOrNull()
                ?.let { argument.defaultValue(ItemStack.of(it.material)) }
        }
        NBTCompound::class -> ArgumentType.NbtCompound(name)
        NBT::class -> ArgumentType.NBT(name)
        Component::class -> ArgumentType.Component(name)
        Duration::class -> ArgumentType.Time(name).also { argument ->
            annotations.filterIsInstance<DefaultChronoDuration>().firstOrNull()
                ?.let { argument.defaultValue(Duration.of(it.amount, it.timeUnit)) }

            annotations.filterIsInstance<DefaultTickDuration>().firstOrNull()
                ?.let {
                    argument.defaultValue(
                        Duration.of(
                            it.amount,
                            if (it.isClient) TimeUnit.CLIENT_TICK else TimeUnit.SERVER_TICK
                        )
                    )
                }
        }
        IntRange::class -> ArgumentType.IntRange(name)
        FloatRange::class -> ArgumentType.FloatRange(name)
        SerializableEntityFinder::class -> ArgumentType.Entity(name)
        Enchantment::class -> ArgumentType.Enchantment(name)
        RelativeVec::class -> ArgumentType.RelativeVec3(name)
        Vector::class -> ArgumentType.RelativeVec3(name)
        Byte::class -> ArgumentByte(name).also { argument ->
            argument.min(
                annotations.filterIsInstance<MinAmount>().firstOrNull()?.min?.toInt()?.toByte()
                    ?.coerceAtLeast(Byte.MIN_VALUE) ?: Byte.MIN_VALUE
            )

            argument.max(
                annotations.filterIsInstance<MaxAmount>().firstOrNull()?.max?.toInt()?.toByte()
                    ?.coerceAtLeast(Byte.MAX_VALUE) ?: Byte.MAX_VALUE
            )

            annotations.filterIsInstance<DefaultNumber>().firstOrNull()
                ?.let { argument.defaultValue(it.number.toInt().toByte().coerceAtLeast(Byte.MIN_VALUE).coerceAtMost(Byte.MAX_VALUE)) }

        }
        RelativeBlockPosition::class -> ArgumentType.RelativeBlockPosition(name)
        BlockPosition::class -> ArgumentType.RelativeBlockPosition(name)
        Block::class -> ArgumentType.BlockState(name).also { argument ->
            annotations.filterIsInstance<DefaultBlock>().firstOrNull()
                ?.let { argument.defaultValue(it.block) }
        }
        CommandResult::class -> ArgumentType.Command(name)
        PotionEffect::class -> ArgumentType.Potion(name).also { argument ->
            annotations.filterIsInstance<DefaultPotionEffect>().firstOrNull()
                ?.let { argument.defaultValue(it.potionEffect) }
        }
        UUID::class -> ArgumentType.UUID(name)
        else -> {

            if (annotations.any { it is ParameterContext }) {
                val annotation = annotations.filterIsInstance<ParameterContext>().first()

                val instance = annotation.parser.objectInstance!! as ContextParser<*>

                return ArgumentContext(instance::parse)
            }

            if (clazz.java.enumConstants == null) throw IllegalStateException("Must be a valid argument!")

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            return (ArgumentEnum(name, clazz.java as Class<Enum<*>>))
        }
    }
}