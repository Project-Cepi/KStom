package world.cepi.kstom.schema

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag

class SimpleSchema(damage: Int, name: String = "Hello"): Schema() {
    val damage by schemaInt(damage)
    val name by schemaString(name)

    override val properties = listOf<Schemable>(this.damage, this.name)
}

class SchemaTest: StringSpec({
    val block = Block.BONE_BLOCK

    val appliedSchemaBlock = block.applySchema(SimpleSchema(5))

    appliedSchemaBlock.getTag(Tag.Integer("damage")) shouldBe 5

    appliedSchemaBlock.getTag(Tag.String("name")) shouldBe "Hello"
})