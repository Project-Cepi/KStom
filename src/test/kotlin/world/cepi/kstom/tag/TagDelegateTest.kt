package world.cepi.kstom.tag

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.tag.Tag
import world.cepi.kstom.item.item

val ItemStack.canCutTrees by TagDelegate(Tag.Boolean("canCutTreeTag"))
var Entity.friendlyName by TagDelegate(Tag.String("friendlyName"))

class TagDelegateTest : StringSpec({

    "getting tag from TagReadable should work" {
        val itemStack = item(Material.WOODEN_AXE) {
            setTag(Tag.Boolean("canCutTreeTag"), true)
        }

        itemStack.canCutTrees shouldBe true
    }

    "setting tag on TagWritable with delegate should work" {
        val entity = Entity(EntityType.ZOMBIFIED_PIGLIN)

        entity.getTag(Tag.String("friendlyName")) shouldBe null

        entity.friendlyName = "Zombie Piglin"

        entity.getTag(Tag.String("friendlyName")) shouldBe "Zombie Piglin"
    }

})
