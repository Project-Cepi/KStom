package world.cepi.kstom

import net.minestom.server.entity.Player
import net.minestom.server.permission.Permission
import org.jglrxavpok.hephaistos.nbt.NBTCompound

fun Player.addPermission(string: String) = this.addPermission(Permission(string))
fun Player.addPermission(string: String, nbt: NBTCompound) = this.addPermission(Permission(string, nbt))