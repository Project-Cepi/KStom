package world.cepi.kstom.command.arguments

import net.kyori.adventure.text.Component
import net.minestom.server.color.Color
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandResult
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.potion.PotionEffect
import net.minestom.server.utils.Vector
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeBlockPosition
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.UpdateOption
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.command.arguments.annotations.MaxAmount
import world.cepi.kstom.command.arguments.annotations.MinAmount
import world.cepi.kstom.serializer.SerializableEntityFinder
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

class GeneratedArguments<T : Any>(val clazz: KClass<T>, val args: Array<Argument<*>>) {

    fun createInstance(context: CommandContext, sender: CommandSender): T {

        val classes = clazz.primaryConstructor!!.valueParameters.map {
            it.type.classifier as KClass<*>
        }

        return clazz.primaryConstructor!!.call(*args.mapIndexed { index, argument ->

            val correspondingClass = classes[index]
            val value = context.get(argument)

            // Special Material type class

            when (correspondingClass) {
                Material::class -> if (value is ItemStack) return@mapIndexed value.material
                Vector::class -> if (value is RelativeVec) return@mapIndexed value.from(sender as? Entity)
            }

            // Entity finder serializable exception
            if (value is EntityFinder) {
                return@mapIndexed SerializableEntityFinder(context.getRaw(argument.id))
            }

            return@mapIndexed value
        }.toTypedArray())
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
public inline fun <reified T : Any> argumentsFromClass(): GeneratedArguments<T> =
    argumentsFromClass(T::class)

/**
 * Can generate a list of Arguments from a class.
 *
 * @param clazz The class to generate from
 *
 * @return A organized hashmap of arguments and its classifier
 *
 * @throws NullPointerException If the constructor or any arguments are invalid.
 */
public fun <T : Any> argumentsFromClass(clazz: KClass<T>): GeneratedArguments<T> =
    GeneratedArguments(clazz, argumentsFromFunction(clazz.primaryConstructor!!).map { it!! }.toTypedArray())

/**
 * Can generate a list of Arguments from a class constructor.
 *
 * @param constructor The constructor to use to generate args from.
 *
 * @return A organized hashmap of arguments and its classifier
 */
public fun argumentsFromFunction(constructor: KFunction<*>): List<Argument<*>?> =
    constructor.valueParameters.map {
        argumentFromClass(
            it.name ?: it.type.jvmErasure.simpleName!!,
            it.type.classifier!! as KClass<*>,
            it.annotations
        )
    }

/**
 * Generates a Minestom argument based on the class
 *
 * @param clazz The class to base the argument off of
 *
 * @return An argument that matches with the class.
 *
 */
public fun argumentFromClass(name: String, clazz: KClass<*>, annotations: List<Annotation> = emptyList()): Argument<*>? {

    if (clazz.simpleName == null) return null

    return when (clazz) {
        String::class -> ArgumentType.String(name)
        Int::class -> ArgumentType.Integer(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min.toInt()) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max.toInt()) }
        }
        Double::class -> ArgumentType.Double(name).also { argument ->
            annotations.filterIsInstance<MinAmount>().firstOrNull()?.let { argument.min(it.min) }
            annotations.filterIsInstance<MaxAmount>().firstOrNull()?.let { argument.max(it.max) }
        }
        Color::class -> ArgumentType.Color(name)
        EntityType::class -> ArgumentType.EntityType(name)
        Material::class -> ArgumentType.ItemStack(name)
        Boolean::class -> ArgumentType.Boolean(name)
        Float::class -> ArgumentType.Float(name)
        ItemStack::class -> ArgumentType.ItemStack(name)
        Material::class -> ArgumentType.ItemStack(name)
        NBTCompound::class -> ArgumentType.NbtCompound(name)
        NBT::class -> ArgumentType.NBT(name)
        Component::class -> ArgumentType.Component(name)
        UpdateOption::class -> ArgumentType.Time(name)
        IntRange::class -> ArgumentType.IntRange(name)
        FloatRange::class -> ArgumentType.FloatRange(name)
        SerializableEntityFinder::class -> ArgumentType.Entity(name)
        Enchantment::class -> ArgumentType.Enchantment(name)
        RelativeVec::class -> ArgumentType.RelativeVec3(name)
        Vector::class -> ArgumentType.RelativeVec3(name)
        RelativeBlockPosition::class -> ArgumentType.RelativeBlockPosition(name)
        CommandResult::class -> ArgumentType.Command(name)
        PotionEffect::class -> ArgumentType.Potion(name)
        UUID::class -> ArgumentType.UUID(name)
        Array<String>::class -> ArgumentType.StringArray(name)
        else -> {
            if (clazz.java.enumConstants == null) return null

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            (ArgumentEnum(name, clazz.java as Class<Enum<*>>))
        }
    }
}