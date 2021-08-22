package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encoding.Decoder
import net.minestom.server.instance.block.Block
import net.minestom.server.potion.PotionEffect

@OptIn(ExperimentalSerializationApi::class)
object PotionEffectSerializer : AbstractProtocolObjectSerializer<PotionEffect>(PotionEffect::class) {
    override fun deserialize(decoder: Decoder): PotionEffect = PotionEffect.fromNamespaceId(decoder.decodeString())!!
}