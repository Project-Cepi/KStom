package world.cepi.kstom.serializer

import kotlinx.serialization.Serializable
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.particle.Particle
import net.minestom.server.permission.Permission
import net.minestom.server.potion.Potion
import net.minestom.server.potion.PotionEffect
import net.minestom.server.sound.SoundEvent
import net.minestom.server.utils.NamespaceID
import net.minestom.server.utils.location.RelativeVec
import net.minestom.server.utils.math.IntRange
import org.jglrxavpok.hephaistos.nbt.NBT
import java.util.UUID
import kotlin.time.Duration

typealias SerializableBlock = @Serializable(with = BlockSerializer::class) Block
typealias SerializableBossBar = @Serializable(with = BossBarSerializer::class) BossBar
typealias SerializableComponent = @Serializable(with = ComponentSerializer::class) Component
typealias SerializableDuration = @Serializable(with = DurationSerializer::class) Duration
typealias SerializableEntityType = @Serializable(with = EntityTypeSerializer::class) EntityType
typealias SerializableIntRange = @Serializable(with = IntRangeSerializer::class) IntRange
typealias SerializableItemStack = @Serializable(with = ItemStackSerializer::class) ItemStack
typealias SerializableMaterial = @Serializable(with = MaterialSerializer::class) Material
typealias SerializableNamespaceID = @Serializable(with = NamespaceIDSerializer::class) NamespaceID
typealias SerializableNBT = @Serializable(with = NBTSerializer::class) NBT
typealias SerializableParticle = @Serializable(with = ParticleSerializer::class) Particle
typealias SerializablePermission = @Serializable(with = PermissionSerializer::class) Permission
typealias SerializablePosition = @Serializable(with = PositionSerializer::class) Pos
typealias SerializablePotionEffect = @Serializable(with = PotionEffectSerializer::class) PotionEffect
typealias SerializablePotion = @Serializable(with = PotionSerializer::class) Potion
typealias SerializableRelativeVec = @Serializable(with = RelativeVecSerializer::class) RelativeVec
typealias SerializableSoundEvent = @Serializable(with = SoundEventSerializer::class) SoundEvent
typealias SerializableSound = @Serializable(with = SoundSerializer::class) Sound
typealias SerializableUUID = @Serializable(with = UUIDSerializer::class) UUID
typealias SerializableVector = @Serializable(with = VectorSerializer::class) Vec
