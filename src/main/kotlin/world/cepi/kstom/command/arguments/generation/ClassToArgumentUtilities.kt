package world.cepi.kstom.command.arguments.generation

import net.kyori.adventure.text.Component
import net.minestom.server.color.Color
import net.minestom.server.command.builder.CommandResult
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Enchantment
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.potion.PotionEffect
import net.minestom.server.sound.SoundEvent
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.FloatRange
import net.minestom.server.utils.time.TimeUnit
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.command.arguments.*
import world.cepi.kstom.command.arguments.generation.annotations.*
import world.cepi.kstom.serializer.SerializableEntityFinder
import java.lang.reflect.RecordComponent
import java.time.Duration
import java.util.*
import kotlin.reflect.KClass

fun argumentFromRecordComponent(
    name: String,
    component: RecordComponent,
    annotations: List<Annotation> = emptyList(),
    isLast: Boolean
) = argumentFromClass(
    name,
    component.type.kotlin,
    annotations,
    isLast
)


/**
 * Generates a Minestom argument based on the class
 *
 * @param name The name of the argument
 * @param clazz The class to base the argument off of
 *
 * @return An argument that matches with the class.
 *
 */
fun argumentFromClass(
    name: String,
    clazz: KClass<*>,
    annotations: List<Annotation> = emptyList(),
    isLast: Boolean
): Argument<*> {

    if (annotations.any { it is ParameterContext }) {
        val annotation = annotations.filterIsInstance<ParameterContext>().first()

        val instance = annotation.parser.objectInstance
            ?: throw IllegalStateException("Class ${annotation.parser.name} is not an object type! (ParameterContext)")

        return instance.toArgumentContext()
    }

    if (annotations.any { it is CustomArgument }) {
        val annotation = annotations.filterIsInstance<CustomArgument>().first()

        val instance = annotation.generator.objectInstance
            ?: throw IllegalStateException("Class ${annotation.generator.name} is not an object type! (CustomArgument)")

        return instance.new(name, annotations)
    }

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
                ?.let { argument.defaultValue(ItemStack.of(Material.fromNamespaceId(it.material) ?: error("Invalid Material ${it.material}!"))) }
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
        FloatRange::class -> ArgumentType.FloatRange(name).also { argument ->
            annotations.filterIsInstance<DefaultFloatRange>().firstOrNull()
                ?.let { argument.defaultValue(FloatRange(it.minimum, it.maximum)) }
        }
        SerializableEntityFinder::class -> ArgumentType.Entity(name)
        Enchantment::class -> ArgumentType.Enchantment(name)
        RelativeVec::class -> ArgumentType.RelativeVec3(name)
        Vec::class -> ArgumentType.RelativeVec3(name)
        SoundEvent::class -> ArgumentSound(name)
        Byte::class -> ArgumentByte(name).also { argument ->
            annotations.filterIsInstance<DefaultNumber>().firstOrNull()
                ?.let { argument.defaultValue(it.number.toInt().toByte().coerceAtLeast(Byte.MIN_VALUE).coerceAtMost(Byte.MAX_VALUE)) }

        }
        Block::class -> ArgumentType.BlockState(name).also { argument ->
            annotations.filterIsInstance<DefaultBlock>().firstOrNull()
                ?.let { argument.defaultValue(Block.fromNamespaceId(it.block)) }
        }
        Point::class -> ArgumentType.RelativeVec3(name)
        CommandResult::class -> ArgumentType.Command(name)
        PotionEffect::class -> ArgumentType.Potion(name).also { argument ->
            annotations.filterIsInstance<DefaultPotionEffect>().firstOrNull()
                ?.let { argument.defaultValue(PotionEffect.fromNamespaceId(it.potionEffect)) }
        }
        UUID::class -> ArgumentType.UUID(name)
        else -> {

            if (clazz.java.enumConstants == null) throw IllegalStateException("Class ${clazz.qualifiedName} must be a valid argument!")

            @Suppress("UNCHECKED_CAST") // We already check if the class is an enum or not.
            return (ArgumentEnum(name, clazz.java as Class<Enum<*>>)).also { enumArgument ->
                val annotation = annotations.filterIsInstance<EnumArgument>().firstOrNull() ?: return@also

                val enumConstraints = (clazz.java as Class<Enum<*>>).enumConstants

                enumArgument.setFormat(annotation.flattenType)
                enumArgument.defaultValue(enumConstraints.firstOrNull { it.name.lowercase() == annotation.default.lowercase() })
            }
        }
    }.also {
        if (annotations.any { it is DynamicWord }) {
            val annotation = annotations.filterIsInstance<DynamicWord>().first()

            val instance = annotation.generator.objectInstance
                ?: throw IllegalStateException("Class ${annotation.generator.name} is not an object type! (DynamicWord)")

            it.suggestComplex(lambda = { instance.grab(sender) })
        }
    }
}