package world.cepi.kstom.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import net.minestom.server.permission.Permission
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound

object PermissionSerializer: KSerializer<Permission> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Permission") {
        element<String>("permissionName")
        element<NBT>("nbt")
    }

    override fun serialize(encoder: Encoder, value: Permission) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.permissionName)
            encodeSerializableElement(descriptor, 1, NBTSerializer, value.nbtData ?: NBTCompound())
        }
    }

    override fun deserialize(decoder: Decoder): Permission = decoder.decodeStructure(descriptor) {
        var permissionName: String? = null
        var nbt: NBT? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> permissionName = decodeStringElement(descriptor, 0)
                1 -> nbt = decodeSerializableElement(descriptor, 1, NBTSerializer)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }

        permissionName?.let { Permission(it, nbt as? NBTCompound ?: NBTCompound()) } ?: Permission("")
    }
}