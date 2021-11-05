package world.cepi.kstom.serialization

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.minestom.server.permission.Permission
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.serializer.MinestomJSON

class PermissionSerializerTests: StringSpec({
    "Test permission serializer without nbt data" {
        val expected = Permission("hello-world")

        val encodeToString = MinestomJSON.encodeToString(expected)
        val actual = MinestomJSON.decodeFromString<Permission>(encodeToString)

        expected.permissionName shouldBe actual.permissionName
    }

    "Test permission serializer with nbt data" {
        val nbtCompound = NBTCompound().apply { setString("testing", "123") }
        val expected = Permission("hello-world", nbtCompound)

        val encodeToString = MinestomJSON.encodeToString(expected)
        val actual = MinestomJSON.decodeFromString<Permission>(encodeToString)

        expected.permissionName shouldBe actual.permissionName
        expected.nbtData shouldBe nbtCompound
    }
})