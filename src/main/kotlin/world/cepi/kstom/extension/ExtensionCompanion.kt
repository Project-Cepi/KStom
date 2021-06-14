package world.cepi.kstom.extension

import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.extensions.Extension
import net.minestom.server.extras.selfmodification.MinestomExtensionClassLoader
import net.minestom.server.extras.selfmodification.MinestomRootClassLoader
import org.slf4j.Logger
import world.cepi.kstom.Manager
import java.nio.file.Path
import kotlin.reflect.KClass

/**
 * Kotlin utilities that can be attatched per extension class.
 */
open class ExtensionCompanion<T: Extension>(val obj: Any) {

    val instance: T
        get() {
            return Manager.extension.getExtension(MinestomRootClassLoader.findExtensionObjectOwner(obj)!!)!! as T
        }

    val eventNode: EventNode<Event>
        get() = instance.eventNode

    val logger: Logger
        get() = instance.logger

    val dataDirectory: Path
        get() = instance.dataDirectory

}