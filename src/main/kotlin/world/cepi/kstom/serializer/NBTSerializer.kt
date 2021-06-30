package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.SNBTParser
import java.io.StringReader

@Serializer(forClass = NBT::class)
@OptIn(ExperimentalSerializationApi::class)
object NBTSerializer : KSerializer<NBT> {
    override fun deserialize(decoder: Decoder): NBT {
        return SNBTParser(StringReader(decoder.decodeSerializableValue(SerializableNBT.serializer()).nbt)).parse()
    }

    override val descriptor: SerialDescriptor
        get() = SerializableNBT.serializer().descriptor

    override fun serialize(encoder: Encoder, value: NBT) {
        encoder.encodeSerializableValue(SerializableNBT.serializer(), SerializableNBT(value.toSNBT()))
    }

    fun NBT.serializer() = this@NBTSerializer
    // Inline class because stdlib is weirdly outdated
    @Serializable
    @JvmInline
    private value class SerializableNBT(val nbt: String)
}