package world.cepi.kstom.serializer

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Material
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import world.cepi.kstom.item.item
import world.cepi.kstom.item.withMeta

class ItemStackSerializerTest {

    @Test
    fun `make sure item serialization goes back and forth`() {
        val item = item(Material.PAPER) {

            displayName(Component.text("A Paper"))

            withMeta {
                customModelData(4)
                canDestroy(Block.EMERALD_BLOCK)
            }
        }

        val json = Json.encodeToString(ItemStackSerializer, item)

        val backItem = Json.decodeFromString(ItemStackSerializer, json)

        assertEquals(item.meta, backItem.meta)

    }

}