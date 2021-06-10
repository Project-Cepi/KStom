package world.cepi.kstom.extension

import net.minestom.server.extras.selfmodification.MinestomExtensionClassLoader
import world.cepi.kstom.Manager

/**
 * Unsafe utilities for handling extensions.
 *
 * Use [ExtensionCompanion] instead.
 */
internal object ExtensionUtils {

    inline fun currentExtensionClassLoader() =
        this::class.java.classLoader as MinestomExtensionClassLoader

    inline fun currentExtension() =
        Manager.extension.getExtension(currentExtensionClassLoader().extensionName)!!
}