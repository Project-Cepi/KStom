package world.cepi.kstom.schema

import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag

class ExplosionSchema(damage: Int = 20, radius: Int = 5, beforeTicks: Int = 60, restTicks: Int = 500): Schema() {
    var damage by schemaInt(damage)
    var radius = schema(Tag.Integer("radius"), radius)

    var beforeTicks by schemaInt(beforeTicks)
    var restTicks by schemaInt(restTicks)

    var isExploding by schemaFlag(false)
    var isRegenerating by schemaFlag(false)

}

fun main() {

    val block = Block.BONE_BLOCK

    // create
    block.applySchema(ExplosionSchema(20, 5, 60))

    // onstep:

    block.withSchema<ExplosionSchema> {
        if (isRegenerating == true) return@withSchema

        isExploding = true
        // play sound
    }

    // ontick
    block.withSchema<ExplosionSchema> {

    }
}