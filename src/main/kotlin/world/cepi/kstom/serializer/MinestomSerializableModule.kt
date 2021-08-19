package world.cepi.kstom.serializer

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.contextual

fun MinestomSerializableModule(lambda: SerializersModuleBuilder.() -> Unit = {}) = SerializersModule {
    contextual(DurationSerializer)
    contextual(ItemStackSerializer)
    contextual(NBTSerializer)
    contextual(PositionSerializer)
    contextual(SoundSerializer)
    contextual(VectorSerializer)
    lambda(this)
}