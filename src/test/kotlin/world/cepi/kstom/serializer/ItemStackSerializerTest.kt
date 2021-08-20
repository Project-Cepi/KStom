package world.cepi.kstom.serializer

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import net.kyori.adventure.text.Component
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Material
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import world.cepi.kstom.item.*

class ItemStackSerializerTest {

    @Disabled("Item stacks cant be serialized inside -- yet")
    fun `ensure items can be serialized in items`() {
        val item = item(Material.PAPER) {

            displayName(Component.text("A Paper"))

            withMeta {
                customModelData(4)
                canDestroy(Block.EMERALD_BLOCK)
            }
        }

        val json = Json.encodeToString(ItemStackSerializer, item)

        val backItem = Json.decodeFromString(ItemStackSerializer, json)

        assertEquals(item, backItem)

        val itemWithItem = item.and {
            withMeta {
                this["item", ItemStackSerializer] = item
            }
        }

        assertEquals(item, itemWithItem.meta.get("item", serializer = ItemStackSerializer))

    }

}