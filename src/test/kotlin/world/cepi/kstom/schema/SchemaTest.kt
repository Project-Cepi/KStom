package world.cepi.kstom.schema

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag

class SimpleSchema @JvmOverloads constructor(damage: Int = 5, name: String = "Hello"): Schema() {
    var damage by schemaInt(damage)
    var name by schemaString(name)
}

class SchemaTest: StringSpec({
    "schemas should apply" {
        val block = Block.BONE_BLOCK

        val appliedSchemaBlock = block.applySchema(SimpleSchema(5))

        appliedSchemaBlock.getTag(Tag.Integer("damage")) shouldBe 5

        appliedSchemaBlock.getTag(Tag.String("name")) shouldBe "Hello"
    }

    "schemas should be writable" {
        val block = Block.BONE_BLOCK

        val appliedBlock = block.withSchema<SimpleSchema> {
            name = "World"
        }

        appliedBlock.getTag(Tag.String("name")) shouldBe "World"
    }
})