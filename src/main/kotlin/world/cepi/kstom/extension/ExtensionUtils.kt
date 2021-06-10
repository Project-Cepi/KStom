package world.cepi.kstom.extension

import net.minestom.server.extras.selfmodification.MinestomExtensionClassLoader
import net.minestom.server.extras.selfmodification.MinestomRootClassLoader
import world.cepi.kstom.Manager
import kotlin.reflect.KClass

/**
 * Unsafe utilities for handling extensions.
 *
 * Use [ExtensionCompanion] instead.
 */
internal object ExtensionUtils {

    inline fun currentExtension(clazz: KClass<*>) =
        Manager.extension.getExtension(MinestomRootClassLoader.findExtensionObjectOwner(clazz)!!)!!
}