package world.cepi.kstom.command.arguments

import net.kyori.adventure.text.Component
import net.minestom.server.color.Color
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentLiteral
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.EntityType
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.entity.EntityFinder
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.math.IntRange
import net.minestom.server.utils.time.TimeUnit
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

/**
 * Automatically generates an ArgumentWord based on the String being passed
 *
 * @return an ArgumentWord based on the String being passed
 */
public fun String.asSubcommand(): ArgumentLiteral = ArgumentType.Literal(this)

/**
 * Can generate a list of Arguments from a class constructor.
 *
 * @param constructor The constructor to use to generate args from.
 *
 * @return A organized hashmap of arguments and its classifier
 */
public fun argumentsFromConstructor(constructor: KFunction<*>): List<Argument<*>> =
        constructor.valueParameters.map { argumentFromClass(it.name ?: it.type.jvmErasure.simpleName!!, it.type.classifier!! as KClass<*>)!! }

/**
 * Generates a Minestom argument based on the class
 *
 * @param clazz The class to base the argument off of
 *
 * @return An argument that matches with the class.
 *
 */
public fun argumentFromClass(name: String, clazz: KClass<*>): Argument<*>? {

    if (clazz.simpleName == null) return null

    // TODO allow complex types

    when (clazz) {
        String::class -> return ArgumentType.String(name)
        Int::class -> return ArgumentType.Integer(name)
        Double::class -> return ArgumentType.Double(name)
        Color::class -> return ArgumentType.Color(name)
        EntityType::class -> return ArgumentType.EntityType(name)
        Material::class -> return ArgumentType.ItemStack(name)
        Boolean::class -> return ArgumentType.Boolean(name)
        Float::class -> return ArgumentType.Float(name)
        ItemStack::class -> return ArgumentType.ItemStack(name)
        NBTCompound::class -> return ArgumentType.NbtCompound(name)
        NBT::class -> return ArgumentType.NBT(name)
        Component::class -> return ArgumentType.Component(name)
        TimeUnit::class -> return ArgumentType.Time(name)
        IntRange::class -> return ArgumentType.IntRange(name)
        FloatRange::class -> return ArgumentType.FloatRange(name)
        EntityFinder::class -> return ArgumentType.Entity(name)
        Enchantment::class -> return ArgumentType.Enchantment(name)
        RelativeVec::class -> return ArgumentType.RelativeVec3(name)
        BlockPosition::class -> return ArgumentType.RelativeBlockPosition(name)
        else -> {
            if (clazz.java.enumConstants == null) return null

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            return ArgumentEnum(name, clazz.java as Class<Enum<*>>)
        }
    }
}