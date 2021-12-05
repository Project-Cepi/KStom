package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeStructure
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.SNBTParser
import java.io.StringReader

@Serializer(forClass = NBT::class)
@OptIn(ExperimentalSerializationApi::class)
object NBTSerializer : KSerializer<NBT> {
    override fun deserialize(decoder: Decoder): NBT {
        return SNBTParser(StringReader(decoder.decodeString())).parse()
    }

    override val descriptor: SerialDescriptor
        get() = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: NBT) {
        encoder.encodeString(value.toSNBT()
            .replace("1B", "true")
            .replace("0B", "false")
            .replace("\\\\", "\\")
        )
    }

    fun NBT.serializer() = this@NBTSerializer
}