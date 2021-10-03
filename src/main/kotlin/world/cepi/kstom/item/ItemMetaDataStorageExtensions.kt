package world.cepi.kstom.item

import kotlinx.serialization.*
import java.util.*
import kotlinx.serialization.modules.SerializersModule
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.potion.Potion
import net.minestom.server.potion.PotionEffect
import net.minestom.server.sound.SoundEvent
import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagReadable
import net.minestom.server.tag.TagWritable
import net.minestom.server.utils.NamespaceID
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.IntRange
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.nbt.NBTParser
import world.cepi.kstom.nbt.NBTFormat
import world.cepi.kstom.serializer.*
import java.time.Duration
import kotlin.reflect.KClass

@OptIn(InternalSerializationApi::class)
operator fun <T: @Serializable Any> TagWritable.set(
    tag: String,
    serializer: KSerializer<T>,
    module: SerializersModule? = null,
    item: @Serializable T,
) {
    if (module == null)
        setTag(Tag.NBT(tag), NBTParser.serialize(serializer, item))
    else
        setTag(Tag.NBT(tag), NBTFormat(module).serialize(serializer, item))
}

@OptIn(InternalSerializationApi::class)
inline operator fun <reified T: @Serializable Any> TagWritable.set(
    tag: String,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = when(T::class) {
        Block::class -> BlockSerializer as KSerializer<T>
        RelativeVec::class -> RelativeVecSerializer as KSerializer<T>
        ParticleSerializer::class -> ParticleSerializer as KSerializer<T>
        BossBar::class -> BossBarSerializer as KSerializer<T>
        Component::class -> ComponentSerializer as KSerializer<T>
        Duration::class -> DurationSerializer as KSerializer<T>
        EntityType::class -> EntityTypeSerializer as KSerializer<T>
        ItemStack::class -> ItemStackSerializer as KSerializer<T>
        Potion::class -> PotionSerializer as KSerializer<T>
        Material::class -> MaterialSerializer as KSerializer<T>
        NamespaceID::class -> NamespaceIDSerializer as KSerializer<T>
        NBT::class -> NBTSerializer as KSerializer<T>
        Pos::class -> PositionSerializer as KSerializer<T>
        PotionEffect::class -> PotionEffectSerializer as KSerializer<T>
        SoundEvent::class -> SoundEventSerializer as KSerializer<T>
        Sound::class -> SoundSerializer as KSerializer<T>
        UUID::class -> UUIDSerializer as KSerializer<T>
        Vector::class -> VectorSerializer as KSerializer<T>
        IntRange::class -> IntRangeSerializer as KSerializer<T>
        else -> T::class.serializer()
                                                },
    item: @Serializable T
) = set(tag, serializer, module, item)

// TODO convert to TagWithable or something when its an interface

@OptIn(InternalSerializationApi::class)
fun <T: @Serializable Any> Block.with(
    tag: String,
    clazz: KClass<T>,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = clazz.serializer(),
    item: @Serializable T
): Block = if (module == null)
        withTag(Tag.NBT(tag), NBTParser.serialize(serializer, item))
    else
        withTag(Tag.NBT(tag), NBTFormat(module).serialize(serializer, item))

@OptIn(InternalSerializationApi::class)
inline fun <reified T: @Serializable Any> Block.with(
    tag: String,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = T::class.serializer(),
    item: @Serializable T
): Block = with(tag, T::class, module, serializer, item)

@OptIn(InternalSerializationApi::class)
fun <T: @Serializable Any> TagReadable.get(
    tag: String,
    clazz: KClass<T>,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = clazz.serializer()
): T? = this.getTag(Tag.NBT(tag))?.let {
    return@let (
            if (module == null)
                NBTParser
            else
                NBTFormat(module)
            )
        .deserialize(serializer, it as? NBTCompound ?: return null)
}

@OptIn(InternalSerializationApi::class)
public inline fun <reified T: @Serializable Any> TagReadable.get(
    tag: String,
    module: SerializersModule? = null,
    serializer: KSerializer<T> = T::class.serializer()
): T? = this.get(tag, T::class, module, serializer)