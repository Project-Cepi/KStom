package world.cepi.kstom.schema

import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag

class ExplosionSchema(damage: Int = 20, radius: Int = 5, beforeTicks: Int = 60, restTicks: Int = 500): Schema {
    val damage by schemaInt(damage)
    val radius = schema(Tag.Integer("radius"), radius)

    val beforeTicks by schemaInt(beforeTicks)
    val restTicks by schemaInt(restTicks)

    val beforeTicksCountdown by schemaTickDown(beforeTicks) // tickDown has a .tick {} method. Whenever it reaches zero, it calls the callback function and resets to the other schema value

    val restTicksCountdown by schemaTickDown(restTicks)

    val isExploding by schemaFlag(false)
    val isRegenerating by schemaFlag(false)

}

fun main() {

    val block = Block.BONE_BLOCK

    // create
    block.applySchema(ExplosionSchema(20, 5, 60))

    // onstep:

    block.withSchema<ExplosionSchema> {
        if (isRegenerating) return

        isExploding = true
        // play sound
    }

    // ontick
    block.withSchema<ExplosionSchema> {

        if (isExploding) {
            beforeTicksCooldown.tick { isExploding = false; /* explode! */; isRegenerating = true }
            return
        }

        if (isRegenerating) restTicksCountdown.tick { isRegenerating = false }
    }
}