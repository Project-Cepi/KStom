package world.cepi.kstom.serialization

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import net.minestom.server.instance.block.Block
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.serializer.BlockSerializer
import world.cepi.kstom.serializer.MinestomJSON

class BlockSerializerTest: StringSpec ({
    "block without nbt data should have empty compound" {
        val block = Block.CHEST

        val encodeToString = MinestomJSON.encodeToString(BlockSerializer, block)
        val decodeFromString = MinestomJSON.decodeFromString(BlockSerializer, encodeToString)

        decodeFromString shouldBe block.withNbt(NBTCompound())
    }

    "block with nbt data should serialize correct" {
        val nbtCompound = NBTCompound()
        nbtCompound.setString("data", "my awesome data!")

        val block = Block.CHEST.withNbt(nbtCompound)

        val encodeToString = MinestomJSON.encodeToString(BlockSerializer, block)
        val decodeFromString = MinestomJSON.decodeFromString(BlockSerializer, encodeToString)

        decodeFromString shouldBe block
    }
})