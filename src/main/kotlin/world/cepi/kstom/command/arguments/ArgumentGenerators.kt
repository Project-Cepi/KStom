package world.cepi.kstom.command.arguments

import net.kyori.adventure.text.Component
import net.minestom.server.color.Color
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.EntityType
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeBlockPosition
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.UpdateOption
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

/**
 * Can generate a list of Arguments from a class constructor.
 *
 * @param constructor The constructor to use to generate args from.
 *
 * @return A organized hashmap of arguments and its classifier
 */
public fun argumentsFromConstructor(constructor: KFunction<*>): List<Argument<*>> =
    safeArgumentsFromConstructor(constructor).map { it!! }

/**
 * Can generate a list of Arguments from a class constructor.
 *
 * @param constructor The constructor to use to generate args from.
 *
 * @return A organized hashmap of arguments and its classifier
 */
public fun safeArgumentsFromConstructor(constructor: KFunction<*>): List<Argument<*>?> =
    constructor.valueParameters.map {
        argumentFromClass(it.name ?: it.type.jvmErasure.simpleName!!, it.type.classifier!! as KClass<*>)
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
        Int::class -> ArgumentType.Integer(name)
        Double::class -> ArgumentType.Double(name)
        Color::class -> ArgumentType.Color(name)
        EntityType::class -> ArgumentType.EntityType(name)
        Material::class -> ArgumentType.ItemStack(name)
        Boolean::class -> ArgumentType.Boolean(name)
        Float::class -> ArgumentType.Float(name)
        ItemStack::class -> ArgumentType.ItemStack(name)
        NBTCompound::class -> ArgumentType.NbtCompound(name)
        NBT::class -> ArgumentType.NBT(name)
        Component::class -> ArgumentType.Component(name)
        UpdateOption::class -> ArgumentType.Time(name)
        IntRange::class -> ArgumentType.IntRange(name)
        FloatRange::class -> ArgumentType.FloatRange(name)
        EntityFinder::class -> ArgumentType.Entity(name)
        Enchantment::class -> ArgumentType.Enchantment(name)
        RelativeVec::class -> ArgumentType.RelativeVec3(name)
        RelativeBlockPosition::class -> ArgumentType.RelativeBlockPosition(name)
        else -> {
            if (clazz.java.enumConstants == null) return null

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            (ArgumentEnum(name, clazz.java as Class<Enum<*>>))
        }
    }
}