package world.cepi.kstom.nbt.classes

import kotlinx.serialization.Serializable

@Serializable
data class PrimitiveClass(
    val first: Int,
    val second: Byte,
    val third: Short
)