package world.cepi.kstom.serialization

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.entity.EntityType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import world.cepi.kstom.serializer.EntityTypeSerializer
import world.cepi.kstom.serializer.MinestomJSON

class EntitySerializerTest : StringSpec({
    "entity types should serialize correctly" {
        val type = EntityType.SKELETON

        val typeString = MinestomJSON.encodeToString(EntityTypeSerializer, type)

        val backToType = MinestomJSON.decodeFromString(EntityTypeSerializer, typeString)

        type shouldBe backToType
    }
})