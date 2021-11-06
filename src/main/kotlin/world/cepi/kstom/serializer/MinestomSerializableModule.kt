package world.cepi.kstom.serializer

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import net.minestom.server.entity.EntityType
import kotlin.reflect.KClass

val MinestomSerializableModule = SerializersModule {
    contextual(BlockSerializer)
    contextual(ItemStackSerializer)
    contextual(PotionSerializer)
    contextual(NBTSerializer)
    contextual(PositionSerializer)
    contextual(SoundSerializer)
    contextual(VectorSerializer)
    contextual(IntRangeSerializer)
    contextual(UUIDSerializer)
    contextual(DurationSerializer)
    contextual(NamespaceIDSerializer)
    contextual(ComponentSerializer)
    contextual(BossBarSerializer)
    contextual(PermissionSerializer)
}

val MinestomJSON = Json {
    serializersModule = MinestomSerializableModule
    isLenient = true
    ignoreUnknownKeys = true
}