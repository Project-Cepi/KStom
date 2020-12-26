package world.cepi.kstom

import net.minestom.server.entity.Player
import net.minestom.server.permission.Permission
import org.jglrxavpok.hephaistos.nbt.NBTCompound

/**
 * Adds a string permission to the player without the object overhead.
 *
 * @param string The string to add to the player's permissions.
 */
fun Player.addPermission(string: String) = this.addPermission(Permission(string))

/**
 * Adds a string permission with NBT to the player without the object overhead.
 *
 * @param string The string to add to the player's permissions.
 * @param nbt The additional NBT data to add to the permission.
 */
fun Player.addPermission(string: String, nbt: NBTCompound) = this.addPermission(Permission(string, nbt))