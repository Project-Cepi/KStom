package world.cepi.kstom.serialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.entity.EntityType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.kstom.serializer.EntityTypeSerializer
import world.cepi.kstom.serializer.MinestomJSON

class EntitySerializerTest {

    @Test
    fun `entity types should be serialized`() {
        val type = EntityType.SKELETON

        val typeString = MinestomJSON.encodeToString(EntityTypeSerializer, type)

        val backToType = MinestomJSON.decodeFromString(EntityTypeSerializer, typeString)

        assertEquals(type, backToType)
    }

}